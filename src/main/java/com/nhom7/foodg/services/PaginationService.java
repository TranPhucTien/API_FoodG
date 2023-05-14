package com.nhom7.foodg.services;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface PaginationService {
    Map<String, Long> getCountProductEachCategory();
}
