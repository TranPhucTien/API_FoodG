package com.nhom7.foodg.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Algorithm<T> {
    public ArrayList<ArrayList<T>> splitList(int page, int limit, List<T> l){
        ArrayList<ArrayList<T>> result = new ArrayList<ArrayList<T>>();
        int remain = l.size();
        int check = 1;
        int count = 0;
        for(int i = 0; i < page; i++){
            ArrayList<T> temp = new ArrayList<T>();
            for(int j = 0; j < limit; j ++){
                if( remain == 0 ){
                    check = 0;
                    break;
                }
                remain--;
                temp.add(l.get(count));
                count++;
            }
            result.add(temp);
            if(check == 0){
                break;
            }
        }
        return result;
    }

    public <T> boolean haveMethodAttribute(String attribute, Class<T> clazz){
        Method[] method = clazz.getMethods();
        for(Method m : method){
            if(m.getName().equals(attribute)){
                return true;
            }
        }
        return false;
    }

    public <T> Comparator<T> getComparatorByName(String name, Class<T> clazz) throws NoSuchMethodException {
        String attribute = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        if(!haveMethodAttribute(attribute, clazz)){
            attribute = "getName";
        }
        Method m = clazz.getMethod(attribute);
        Comparator<T> cm = Comparator.comparing((Function<T, Comparable>) (T t) -> {
            try {
                return (Comparable) m.invoke(t);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        return cm;
    }
}
