package com.nhom7.foodg.utils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

public class DataUtil {
    public static String generateTempPwd(int length) {
        String numbers = "012345678";
        char otp[] = new char[length];
        Random getOtpNum = new Random();
        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt(getOtpNum.nextInt(numbers.length()));
        }
        String optCode = "";
        for (int i = 0; i < otp.length; i++) {
            optCode += otp[i];
        }
        return optCode;
    }

    public static java.util.Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
