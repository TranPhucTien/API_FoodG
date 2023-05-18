package com.nhom7.foodg.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface FuzzySearchService {
    boolean fuzzyMatch(String strSearch, String srtReference);
    String changeUnicode(String str);
    boolean haveAlphabet(String str);
    ArrayList<String> ResultFuzzySearch(ArrayList<String> arr, String strSearch);
}
