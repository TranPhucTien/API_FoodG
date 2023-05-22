package com.nhom7.foodg.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encode {
    private int secret_key[] = {1,0,0,1,0,1,0,1};
    private int Call(int a, int b){
        if (a == 0 && b == 0) return 1;
        return 0;
    }
    private int Me(int a) {
        return Call(a, a);
    }
    private int I(int a, int b) {
        return Me(Call(a, b));
    }
    private int Am(int a, int b) {
        return Me(I(Me(a), Me(b)));
    }
    private int TDF(int a, int b) {
        return I(Am(a, Me(b)), Am(Me(a), b));
    }
    public String Encrypt(String input){
        String res="";
        for (int i = 0; i < input.length(); i++) {
            int tmp[] = {0,0,0,0,0,0,0,0};
            int zzz = 0;
            for (int b = 0; b < 8; b++) {

                int bit_1 = (input.charAt(i) >> b)&1;
                int bit_2 = secret_key[b];
                int rs = TDF(bit_1, bit_2);
                tmp[b] = rs;
            }
            for (int k = 7; k >= 0; k--) {
                zzz = (zzz << 1) + tmp[k];
            }
            String x = String.valueOf(zzz);
            res += x;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(res.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String Decrypt(String encrypt){
        String res = "";
        int count = 0;
        String tmp = "";
        for(int i = 0; i <= encrypt.length(); i++){
            if(count == 3){
                count = 0;
                res = res + (char)(Integer.valueOf(tmp) ^ 169);
                tmp = "";
            }
            if(i < encrypt.length()){
                tmp+=encrypt.charAt(i);
                count++;
            }
        }
        return res;
    }

}
