package com.nhom7.foodg.utils;

import com.nhom7.foodg.models.entities.*;
import com.nhom7.foodg.repositories.CategoryRepository;
import com.nhom7.foodg.repositories.LineRepository;
import com.nhom7.foodg.repositories.ProductRepository;
import com.nhom7.foodg.services.InvoiceService;
import com.nhom7.foodg.services.LineService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Statistics {
    InvoiceService invoiceService;
    LineService lineService;
    LineRepository lineRepository;

    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public Statistics(InvoiceService invoiceService, LineService lineService, LineRepository lineRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.invoiceService = invoiceService;
        this.lineService = lineService;
        this.lineRepository = lineRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Statistics() {
    }

    public String ymdToString(int year, int month, int day){
        String y, m, d;
        if(month < 10){
            m = "0" + month;
        }
        else{
            m = String.valueOf(month);
        }
        if(day < 10){
            d = "0" + day;
        }
        else{
            d = String.valueOf(day);
        }
        return year + "-" + m + "-" + d;
    }
    public ArrayList<Integer> getInvoiceByDate(String date){
        ArrayList<Integer> rs = new ArrayList<>();
        List<TblInvoiceEntity> ListInvoice = invoiceService.getAll();

        for(int i = 0; i < ListInvoice.size(); i++){
            TblInvoiceEntity tblInvoiceEntity = ListInvoice.get(i);
            Date dt = tblInvoiceEntity.getCreatedAt();
            String dtStr = dt.toString();
            //System.out.println(tblInvoiceEntity.getId() + " " + dtStr);
            if(dtStr.indexOf(date) != -1){
                rs.add(tblInvoiceEntity.getId());
                //System.out.println(tblInvoiceEntity.getId());
            }
        }
        return rs;
    }
    public TblCategoryEntity getCategoryById(int id){
        List<TblCategoryEntity> list = categoryRepository.findAll();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getId() == id){
                System.out.println(list.get(i));
                return list.get(i);
            }
        }
        return null;
    }
    public int contain(ArrayList<MyObject> list, String key){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getKey() == key){
                return i;
            }
        }
        return -1;
    }
    public ArrayList<MyObject> result(ArrayList<Integer> listInvoice){
        ArrayList<MyObject> rs = new ArrayList<>();
        //ArrayList<TblCategoryEntity> rs = new ArrayList<>();
        List<TblLineEntity> ListLine = lineService.getAll();
        for(int i = 0; i < listInvoice.size(); i++){
            List<TblLineEntity> lineByInvoice = lineRepository.findByIdInvoice(listInvoice.get(i));
            for(int j = 0; j < lineByInvoice.size(); j++){
                String productId = lineByInvoice.get(j).getIdProduct();
                int quantity = lineByInvoice.get(j).getQuantity();
                TblProductEntity tblProductEntity = productRepository.getProductByID(productId);
                Integer categoryId = tblProductEntity.getIdCategory();
                System.out.println(categoryId);
                if(categoryId != null) {
                    int id = categoryId.intValue();
                    TblCategoryEntity tblCategoryEntity = getCategoryById(id);
                    if(tblCategoryEntity!=null){
                        String name = tblCategoryEntity.getName();
                        if(contain(rs, name) != -1){
                            int a = contain(rs, name);
                            MyObject myObject = rs.get(a);
                            int value = myObject.getVal();
                            rs.get(a).setVal(value + quantity);
                        }
                        else{
                            MyObject myObject = new MyObject(name, quantity);
                            rs.add(myObject);

                        }
                    }
                    //rs.add(tblCategoryEntity);
                }
            }
        }
        return rs;
    }
}
