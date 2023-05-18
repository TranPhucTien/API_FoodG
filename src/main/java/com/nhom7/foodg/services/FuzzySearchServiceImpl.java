package com.nhom7.foodg.services;

import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;
@Component
public class FuzzySearchServiceImpl implements FuzzySearchService{
    public boolean fuzzyMatch(String strSearch, String strReference){
        strSearch = ".*" + String.join(".*", strSearch.split("")) + ".*";
        Pattern re = Pattern.compile(strSearch);
        return re.matcher(strReference).find();
    }
    public String changeUnicode(String str){
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        return temp.replaceAll("đ", "d").replaceAll("Đ", "D");
    }
    public boolean haveAlphabet(String str){
        for(int i = 0; i < str.length(); i++){
            String charAt = String.valueOf(str.charAt(i));
            if(changeUnicode(charAt).toLowerCase().matches("[a-z]")){
                return true;
            }
        }
        return false;
    }
    public ArrayList<String> ResultFuzzySearch(ArrayList<String> arr, String strSearch){
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
}
