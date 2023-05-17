package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.services.CategoryService;
import com.nhom7.foodg.services.FuzzySearchService;
import com.nhom7.foodg.services.FuzzySearchServiceImpl;
import com.nhom7.foodg.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping
public class FuzzySearchController {
    private final ProductService productService;
    private final CategoryService categoryService;
    @Autowired
    public FuzzySearchController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    private boolean fuzzyMatch(String strSearch, String strReference){
        strSearch = ".*" + String.join(".*", strSearch.split("")) + ".*";
        Pattern re = Pattern.compile(strSearch);
        return re.matcher(strReference).find();
    }

    private String changeUnicode(String str){
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        return temp.replaceAll("đ", "d").replaceAll("Đ", "D");
    }

    private boolean haveAlphabet(String str){
        for(int i = 0; i < str.length(); i++){
            String charAt = String.valueOf(str.charAt(i));
            if(changeUnicode(charAt).toLowerCase().matches("[a-z]")){
                return true;
            }
        }
        return false;
    }

    private ArrayList<String> ResultFuzzySearch(ArrayList<String> arr, String strSearch){
        ArrayList<String> result = new ArrayList<String>();
        if(strSearch.equals("")|| !haveAlphabet(strSearch)) {
            return null;
        }
        String[] splitText = strSearch.split("\\s");
        for(String element : arr){
            boolean all = true;
            for(String searchText : splitText){
                if(!fuzzyMatch(changeUnicode(searchText.toLowerCase()), changeUnicode(element.toLowerCase()))){
                    all = false;
                    break;
                }
            }
            if(all){
                result.add(element);
            }
        }
        return result;
    }

    @GetMapping("/FuzzySearchProducts/{name}")
    public ResponseEntity<ArrayList<String>> FuzzySearchProducts(@PathVariable("name") String name){
        List<TblProductEntity> arr = productService.getAll();
        ArrayList<String> r = new ArrayList<String>();
        for(int i = 0; i < arr.size(); i++){
            r.add(arr.get(i).getName());
        }
        ArrayList<String> result = new ArrayList<String>();
        result = ResultFuzzySearch(r, name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/FuzzySearchCategories/{name}")
    public ResponseEntity<ArrayList<String>> FuzzySearchCategories(@PathVariable("name") String name){
        List<TblCategoryEntity> arr = categoryService.getAll();
        ArrayList<String> r = new ArrayList<String>();
        for(int i = 0; i < arr.size(); i++){
            r.add(arr.get(i).getName());
        }
        ArrayList<String> result = new ArrayList<String>();
        result = ResultFuzzySearch(r, name);
        return ResponseEntity.ok(result);
    }
}
