package com.nhom7.foodg.utils;

import java.lang.reflect.Field;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FuzzySearch<T> {
    List<T> data;
    public FuzzySearch(List<T> data) {
        this.data = data;
    }

    public String getName(T data) {
        try {
            Field nameField = data.getClass().getDeclaredField("name");
            nameField.setAccessible(true);
            Object nameValue = nameField.get(data);
            if (nameValue instanceof String) {
                return (String) nameValue;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Ignore the exception and return null
        }
        return null;

    };

    public ArrayList<T> FuzzySearchByName(String name){
        ArrayList<T> r = new ArrayList<T>();
        for(int i = 0; i < data.size(); i++){
            r.add(data.get(i));
        }
        ArrayList<T> result = new ArrayList<T>();
        result = ResultFuzzySearch(r, name);
        return result;
    }

    public boolean fuzzyMatch(String strSearch, String strReference){
        strSearch = ".*" + String.join(".*", strSearch.split("")) + ".*";
        Pattern re = Pattern.compile(strSearch);
        return re.matcher(strReference).find();
    }
    public String changeUnicode(String str){
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll(""); //loại bỏ
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

    public ArrayList<T> ResultFuzzySearch(ArrayList<T> arr, String strSearch){
        ArrayList<T> result = new ArrayList<T>();
        if(strSearch.equals("")|| !haveAlphabet(strSearch)) {
            return null;
        }
        String[] splitText = strSearch.split("\\s");
        for(T element : arr){
            boolean all = true;
            for(String searchText : splitText){
                String elementName = getName(element);
                if(!fuzzyMatch(changeUnicode(searchText.toLowerCase()), changeUnicode(elementName.toLowerCase()))){
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