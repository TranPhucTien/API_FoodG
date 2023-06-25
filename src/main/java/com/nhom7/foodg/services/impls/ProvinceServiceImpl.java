package com.nhom7.foodg.services.impls;

import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.ModifyException;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.entities.TblProvinceEntity;
import com.nhom7.foodg.models.entities.TblProvinceEntity;
import com.nhom7.foodg.repositories.ProvinceRepository;
import com.nhom7.foodg.services.ProvinceService;
import com.nhom7.foodg.shareds.Constants;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public void insert(TblProvinceEntity newProvince){
//        //Validate input
        Constants.validateRequiredFields(newProvince, "city", "district", "ward");

        String provinceWard = newProvince.getWard();
        String provinceDistrict = newProvince.getDistrict();

        try {

            if (provinceRepository.existsByWard(provinceWard) && provinceRepository.existsByDistrict(provinceDistrict)){
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, provinceWard));
            }

            TblProvinceEntity tblDiscountEntity = TblProvinceEntity.create(
                    0,
                    provinceWard,
                    provinceDistrict,
                    newProvince.getCity()
            );
            provinceRepository.save(tblDiscountEntity);

        } catch (DataIntegrityViolationException ex){
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }
    @Transactional
    @Override
    public void update(TblProvinceEntity tblDiscountEntity){
        if (provinceRepository.existsByWard(tblDiscountEntity.getWard()) && provinceRepository.existsByDistrict(tblDiscountEntity.getDistrict())){
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, tblDiscountEntity.getWard()));
        }
        if (!provinceRepository.existsById(tblDiscountEntity.getId())) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, tblDiscountEntity.getId()));
        }

        try {
            TblProvinceEntity province = provinceRepository.findById(tblDiscountEntity.getId()).orElse(null);

            if (province != null) {
                province.setWard(tblDiscountEntity.getWard());
                province.setDistrict(tblDiscountEntity.getDistrict());
                province.setCity(tblDiscountEntity.getCity());

                provinceRepository.save(province);
            }
        } catch (NullPointerException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }

}
