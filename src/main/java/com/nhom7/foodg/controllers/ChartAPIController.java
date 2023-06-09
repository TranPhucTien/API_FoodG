package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.entities.TblInvoiceEntity;
import com.nhom7.foodg.repositories.InvoiceRepository;
import com.nhom7.foodg.services.InvoiceService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    InvoiceRepository invoiceRepository;

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
//    @GetMapping("/categoryPerDay")
//    public ResponseEntity<FuncResult<ArrayList<Map<String, Integer>>>> getCategoryPerDay(){
//        //List<TblInvoiceEntity> ListInvoice = invoiceService.getAll();
//        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());
//        ArrayList<Integer> listInvoice = getInvoiceByDate(date);
//
//    }

    @GetMapping("/categoryPerDay")
    public ArrayList<MyObject>  returnApi(){
        Map<String, Integer> x = new HashMap<>();
        x.put("TDF", 123);
        x.put("TTT", 321);
        x.put("T", 123);
        x.put("D", 112);
        x.put("F", 111);
        x.put("R", 231);
        x.put("A", 12);
        x.put("Z", 99);
        ArrayList<MyObject> arr = new ArrayList<MyObject>();

        for(Map.Entry<String, Integer> entry : x.entrySet()){
            MyObject a = new MyObject(entry.getKey(), entry.getValue());
            arr.add(a);
        }
        return arr;
    }
}
