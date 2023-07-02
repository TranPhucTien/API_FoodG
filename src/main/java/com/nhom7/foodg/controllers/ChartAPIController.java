package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.entities.*;
import com.nhom7.foodg.repositories.CategoryRepository;
import com.nhom7.foodg.repositories.InvoiceRepository;
import com.nhom7.foodg.repositories.LineRepository;
import com.nhom7.foodg.repositories.ProductRepository;
import com.nhom7.foodg.services.InvoiceService;
import com.nhom7.foodg.services.LineService;
import com.nhom7.foodg.utils.Statistics;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

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

    @GetMapping("/categoryPerDay")
    public ArrayList<MyObject> getCategoryPerDay(){ //ResponseEntity<FuncResult<ArrayList<Map<String, Integer>>>>
        Statistics statistics = new Statistics(invoiceService, lineService, lineRepository, productRepository, categoryRepository);
        Calendar calendar = Calendar.getInstance();

        ArrayList<Integer> listInvoice = statistics.getInvoiceByDate(statistics.ymdToString(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))); // listInvoice

        ArrayList<MyObject> rs = statistics.result(listInvoice);
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
