package com.mianze.apiexport.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 身份证信息算法类
 *
 * @author javaweb
 *
 */

public class IdcardInfoExtractor {

    /**
     * 根据身份证的号码算出当前身份证持有者的性别和年龄,以及户籍 18位身份证
     *
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> getCardInfo(String idCard) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String year = idCard.substring(6,10);// 得到年份
        String month = idCard.substring(10,12);// 得到月份
        String day=idCard.substring(12,14);//得到日

        String sex;
        if (Integer.parseInt(idCard.substring(16,17)) % 2 == 0) {// 判断性别
            sex = "女";
        } else {
            sex = "男";
        }
        Date date = new Date();// 得到当前的系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fyear = format.format(date).substring(0, 4);// 当前年份
        String fmonth = format.format(date).substring(5, 7);// 月份
        String fday=format.format(date).substring(8,10);
        int age = 0;
        if (Integer.parseInt(month) < Integer.parseInt(fmonth)) { // 当前月份大于用户出身的月份表示已过生
            age = Integer.parseInt(fyear) - Integer.parseInt(year);
        } else if(Integer.parseInt(month) > Integer.parseInt(fmonth)){// 当前用户还没过生
            age = Integer.parseInt(fyear) - Integer.parseInt(year) - 1;
        }else {
            //当前月份等于用户出身的月份,需要再比较日
            if (Integer.parseInt(day) <= Integer.parseInt(fday)) { //出生日期小于等于当前日期,已过生日
                age = Integer.parseInt(fyear) - Integer.parseInt(year);
            }else { //出生日期大于当前日期,没过生日
                age = Integer.parseInt(fyear) - Integer.parseInt(year) -1;
            }
        }

        map.put("sex", sex);
        map.put("age", age);
        return map;
    }
}