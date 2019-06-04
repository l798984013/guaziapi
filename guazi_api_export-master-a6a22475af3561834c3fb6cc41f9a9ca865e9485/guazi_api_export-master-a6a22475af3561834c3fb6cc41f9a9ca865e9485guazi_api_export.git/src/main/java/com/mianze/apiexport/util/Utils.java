package com.mianze.apiexport.util;

import com.mianze.apiexport.constant.Constant;


import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hexu on 2017/4/25.
 */
public class Utils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String nowDateStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    public static String nowDateStr2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }

    public static String nowTimeStr4(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date());
    }

    public static String afterHoursTimeStr(Date date, float hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, (int) (hours * 60));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        return format.format(new Date(calendar.getTimeInMillis()));
    }

    public static String afterMinutesTimeStr(Date date,int minutes){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE,minutes);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        return format.format(new Date(calendar.getTimeInMillis()));
    }

    public static String UTCTimeStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -8);
        calendar.setTimeZone(TimeZone.getDefault());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(calendar.getTimeInMillis()));
    }

    public static String nowTimeStr(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        return format.format(new Date());
    }

    public static String nowTimeStr2(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public static String nowTimeStr3(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(new Date());
    }

    public static String dateStr(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String dateStr2(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }



    public static String createRandomNumber(){
        return String.valueOf((new Random()).nextInt(9000) + 1000);
    }

    public static boolean isPhoneNumber(String str){
        if (str == null || str.length() != 11){
            return false;
        }
        Pattern pattern = Pattern.compile("^1\\d{10}$");
        return pattern.matcher(str).matches();
    }

    public static String getSign(long orderNo){
        return getMd5(orderNo + "e033f5d7af85dc20b8bb75fa101bdaf4");
    }

    public static boolean isSafePassword(String str){
        return !str.matches("[0-9]+") && !str.matches("[a-zA-Z]+");
    }


    public static boolean isBeofreToday(Date date){
        return dateStr(date).compareTo(nowDateStr()) < 0;
    }

    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder();
            for (byte aB : b) {
                i = aB;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            // e.printStackTrace();
            return null;
        }

    }


    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }

    public static String timeHM(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        return format.format(date);
    }


    public static String getZsdkSign(Object o, String key) throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.getName().equals("sign")){
                continue;
            }

            if (f.get(o) != null) {
                list.add(f.getName() + "=" +  URLEncoder.encode(f.get(o).toString(),"UTF-8") + "&");
            }else {
                list.add(f.getName() + "=&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = size-1; i >= 0; i--) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result = result.substring(0,result.length() - 1);
        return getMd5(result + key);
    }


}
