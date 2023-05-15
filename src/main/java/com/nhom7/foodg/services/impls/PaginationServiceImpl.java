package com.nhom7.foodg.services;

import com.nhom7.foodg.repositories.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaginationServiceImpl implements PaginationService{
    private final ProductRepository productRepository;
    public PaginationServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Map<String, Long> getCountProductEachCategory() {
        List<Object[]> results = productRepository.countAllProductEachCategory();

        Map<String, Long> counts = new HashMap<>();
        for (Object[] result : results) {
            String categoryName = (String) result[0];
            Long count = (Long) result[1];
            counts.put(categoryName, count);
        }

        return counts;
    }
}
