package com.nhom7.foodg.utils;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MapUtil {
    public ArrayList<Double> getLatLngByName(String address) throws UnsupportedEncodingException {
        address = URLEncoder.encode(address, "UTF-8");
        //address = address.replaceAll(" ","%20");
        try {
            String urlString = "https://rsapi.goong.io/geocode?address=" + address + "&api_key=MWBiaW9yAGrpxtJ3mL8PhPsZ8rqtYDthBURDNjr6";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            //BufferedReader rd = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(urlString)).openConnection()).getInputStream(), Charset.forName("UTF-8")));
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); //getInputStream => kết nối đến nguồn, InputStreamReader => chuyển sang đọc kí tự
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            JSONObject json = new JSONObject(sb.toString());
            double lat = json.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location")
                    .getDouble("lat");
            double lng = json.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location")
                    .getDouble("lng");
            ArrayList<Double> rs = new ArrayList<>();
            rs.add(lat);
            rs.add(lng);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<String> getDistance(ArrayList<Double> origin, ArrayList<Double> destination) {
        String url = "https://rsapi.goong.io/DistanceMatrix?origins=" + origin.get(0) + "," + origin.get(1)
                + "&destinations=" + destination.get(0) + "," + destination.get(1)
                + "&vehicle=bike&api_key=MWBiaW9yAGrpxtJ3mL8PhPsZ8rqtYDthBURDNjr6";

        System.out.println(url);

        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {
                JSONObject jsonObj = new JSONObject(output);

                JSONArray rows = jsonObj.getJSONArray("rows");
                JSONObject row = rows.getJSONObject(0);
                JSONArray elements = row.getJSONArray("elements");
                JSONObject element = elements.getJSONObject(0);
                JSONObject distance = element.getJSONObject("distance");
                String distanceStr = distance.getString("text");

                JSONObject duration = element.getJSONObject("duration");
                String durationStr = duration.getString("text");
                ArrayList<String> rs = new ArrayList<>();
                rs.add(distanceStr);
                rs.add(durationStr);
                return rs;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
