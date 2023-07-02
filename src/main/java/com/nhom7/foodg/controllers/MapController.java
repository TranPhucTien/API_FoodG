package com.nhom7.foodg.controllers;

import com.nhom7.foodg.models.FuncResult;
import com.nhom7.foodg.models.entities.MapEntity;
import com.nhom7.foodg.utils.MapUtil;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping
public class MapController {
    MapUtil mapUtil = new MapUtil();
    @GetMapping("map")
    //http://localhost:8080/map?address=26 đường láng
    //http://localhost:8080/map
    public ResponseEntity<FuncResult<MapEntity>> getDistance(@RequestParam(value = "address", required = false) String address) throws UnsupportedEncodingException {
        if(address == null || address == ""){
            String img = "<iframe src='https://mapfoodg.glitch.me/'></iframe>";
            MapEntity mapInformation = new MapEntity("0 phút", "0 km", img, 0);
            FuncResult<MapEntity> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Thanh Cong",
                    mapInformation
            );
            return ResponseEntity.ok(rs);
        }
        else {
            ArrayList<Double> origin = mapUtil.getLatLngByName("đại học xây dựng Hà Nội");
            ArrayList<Double> desti = mapUtil.getLatLngByName(address);
            ArrayList<String> res = mapUtil.getDistance(origin, desti);
            address = URLEncoder.encode(address, "UTF-8");
            String img = "<iframe src='https://mapfoodg.glitch.me/?destination=" + address + "'></iframe>";
            String distance = res.get(0);
            System.out.println(distance);
            String splitDistance;
            Double km;
            if(distance.indexOf(" km") != -1){ //kq trả về km
                splitDistance = distance.substring(0, distance.length() - 3);
                km = Double.parseDouble(splitDistance);
            }
            else{ // trả về m
                splitDistance = distance.substring(0, distance.length() - 2);
                km = Double.parseDouble(splitDistance)/1000;

            }
            System.out.println(splitDistance);

            double price = mapUtil.getPrice(km);

            MapEntity mapInformation = new MapEntity( res.get(1), res.get(0), img, price);
            FuncResult<MapEntity> rs = FuncResult.create(
                    HttpStatus.OK,
                    "Thanh Cong",
                    mapInformation
            );
            return ResponseEntity.ok(rs);
        }
    }

}
