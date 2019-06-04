package com.mianze.apiexport.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.mianze.apiexport.constant.Constant;
import com.mianze.apiexport.pojo.*;

import com.mianze.apiexport.util.houben.Base64Utils;
import com.mianze.apiexport.util.houben.EncodeUtil;

import io.netty.handler.codec.base64.Base64Encoder;
import okhttp3.FormBody;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {


    public static String covert(String test) {
        test = test.replace("\\x", "");

        byte[] b = new byte[test.length() / 2];// 每两个字符为一个十六进制确定数字长度
        for (int i = 0; i < b.length; i++) {
            // 将字符串每两个字符做为一个十六进制进行截取
            String a = test.substring(i * 2, i * 2 + 2);
            b[i] = (byte) Integer.parseInt(a, 16);// 将如e4转成十六进制字节，放入数组
        }
        try {
            // 将字节数字以utf-8编码以字符串形式输出
            return new String(b, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    interface ITest {
        int add(int a, int b);
    }


    public static void main(String[] args) throws Exception {
        LoanInfo loanInfo = new LoanInfo();
        loanInfo.setUserName("朱静");
        loanInfo.setTerm(12);
        loanInfo.setZmxy("592");
        loanInfo.setLocation("上海");
        loanInfo.setPhone("18361548485");
        //loanInfo.setIdCard("320534199211294392");
        loanInfo.setJob(1);
        loanInfo.setCompanyType(4);
        loanInfo.setMonthlyIncome(7000);
        loanInfo.setCar(1);
        loanInfo.setDegree(2);
        loanInfo.setIncome(3);
        loanInfo.setWorkYear(3);
        loanInfo.setRegisterTime(6);
        loanInfo.setAge(34);
        loanInfo.setSex("男");
        loanInfo.setMarried(2);
        loanInfo.setHouse(1);
        loanInfo.setWebank("4");
        loanInfo.setCarProperty(2);
        loanInfo.setHouseProperty(2);
        loanInfo.setSheBao(1);
        loanInfo.setGongJiJin(1);
        loanInfo.setChit(3);
        loanInfo.setStatus(2);
        loanInfo.setAmount(5);
        loanInfo.setIp("112.96.109.201");
        /*XiaoDouModel model = new XiaoDouModel();
        model.setChannelId("rongle_a");
        model.setCity("苏州");
        model.setProvince("江苏");
        model.setName(loanInfo.getUserName());
        model.setMoney(Integer.toString(loanInfo.getAmount()));*/

       /* YiCaiDao model = new YiCaiDao();
        model.setIP(loanInfo.getIp());

        String idCard = loanInfo.getIdCard();
        String year = idCard.substring(6, 10);// 得到年份
        String month = idCard.substring(10, 12);// 得到月份
        String day = idCard.substring(12, 14);//得到日
        String birthday = year + "-" + month + "-" + day;
        String v = Utils.getMd5(birthday + loanInfo.getPhone() + "2703" + "ycd2kals4qpc7p9Loan");
        //final String url = "http://beta.yicaidao.cn/api/loan/reg?c=2703&t=loan&v=" + v;
        final String url = Constant.DaJinDaiUrl + v;*/
        // final String url = Constant.QianHuiUrl;
/*        LieBao model = new LieBao();
        model.setApplyName(loanInfo.getUserName());
        model.setPhone(Long.parseLong(loanInfo.getPhone()));
        model.setAge(loanInfo.getAge());
        model.setAmount(loanInfo.getAmount());
        if (loanInfo.getTerm()==1){
            model.setLoanPeriod(1);
        }else if (loanInfo.getTerm()==3){
            model.setLoanPeriod(2);
        }else if (loanInfo.getTerm()>3 && loanInfo.getTerm()<=6){
            model.setLoanPeriod(3);
        }else if (loanInfo.getTerm()>6 && loanInfo.getTerm()<=12){
            model.setLoanPeriod(4);
        }else if (loanInfo.getTerm()>12 && loanInfo.getTerm()<=24){
            model.setLoanPeriod(5);
        }else if (loanInfo.getTerm()>24 && loanInfo.getTerm()<=36){
            model.setLoanPeriod(6);
        }else{
            model.setLoanPeriod(7);
        }

        if (loanInfo.getJob()==1){
            model.setJobType(6);
            model.setIncome(loanInfo.getMonthlyIncome());
        }else{
            model.setJobType(5);
        }

        if (loanInfo.getStatus()==1 ||loanInfo.getStatus()==6){
            model.setCredit(1);
        }else if (loanInfo.getStatus()==2){
            model.setCredit(4);
        }else if (loanInfo.getStatus()==3 || loanInfo.getStatus()==4 || loanInfo.getStatus()==5){
            model.setCredit(3);
        }

        if (loanInfo.getHouseProperty()==1|| loanInfo.getHouseProperty()==-1){
            model.setHouseProperty(7);
        }else{
            model.setHouseProperty(2);
        }

        if (loanInfo.getCarProperty()==1|| loanInfo.getCarProperty()==-1){
            model.setVehicleProperty(1);
        }else{
            model.setVehicleProperty(3);
        }

        if (loanInfo.getSheBao()==1 || loanInfo.getSheBao()==5){
            model.setSocialSecurity(0);
        }else{
            model.setSocialSecurity(1);
        }

        if (loanInfo.getGongJiJin()==1 || loanInfo.getGongJiJin()==5){
            model.setHousingFund(0);
        }else{
            model.setHousingFund(1);
        }

        if (loanInfo.getIdCard()!=null && !"-1".equals(loanInfo.getIdCard())) {
            model.setId(loanInfo.getIdCard());
        }

        if (loanInfo.getJob()==1){
            if (loanInfo.getCompanyType()==1){
                model.setCompanyType(2);
            }else if(loanInfo.getCompanyType()==2||loanInfo.getCompanyType()==3){
                model.setCompanyType(1);
            }else {
                model.setCompanyType(5);
            }

            if (loanInfo.getWorkYear()==1){
                model.setWorkYears(3);
            }else if (loanInfo.getWorkYear()==2){
                model.setWorkYears(1);
            }else if (loanInfo.getWorkYear()==3){
                model.setWorkYears(4);
            }

            if (loanInfo.getIncome()==1){
                model.setSalaryType(1);
            }else if (loanInfo.getIncome()==2){
                model.setSalaryType(3);
            }else if (loanInfo.getIncome()==3){
                model.setSalaryType(2);
            }
        }

        if (loanInfo.getChit()==1 ||loanInfo.getChit()==2){
            model.setLifeInsurance(0);
        }else{
            model.setLifeInsurance(1);
        }

        model.setSource("xxdp50802");
        model.setCityId(320100);
        if (loanInfo.getWebank().equals("无")||loanInfo.getWebank().equals("-1")||loanInfo.getWebank()==null){
            model.setTinyLoa(0);
        }else {
            model.setTinyLoa(1);
        }

        model.setOnlineStore(0);
        model.setTravelInsurance(3);
        model.setIp(loanInfo.getIp());

        String json = GsonUtil.toString(model);
        System.out.println(json+"++++++++++++++++++++");
        // byte[] sendByte = EncodeUtil.des3EncodeECB(Constant.signKey,json.getBytes("utf-8"));
        String encryptStr = Util.encrypt("550e08c4acf7610c","46d2d231e2ba50bd", Base64.encodeBase64String(json.getBytes()));
        System.out.println(encryptStr+"=============");
        FormBody formBody = new FormBody.Builder()
                .add("data", encryptStr)
                .add("source","xxdp50802")
//
                .build();
        String result = HttpUtil.post(Constant.LieBaoUrl,formBody);*/
        int max=200000;
        int min=10;
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        System.out.println(s+"===========");
        String url = "https://apigateway.api.qcloud.com/v2/index.php?secretName=test&Action=CreateApiKey" +
                "&SecretId=AKIDXXXXWKipDlK &Region=sh&Timestamp="+Long.toString(new Date().getTime())+"&Nonce="+s+"&Signature=mysignature";
        String result = HttpUtil.get(url);
        System.out.println(result+"******");

       /* result = Utils.unicodeToString(result);
        JingLeiModelResponse response = GsonUtil.gson().fromJson(result, JingLeiModelResponse.class);
        System.out.println(response.getErrno());*/
//        XDUDResponse response = GsonUtil.gson().fromJson(result, XDUDResponse.class);
//        System.out.println(response);
//        System.out.println(response.getData());
      //  String result = HttpUtil.post(Constant.QianHuiUrl,formBody);

     /*   try {
            String returnStr = Base64Utils.decodeBuffer(result,"utf-8");
            JSONObject returnObject = JSON.parseObject(returnStr);
            String payload = returnObject.get("returnData").toString();
            byte[] returnByte = Util(payload);
           // byte[] decryptByte = EncodeUtil.ees3DecodeECB(Constant.signKey,returnByte);
             String decryptStr = new String(returnByte,"UTF-8");
            QianHuiResponse response = GsonUtil.gson().fromJson(decryptStr,QianHuiResponse.class);
            String msg = response.getMessage();
            System.out.println(msg);
          *//*  if ("0".equals(response.getRescode())){
                redisOperator.setRestrictAmount(channelExport.getId());
                apiService.addQianHuiResult(msg,0, loanInfo.getOrderNo());
                return 0;
            }else if ("2490373".equals(response.getRescode())){
                apiService.addQianHuiResult( msg, 1, loanInfo.getOrderNo());
                return 1;
            }else {
                apiService.addQianHuiResult( msg, 2, loanInfo.getOrderNo());
                return 1;
            }*//*
        }catch (Exception e){
          *//*  log.error("千惠管家JSON异常",e);

            apiService.addQianHuiResult("JSON异常",4,loanInfo.getOrderNo());

            return 2;*//*
          System.out.println("千惠管家JSON异常");
        }

        System.out.println(result);*/

//        Calendar now = Calendar.getInstance();
//        now.add(Calendar.HOUR,8);
//        String timeHM = Utils.timeHM(now.getTime());
//        System.out.println(timeHM);
//        boolean after1750 = timeHM.compareTo("17:50") > 0;
//        System.out.println("after1750: "  + after1750);
//        boolean before0610 = timeHM.compareTo("06:10") < 0;
//        System.out.println("before0610: "  + before0610);
//        if (after1750 || before0610) {
//            if (after1750) {
//                now.add(Calendar.HOUR, 24);
//            }
//            System.out.println(Utils.dateStr2(now.getTime()));
//        }

//        JSONArray jsonArray = JSONArray.parseArray(Test2.json);
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONObject object = jsonArray.getJSONObject(i);
//            String orderNo = object.getString("order_no");
//            String phone = object.getString("loan_phone");
//
//            System.out.println(orderNo + ",");
//            //   System.out.println("UPDATE loan_log SET order_no = " + orderNo + " WHERE phone = " + phone + " AND create_time >= '2018-09-13 10:30:00' AND create_time <= '2018-09-13 12:00:00' AND result = 2 AND order_no is null;");
//
//        }


//        try {
//            String sign = RsaCryptoHelper.rsaCryptoHelper.sign("70b986c9a2314ffc9cbf60211208181d" + "2018-09-12 06:10:00");
//            System.out.println(sign);
//            String sign2 = RsaCryptoHelper.rsaCryptoHelper.sign("15161708513","张斌","32082919930907043X");
//            System.out.println(sign2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//

//
//        EpeModel model = new EpeModel();
//        model.setMec_id("272b278ae26548fb63f9222d4de34d7a");
//        //城市
//        model.setCity("440100");
//        model.setProvince("440000");
//        model.setRequest_time(Integer.parseInt(((Long)System.currentTimeMillis()).toString().substring(0,10)));
//        model.setPhone(loanInfo.getPhone());
//        model.setReal_name(loanInfo.getUserName());
//        model.setId_card(loanInfo.getIdCard());
//        if (loanInfo.getJob() == 1){
//            model.setJob_type(1);
//        }else if (loanInfo.getJob() == 3){
//            model.setJob_type(3);
//        }else {
//            model.setJob_type(5);
//        }
//
//        if (model.getJob_type() == 1){
//            Integer companyType = loanInfo.getCompanyType();
//            if (companyType == 1){
//                model.setCom_type(3);
//            }else if (companyType == 2){
//                model.setCom_type(2);
//            }else if (companyType == 3){
//                model.setCom_type(1);
//            }else {
//                model.setCom_type(5);
//            }
//        }
//
//        model.setAfter_tax_income(loanInfo.getMonthlyIncome());
//        if (loanInfo.getIncome() != null && loanInfo.getIncome() == 3){
//            model.setSalary_type(2);
//        }else {
//            model.setSalary_type(1);
//        }
//
//        if (loanInfo.getWorkYear() != null && loanInfo.getWorkYear() == 3){
//            model.setWork_month(6);
//        }else {
//            model.setWork_month(3);
//        }
//
//        if (loanInfo.getSheBao() == 3 || loanInfo.getSheBao() == 4){
//            model.setSb(1);
//        }else {
//            model.setSb(0);
//        }
//
//        if (loanInfo.getGongJiJin() == 3 || loanInfo.getGongJiJin() == 4){
//            model.setGjj(1);
//        }else {
//            model.setGjj(0);
//        }
//
//        model.setUse_phone_6(0);
//        model.setLoan_amount(loanInfo.getAmount() * 10000);
//        model.setLoan_expire(loanInfo.getTerm());
//
//        if (loanInfo.getStatus() == 1 || loanInfo.getStatus() == 6){
//            model.setCredit_card(0);
//            model.setCredit_info(1);
//            model.setIs_overing(0);
//        }else if (loanInfo.getStatus() == 2){
//            model.setCredit_card(1);
//            model.setCredit_info(2);
//            model.setIs_overing(0);
//        }else {
//            model.setCredit_card(1);
//            model.setCredit_info(3);
//            model.setIs_overing(1);
//        }
//
//        if (loanInfo.getCarProperty() != 1){
//            model.setCar(1);
//            model.setCar_time(0);
//            if (loanInfo.getCarAssessment() != null){
//                switch (loanInfo.getCarAssessment()){
//                    case 1:
//                        model.setCar_value(10);
//                        break;
//                    case 2:
//                        model.setCar_value(20);
//                        break;
//                    case 3:
//                        model.setCar_value(30);
//                        break;
//                    case 4:
//                        model.setCar_value(50);
//                        break;
//                    case 5:
//                        model.setCar_value(60);
//                        break;
//                    default:
//                        model.setCar_value(0);
//                        break;
//                }
//            }else {
//                model.setCar_value(0);
//            }
//        }else {
//            model.setCar(0);
//        }
//
//        if (loanInfo.getHouseProperty() != 1){
//            model.setHouse(1);
//            model.setHouse_time(0);
//            if (loanInfo.getHouseAssessment() != null){
//                switch (loanInfo.getHouseAssessment()){
//                    case 1:
//                        model.setHouse_value(50);
//                        break;
//                    case 2:
//                        model.setHouse_value(100);
//                        break;
//                    case 3:
//                        model.setHouse_value(200);
//                        break;
//                    case 4:
//                        model.setHouse_value(500);
//                        break;
//                    case 5:
//                        model.setHouse_value(1000);
//                        break;
//                    default:
//                        model.setHouse_value(0);
//                        break;
//                }
//            }else {
//                model.setHouse_value(0);
//            }
//        }else {
//            model.setHouse(0);
//        }
//
//        if (loanInfo.getChit() == 0){
//            model.setIs_insur(1);
//            model.setInsur_month(0);
//            model.setInsur_fee(0);
//        }else {
//            model.setIs_insur(0);
//        }
//
//
//
//        try {
//            if (loanInfo.getZmxy() == null || loanInfo.getZmxy().equals("") || "无芝麻分".equals(loanInfo.getZmxy())) {
//                model.setZm(0);
//            }else if (loanInfo.getZmxy().length() > 3) {
//                String zm = loanInfo.getZmxy().split("-")[0];
//                model.setZm(Integer.parseInt(zm));
//            }else {
//                model.setZm(Integer.parseInt(loanInfo.getZmxy()));
//            }
//        }catch (Exception e){
//            model.setZm(0);
//        }
//
//        try {
//            if (loanInfo.getWebank() == null || loanInfo.getWebank().equals("") || loanInfo.getWebank().equals("无")){
//                model.setWld(0);
//            }else {
//                BigDecimal weiLiDai = new BigDecimal(loanInfo.getWebank().substring(0, loanInfo.getWebank().length() - 1));
//                Integer wld = weiLiDai.multiply(new BigDecimal("10000")).intValue();
//                model.setWld(wld);
//            }
//        }catch (Exception e){
//            model.setWld(0);
//        }
//
//        model.setJb_quota(0);
//
//        try {
//
//
//            String content = "2b4d7f1145f7afa2a71dfe344e8ef572" + model.getRequest_time()
//                    + model.getPhone() + model.getReal_name() + model.getId_card() + model.getLoan_amount()
//                    + model.getLoan_expire();
//            String sign = Utils.getMd5(content);
//            model.setSign(sign);
//            FormBody.Builder builder = new FormBody.Builder();
//            Field[] declaredFields = model.getClass().getDeclaredFields();
//            for (Field field : declaredFields) {
//                field.setAccessible(true);
//                Object o = field.get(model);
//                if (o != null) {
//                    builder.add(field.getName(), o.toString());
//                }
//            }
//            builder.add("ip", loanInfo.getIp());
//
//
//            String response = HttpUtil.post(Constant.epeUrl, builder.build());
//            System.out.println(response);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        String weili;
//        if (loanInfo.getWebank().equals("无") || loanInfo.getWebank().equals("暂未填写")){
//            weili = "0";
//        }else if (loanInfo.getWebank().equals("有")){
//            weili = "1";
//        }else {
//            try{
//                int weilidai = (int) (Float.parseFloat(loanInfo.getWebank())*10000);
//                if (weilidai < 10000){
//                    weili = "1";
//                }else if (weilidai < 20000){
//                    weili = "2";
//                }else if (weilidai < 30000){
//                    weili = "3";
//                }else if (weilidai < 50000){
//                    weili = "4";
//                }else{
//                    weili = "5";
//                }
//            }catch (Exception e){
//                weili = "0";
//            }
//        }
//
//
//
//        FormBody formBody  = new FormBody.Builder()
//                .add("name",loanInfo.getUserName())
//                .add("mobile",loanInfo.getPhone())
//                .add("city",loanInfo.getLocation().replace("市",""))
//                .add("car",loanInfo.getCarProperty() > 1 ? "有":"无")
//                .add("age",loanInfo.getAge().toString())
//                .add("job","有")
//                .add("house",loanInfo.getHouseProperty() > 1 ? "有":"无")
//                .add("baodan_is",loanInfo.getChit() == 0 ? "有":"无")
//                .add("sex",loanInfo.getSex())
//                .add("money",loanInfo.getAmount().toString())
//                .add("source","rongle")
//                .add("shebao",(loanInfo.getSheBao() > 1 && loanInfo.getSheBao() < 5 ) ? "有":"无")
//                .add("gongjijin",(loanInfo.getGongJiJin() > 1 && loanInfo.getGongJiJin() < 5 ) ? "有":"无")
//                .add("isbankpay",(loanInfo.getIncome() != null && loanInfo.getIncome() == 1) ? "是":"否")
//                .add("ip",loanInfo.getIp())
//                .add("credit_card",(loanInfo.getStatus() > 1 && loanInfo.getStatus() < 6) ? "有":"无")
//                .add("meiti","rongle")
//                .add("time",String.valueOf(System.currentTimeMillis()/1000))
//                .add("weili",weili)
//                .build();
//        String res = HttpUtil.post(Constant.zhudaiwangUrl,formBody);
//        System.out.println(res);
//
//        String param = "token=" + Constant.ppdToken  + "&phone=" + loanInfo.getPhone();
//        String sign = Utils.getMd5(param + "&paramMd5=" + Utils.getMd5(param));
//
//        int term;
//        if (loanInfo.getTerm() <= 7){
//            term = 7;
//        }else if (loanInfo.getTerm() >= 12){
//            term = 12;
//        }else {
//            term = loanInfo.getTerm();
//        }
//
//        PaiPaiDai model = new PaiPaiDai();
//        model.setZhiye(loanInfo.getJob() == 1 ? "上班族":(loanInfo.getJob() == 2 ? "自由职业":"企业主"));
//        model.setApplyCity(loanInfo.getLocation());
//        switch (loanInfo.getSheBao()){
//            case 1:
//                model.setShebao("无社保");
//                break;
//            case 2:
//                model.setShebao("1-5个月");
//                break;
//            case 3:
//                model.setShebao("连续6个月以上");
//                break;
//            case 4:
//                model.setShebao("连续12个月以上");
//                break;
//            case 5:
//                model.setShebao("暂未填写");
//                break;
//        }
//        switch (loanInfo.getHouseProperty()){
//            case 1:
//                model.setFangchan("无房产");
//                break;
//            case 2:
//                model.setFangchan("有房产,接受抵押");
//                break;
//            case 3:
//                model.setFangchan("有房产,不接受抵押");
//                break;
//            case 4:
//                model.setFangchan("有房产");
//                break;
//        }
//
//        switch (loanInfo.getCarProperty()){
//            case 1:
//                model.setChechan("无车辆");
//                break;
//            case 2:
//                model.setChechan("有车辆,接受抵押");
//                break;
//            case 3:
//                model.setChechan("有车辆,不接受抵押");
//                break;
//            case 4:
//                model.setChechan("有车辆");
//                break;
//        }
//
//        FormBody formBody = new FormBody.Builder()
//                .add("ChannelId","365")
//                .add("SourceId","541")
//                .add("token",Constant.ppdToken)
//                .add("sign",sign)
//                .add("phone",loanInfo.getPhone())
//                .add("userName",loanInfo.getUserName())
//                .add("shenfenzh",loanInfo.getIdCard())
//                .add("applyLoanAmount",String.valueOf(loanInfo.getAmount()*10000))
//                .add("applyLoanMonth",String.valueOf(term))
//                .add("daikuanyt","消费贷款")
//                .add("info",GsonUtil.toString(model))
//                .build();
//        String res = HttpUtil.post(Constant.paipaidaiUrl,formBody);
//        System.out.println(res);
//
//        int zmxy = 0;
//
//        if (loanInfo.getZmxy().contains("-")){
//            try {
//                String[] a = loanInfo.getZmxy().split("-");
//                zmxy = Integer.parseInt(a[1]);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }else {
//            try {
//                zmxy = Integer.parseInt(loanInfo.getZmxy());
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//
//
//        String city = loanInfo.getLocation().replace("市","");
//
//
//        String timestamp = Utils.nowTimeStr4();
//        String phone = loanInfo.getPhone();
//        String sign = Utils.getMd5( phone + Constant.jinrongweidianChannel + timestamp + Constant.jinrongweidianKey);
//
//        //自由职业传4 对应个体户
//        //有信用卡传1 其余传0
//        //没有的字段可以不传
//        String category;
//        if (loanInfo.getJob() == 1){
//            category = "1";
//        }else if (loanInfo.getJob() == 2){
//            category = "4";
//        }else {
//            category = "2";
//        }
//        FormBody.Builder builder = new FormBody.Builder()
//                .add("mobile",phone)
//              //  .add("real_name",loanInfo.getUserName())
//                .add("city",city)
//                .add("channel",Constant.jinrongweidianChannel)
//                .add("timestamp",timestamp)
//                .add("sign",sign)
//                .add("age",loanInfo.getAge().toString())
//                .add("category",category)
//                .add("id_no",loanInfo.getIdCard())
//                .add("apply_amount",String.valueOf(loanInfo.getAmount()*10000))
//                .add("zm_score", Integer.toString(zmxy))
//                .add("house",loanInfo.getHouseProperty() == 1 ? "1":"12")
//                .add("ip",loanInfo.getIp())
//                .add("gender",loanInfo.getSex().equals("男") ? "1":"2")
//                .add("apply_time_limit","12");
//        if (loanInfo.getMarried() == 0){
//            builder.add("marriage","0");
//        }else if (loanInfo.getMarried() == 1){
//            builder.add("marriage","1");
//        }
//        if (loanInfo.getIncome() != null) {
//            if (loanInfo.getIncome() == 1) {
//                builder.add("pay_method","1");
//            } else if (loanInfo.getIncome() == 2) {
//                builder.add("pay_method","3");
//            } else if (loanInfo.getIncome() == 3) {
//                builder.add("pay_method","2");
//            }
//        }
//
//        if (loanInfo.getWorkYear() != null){
//            if (loanInfo.getWorkYear() == 3){
//                builder.add("work_seniority","2");
//            }else {
//                builder.add("work_seniority","1");
//            }
//        }
//        if (loanInfo.getCompanyType() != null){
//            if (loanInfo.getCompanyType() == 1){
//                builder.add("company_type","2");
//            }else if (loanInfo.getCompanyType() == 2 || loanInfo.getCompanyType() == 3){
//                builder.add("company_type","1");
//            }
//        }
//
//        if (loanInfo.getRegisterTime() != null){
//            if (loanInfo.getRegisterTime() == 1){
//                builder.add("operat_life","2");
//            }else if (loanInfo.getRegisterTime() == 2 || loanInfo.getRegisterTime() == 3){
//                builder.add("operat_life","3");
//            }else if (loanInfo.getRegisterTime() == 4){
//                builder.add("operat_life","4");
//            }else if (loanInfo.getRegisterTime() == 5){
//                builder.add("operat_life","5");
//            }
//        }
//        if (loanInfo.getWebank().equals("无")){
//            builder.add("weilidai","0");
//        }else if (!"暂未填写".equals(loanInfo.getWebank())){
//            builder.add("weilidai","1");
//        }
//        if (loanInfo.getMonthlyIncome() != null){
//            if (loanInfo.getMonthlyIncome() <= 1500){
//                builder.add("salary","1");
//            }else if (loanInfo.getMonthlyIncome() <= 3000){
//                builder.add("salary","2");
//            }else if (loanInfo.getMonthlyIncome() <= 5000){
//                builder.add("salary","3");
//            }else if (loanInfo.getMonthlyIncome() <= 10000){
//                builder.add("salary","4");
//            }else if (loanInfo.getMonthlyIncome() <= 15000){
//                builder.add("salary","5");
//            }else if (loanInfo.getMonthlyIncome() <= 20000){
//                builder.add("salary","6");
//            }else {
//                builder.add("salary","7");
//            }
//        }
//
//        if (loanInfo.getCarProperty() == 1){
//            builder.add("car","6");
//        }else if (loanInfo.getCar() == null){
//            builder.add("car","5");
//        }else if (loanInfo.getCar() == 1){
//            builder.add("car","2");
//        }else if (loanInfo.getCar() == 2){
//            builder.add("car","1");
//        }else if (loanInfo.getCar() == 3){
//            builder.add("car","4");
//        }
//
//        if (loanInfo.getSheBao() == 1){
//            builder.add("social_security","6");
//        }else if (loanInfo.getSheBao() == 2){
//            builder.add("social_security","2");
//        }else if (loanInfo.getSheBao() == 3){
//            builder.add("social_security","3");
//        }else if (loanInfo.getSheBao() == 4){
//            builder.add("social_security","4");
//        }else {
//            builder.add("social_security","0");
//        }
//
//        if (loanInfo.getGongJiJin() == 1){
//            builder.add("fund_month","6");
//        }else if (loanInfo.getGongJiJin() == 2){
//            builder.add("fund_month","2");
//        }else if (loanInfo.getGongJiJin() == 3){
//            builder.add("fund_month","3");
//        }else if (loanInfo.getGongJiJin() == 4){
//            builder.add("fund_month","4");
//        }else {
//            builder.add("fund_month","0");
//        }
//
//        if (loanInfo.getChit() == 0){
//            builder.add("insurance","1");
//        }else if (loanInfo.getChit() == 1){
//            builder.add("insurance","3");
//        }else {
//            builder.add("insurance","0");
//        }
//
//       // String res = HttpUtil.post(Constant.jinrongweidianUrl,builder.build());
//       // System.out.println(res);
//        String s = "\\u5fc5\\u4f20\\u5b57\\u6bb5\\u5b58\\u5728\\u4e3a\\u7a7a\\u6570\\u636e";
//        System.out.println(Utils.unicodeToString(s));
//        System.out.println(Utils.unicodeToString("success"));
//        String a = "123456a bb订单";
//        String md1 = Utils.getMd5(a);
//        System.out.println(md1);
//        String md2 = DigestUtils.md5DigestAsHex(a.getBytes());
//        System.out.println(md2);


//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("token","OZ6Ac185lyLc8PQkybWSgiRYYbR1I1Q9");
//        jsonObject.put("sub_source","h5");
//        jsonObject.put("channel","h5");
//        jsonObject.put("district_id",310100);
//        jsonObject.put("name","张三");
//        jsonObject.put("phone_district_id",310100);
////        jsonObject.put("phone","13123456789");
////        jsonObject.put("id_number","412825199311111040");
//        jsonObject.put("gender","男");
//        jsonObject.put("age",22);
//        jsonObject.put("amount",120000);
//        jsonObject.put("term",12);
//        jsonObject.put("life_insurance",0);
//        jsonObject.put("car","有车辆,接受抵押,全款车");
//        jsonObject.put("house","有房产,不接受抵押,全款房");
//        jsonObject.put("credit_situation",2);
//        jsonObject.put("type",1);
//        jsonObject.put("marital_status",0);
//        jsonObject.put("monthly_income", 50000);
//        jsonObject.put("housing_funding",4);
//        jsonObject.put("social_insurance",4);
//        jsonObject.put("paid_method", 1);
//        jsonObject.put("work_duration",3);
//        jsonObject.put("weilidai","20");
//        jsonObject.put("zhima_credit","666");
//        jsonObject.put("apply_at",Utils.nowTimeStr2());
//        jsonObject.put("client_ip","113.143.119.70");
//        jsonObject.put("remark","可面签,其他收入,有担保人");
//
//        String res = HttpUtil.postJSON("https://wx.feidan8.com/api/client_applications?token=OZ6Ac185lyLc8PQkybWSgiRYYbR1I1Q9",jsonObject.toJSONString());
//        System.out.println(res);


//        String r = AESUtil2.cnToUnicode("宋颖");
//        RongDai model = new RongDai();
//        model.setUsername(r);
//        System.out.println(GsonUtil.toString(model).replace("\\\\","\\"));
//        String a = AESUtil2.aes256Encode("宋颖",Constant.rongdaiKey);
//        System.out.println(a);
//        String b = AESUtil2.aes256Decode(a,Constant.rongdaiKey);
//        System.out.println(b);

        //   System.out.println(Utils.unicodeToString("\\u9ad8\\u98ce\\u9669\\u7528\\u6237"));
//        long[] longs = {
//                80099490841755648L,
//                80114503946076160L,
//                80117988456398848L,
//                80118318766227456L,
//                80124684192972800L,
//                80124888308776960L,
//                80130718517166080L,
//                80137131679285248L,
//                80142752638042112L,
//                80162463027298304L,
//                80162525480484864L,
//                80164830070177792L,
//                80169078195486720L,
//                80177632155533312L,
//                80180666541539328L,
//                80192887740956672L,
//                80198050971123712L,
//                80201671150403584L,
//                80204721655119872L,
//                80223162638270464L,
//                80234552551800832L,
//                80245800915959808L,
//                80246336255950848L,
//                80252502038020096L,
//                80256082740510720L,
//                80278005386051584L,
//                80282257172987904L,
//                80284670168662016L,
//                80301304602165248L};
//
//        for (int i =0;i<longs.length;i++){
//            String result = HttpUtil.get(Constant.queryOrderStatusUrl + longs[i]);
//            System.out.println(result);
//        }

//                new Thread(() -> {
//            List<LoanInfo> list = GsonUtil.gson().fromJson(Test2.json,new TypeToken<ArrayList<LoanInfo>>(){}.getType());
//            for (int i=0;i<list.size();i++){
//                LoanInfo loanInfo = list.get(i);
//                loanInfo.setResult(null);
//                String key;
//                if (loanInfo.getChannel().equals("yidaiqz")){
//                    key = "036c346811fedce26f8d4d2cc77c08cc";
//                }else if (loanInfo.getChannel().equals("boluodai")){
//                    key = "58733e6df81c0d5feea085001371b75b";
//                }else if (loanInfo.getChannel().equals("zhengxin")){
//                    key = "69025c4f54300e38a19b38df33f63f15";
//                }else if (loanInfo.getChannel().equals("anyidai")){
//                    key = "a0d906bcb4c4a0fe28725582b10bf63a";
//                }else if (loanInfo.getChannel().equals("baodai")){
//                    key = "c348a0831bcabcbf8699e1fcf5771492";
//                }else if (loanInfo.getChannel().equals("yunhe") || loanInfo.getChannel().equals("anxinhua")){
//                    key = "d63304b992ea2a51644019610fc7083c";
//                }else if (loanInfo.getChannel().equals("heshan")){
//                    key = "d68cbd9dca92b65de67632c6fcf113bf";
//                }else {
//                    key = "2ed3bdccc659dc7bf28da3ac27e87915";
//                }
//                loanInfo.setSign(Utils.getMd5(loanInfo.getPhone() + loanInfo.getIdCard() + key));
//                String res = HttpUtil.post("https://loan.stardai.com/api/loan/addInfo",loanInfo);
//                System.out.println(res);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }
}
