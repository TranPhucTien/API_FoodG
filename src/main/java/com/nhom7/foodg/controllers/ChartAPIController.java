package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.entities.TblCategoryEntity;
import com.nhom7.foodg.models.entities.TblInvoiceEntity;
import com.nhom7.foodg.models.entities.TblLineEntity;
import com.nhom7.foodg.models.entities.TblProductEntity;
import com.nhom7.foodg.repositories.CategoryRepository;
import com.nhom7.foodg.repositories.InvoiceRepository;
import com.nhom7.foodg.repositories.LineRepository;
import com.nhom7.foodg.repositories.ProductRepository;
import com.nhom7.foodg.services.InvoiceService;
import com.nhom7.foodg.services.LineService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
class MyObject{
    public String key;
    public Integer val;
    public MyObject(String key, Integer val) {
        this.key = key;
        this.val = val;
    }
    public MyObject() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }
}
@RestController
@RequestMapping
public class ChartAPIController {
    InvoiceService invoiceService;
    LineService lineService;
    LineRepository lineRepository;

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public ChartAPIController(InvoiceService invoiceService, LineService lineService, LineRepository lineRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.invoiceService = invoiceService;
        this.lineService = lineService;
        this.lineRepository = lineRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    //    @GetMapping("/ordersPerDayOfWeek")
//    public ResponseEntity<FuncResult<ArrayList<Map<Date, Integer>>>> getNumberOfOrdersPerDay(){
//        FuncResult<ArrayList<Map<Date, Integer>>> rs;
//
//        return rs;
//    }
//    public ArrayList<Integer> getInvoiceByDate(Date date){
//        List<TblInvoiceEntity> ListInvoice = invoiceService.getAll();
//        ArrayList<Integer> result = new ArrayList<>();
//        for(TblInvoiceEntity invoice : ListInvoice){
//            if(invoice.getInvoiceDate() == date){
//                result.add(invoice.getId());
//            }
//        }
//        return result;
//    }
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
    @GetMapping("/categoryPerDay")
    public ArrayList<MyObject> getCategoryPerDay(){ //ResponseEntity<FuncResult<ArrayList<Map<String, Integer>>>>
        Calendar calendar = Calendar.getInstance();
        ArrayList<Integer> listInvoice = getInvoiceByDate(ymdToString(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))); // listInvoice
        ArrayList<MyObject> rs = result(listInvoice);
        return rs;
    }

//    @GetMapping("/categoryPerDay")
//    public ArrayList<MyObject>  returnApi(){
//        Map<String, Integer> x = new HashMap<>();
//        x.put("TDF", 123);
//        x.put("TTT", 321);
//        x.put("T", 123);
//        x.put("D", 112);
//        x.put("F", 111);
//        x.put("R", 231);
//        x.put("A", 12);
//        x.put("Z", 99);
//        ArrayList<MyObject> arr = new ArrayList<MyObject>();
//
//        for(Map.Entry<String, Integer> entry : x.entrySet()){
//            MyObject a = new MyObject(entry.getKey(), entry.getValue());
//            arr.add(a);
//        }
//        return arr;
//    }
}
