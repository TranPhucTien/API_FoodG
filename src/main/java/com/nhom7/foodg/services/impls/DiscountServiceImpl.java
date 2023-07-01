package com.nhom7.foodg.services.impls;

import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.ModifyException;
import com.nhom7.foodg.exceptions.NotApplyDiscountExeption;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.dto.TblDiscountDto;
import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblDiscountEntity;
import com.nhom7.foodg.repositories.DiscountRepository;
import com.nhom7.foodg.services.DiscountService;
import com.nhom7.foodg.shareds.Constants;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


import javax.swing.*;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


@Component
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;


    private final String TABLE_NAME = "tbl_discount";

    public DiscountServiceImpl(DiscountRepository discountRepository){
        this.discountRepository = discountRepository;
    }

    @Override
    public List<TblDiscountEntity> getAll(){
        return discountRepository.findAll();
    }

//    @Override
//    public Boolean checkCode(String code){
//        if (!discountRepository.existsByCode(code)){
//           return false;
//        }
//        else return true;
//    }
    @Override
    public BigDecimal checkDiscount(String code,BigDecimal price)  {



        // Kiểm tra end_date của hóa đơn còn hạn hay không
        if (!discountRepository.existsByCode(code)) {
            throw new NotFoundException(MessageFormat.format(Constants.NOT_EXIT_DISCOUNT,  code));
        }

        TblDiscountEntity tblDiscountEntity  = discountRepository.getByCode(code);
        // Kiểm tra end_date của hóa đơn còn hạn hay không
        Date startDate = tblDiscountEntity.getStartDate();
        Date endDate = tblDiscountEntity.getEndDate();
        Date currentDate = Constants.getCurrentDay();
        if (currentDate.before(startDate) || currentDate.after(endDate)) {
            // Nếu hóa đơn đã hết hạn, xử lý ngoại lệ ở đây
            throw new NotFoundException(MessageFormat.format(Constants.NOT_VALID_DATE,  code));
        }
        //Kiểm tra trạng thái active của discount
        else if (tblDiscountEntity.getIsActive() == false) {
            throw new NotFoundException(MessageFormat.format(Constants.NOT_VALID_STATUS,  code));
        }



            BigDecimal priceAfterDiscount = price.multiply(BigDecimal.valueOf(tblDiscountEntity.getPercentage()).divide(BigDecimal.valueOf(100)));


            return priceAfterDiscount;
    }

    @Override
    public List<TblDiscountEntity> search(String keyword){
        List<TblDiscountEntity> rs = new ArrayList<>();
        List<TblDiscountEntity> discounts = discountRepository.findAll();
        for (TblDiscountEntity discount : discounts){
            if (discount.getCode().toLowerCase().contains(keyword.toLowerCase())){
                rs.add(discount);
            }
        }
        return rs;
    }
    @Override
    public TblDiscountEntity getByID(int id) {
        if (discountRepository.getDiscountByID(id) == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        return discountRepository.findById(id).orElse(null);
    }

    @Override
    public void insert(TblDiscountEntity newDiscount){
        //Validate input
        Constants.validateRequiredFields(newDiscount, "percentage","code", "minAmount", "startDate","isActive",  "maxDiscountPrice", "createdBy", "updatedBy");
        Constants.validateIntegerFields(newDiscount, "percentage", "createdBy", "updatedBy", "deletedBy");
        Constants.validateDecimalFields(newDiscount, 5, 2, "maxDiscountPrice");
        Constants.validateDecimalFields(newDiscount, 6, 2,  "minAmount");
        Constants.validateDateFields(newDiscount, "startDate", "endDate");
        Constants.validateStringFields(newDiscount, "varchar(20)", 5, 20, "code");


        String discountCode = newDiscount.getCode();
        try {

            if (discountRepository.existsByCode(discountCode)){
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, discountCode));
            }
            Date currentDate = Constants.getCurrentDay();

            TblDiscountEntity tblDiscountEntity = TblDiscountEntity.create(
                    0,
                    discountCode,
                    newDiscount.getPercentage(),
                    newDiscount.getMinAmount(),
                    newDiscount.getStartDate(),
                    newDiscount.getEndDate(),
                    newDiscount.getIsActive(),
                    currentDate,
                    currentDate,
                    null,
                    false,
                    newDiscount.getMaxDiscountPrice(),
                    newDiscount.getCreatedBy(),
                    newDiscount.getUpdatedBy(),
                    null,
                    newDiscount.getMaxAmount()
            );
            discountRepository.save(tblDiscountEntity);

        } catch (DataIntegrityViolationException ex){
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }
    @Transactional
    @Override
    public void update(TblDiscountEntity tblDiscountEntity){
        if (discountRepository.existsByCode(tblDiscountEntity.getCode())){
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, tblDiscountEntity.getCode()));
        }
        if (!discountRepository.existsById(tblDiscountEntity.getId())) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, tblDiscountEntity.getId()));
        }

        try {
            TblDiscountEntity discount = discountRepository.findById(tblDiscountEntity.getId()).orElse(null);

            if (discount != null) {
                discount.setCode(tblDiscountEntity.getCode());
                discount.setPercentage(tblDiscountEntity.getPercentage());
                discount.setMinAmount(tblDiscountEntity.getMinAmount());
                discount.setStartDate(tblDiscountEntity.getStartDate());
                discount.setEndDate(tblDiscountEntity.getEndDate());
                discount.setIsActive(tblDiscountEntity.getIsActive());
                discount.setUpdatedAt(Constants.getCurrentDay());
                discount.setMaxAmount(tblDiscountEntity.getMaxAmount());
                discount.setMaxDiscountPrice(tblDiscountEntity.getMaxDiscountPrice());

                discountRepository.save(discount);
            }
        } catch (NullPointerException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }


    @Override
    public void softDelete(int id) {
        if (!discountRepository.existsById(id)) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        try {
            discountRepository.deleteById(id);
            TblDiscountEntity discount = discountRepository.findById(id).orElse(null);
            if (discount != null) {
                discount.setDeleted(true);
                discount.setDeletedAt(Constants.getCurrentDay());
//                discount.setDeletedBy();
                discountRepository.save(discount);
            }
        } catch (DataAccessException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME, id) + ex.getMessage());
        }
    }
}
