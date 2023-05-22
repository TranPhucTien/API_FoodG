package com.nhom7.foodg.services.impls;

import com.nhom7.foodg.models.entities.TblDiscountEntity;
import com.nhom7.foodg.models.entities.TblProvinceEntity;
import com.nhom7.foodg.repositories.ProvinceRepository;
import com.nhom7.foodg.services.ProvinceService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final String TABLE_NAME = "tbl_province";

    public ProvinceServiceImpl(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }


    @Override
    public List<TblProvinceEntity> getAll(){
        return provinceRepository.findAll();
    }


    @Override
    public List<TblProvinceEntity> search(String keyword){
        List<TblProvinceEntity> rs = new ArrayList<>();
        List<TblProvinceEntity> provinces = provinceRepository.findAll();
        for (TblProvinceEntity province : provinces){
            if (province.getWard().toLowerCase().contains(keyword.toLowerCase())){
                rs.add(province);
            }
        }
        return rs;
    }
}
