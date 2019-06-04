package com.mianze.apiexport.component;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;

import com.mianze.apiexport.constant.Constant;
import com.mianze.apiexport.mapper.ApiMapper;
import com.mianze.apiexport.pojo.*;
import com.mianze.apiexport.service.ApiService;

import com.mianze.apiexport.util.GsonUtil;
import com.mianze.apiexport.util.HttpUtil;

import com.mianze.apiexport.util.MD5;
import com.mianze.apiexport.util.QBJ.API;
import com.mianze.apiexport.util.Utils;


import okhttp3.FormBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class ApiSchedulerTask {
    private static final Logger log = LoggerFactory.getLogger(ApiSchedulerTask.class);

    @Autowired
    RedisOperator redisOperator;

    @Autowired
    ApiService apiService;
   @Autowired
   ApiMapper apiMapper;
    //定时任务,1分钟执行一次,按照当前时间yyyyMMddHHmm到redis取出待导出渠道信息list,然后遍历导出
    @Scheduled(fixedRate = 60000)
    @Async
    public void apiTask() {
        log.info("*******我是定时任务，我执行了");
        String timeKey = Utils.nowTimeStr();
        List<ChannelOrderExport> exportList = redisOperator.getList(timeKey);
//        log.info("接收到的time下的信息是："+exportList);
        if (exportList == null) {
            return;
        }
        redisOperator.deleteTime(timeKey);
        for (ChannelOrderExport export : exportList) {
            String orderNo = export.getOrderNo();
            int channelId = export.getChannelId();
            //获取这个订单的所有信息
            JSONObject loanInfo = redisOperator.getOrder(orderNo);
            if (loanInfo==null){
                continue;
            }
            if(loanInfo.getString("phone")==null){
                continue;
            }
            updateLoanStatus(channelId, orderNo, loanInfo);
        }

    }
    private void updateLoanStatus(int channelId, String orderNo, JSONObject loanInfo) {
        //根据id查询导出渠道
       JSONObject channelInfo=apiMapper.findExportChannel(channelId);
       if (channelInfo==null||channelInfo.equals("")){
           log.info("渠道不存在");
           return;
       }
       if (channelInfo.getIntValue("status")==0){
            return;
       }
        int exportResult = export(channelId, channelInfo, loanInfo);
        }
    //0表示成功 1表示失败 2表示请求出错,是否成功是未知的  目前是只有返回0才认为是单子导出成功,对于1单子肯定是未导出,对于2是各种异常,这个单子是否成功导出是未知的
    public int export(int channelId, JSONObject channelExport, JSONObject loanInfo) {
        if (loanInfo.getString("amount")==null){
            log.info("渠道货贷额度拦截");
           return 1;
        }
        try {
            switch (channelId) {
                case 1:
                    return exportGuaZi(loanInfo, channelExport);
                case 2:
                    return QianBiJi(loanInfo, channelExport);
                case 3:
                    return exportZhanWang(loanInfo, channelExport);
                case 4:
                    return exportDaJinDai(loanInfo, channelExport);
                case 5:
                    return ExportShuYi(loanInfo, channelExport);
            }
            return 1;
        } catch (Exception e) {
            log.error("导出异常",e);
            return 2;
        }
    }

    private int ExportShuYi(JSONObject loanInfo, JSONObject channelExport){
        log.info("进入速易");
        if(loanInfo.get("amount") == null || loanInfo.getIntValue("amount") <channelExport.getIntValue("min_amount") || loanInfo.getIntValue("amount") >channelExport.getIntValue("max_amount") ){
            return 1;
        }
        if (loanInfo.get("ip") == null || loanInfo.getString("ip").equals("")) {
            return 1;
        }
        if(loanInfo.get("location")==null||loanInfo.getString("location").equals("")){
            return  1;
        }
        if(loanInfo.get("phone")==null||loanInfo.getString("phone").equals("")){
            return  1;
        }
        Integer count=apiService.suyiwangluoCity(loanInfo.getString("location"));
        if (count<1){
            return 1;
        }

        FormBody.Builder formBody = new FormBody.Builder()
                .add("Name",loanInfo.getString("userName"))
                .add("Phone",loanInfo.getString("phone"))
                .add("Sex","男".equals(loanInfo.getString("sex"))?"M":"F")
                .add("LoanAmount",loanInfo.getString("amount"))
                .add("Age",String.valueOf(loanInfo.getIntValue("age")));

        ShuYiModel shuyiModel=new ShuYiModel();
       /* String name=loanInfo.getString("userName");
        shuyiModel.setName(name);
        String phone=loanInfo.getString("phone");
        shuyiModel.setPhone(phone);
        String sex=loanInfo.getString("sex");
        shuyiModel.setSex(sex);
        Integer loanAmount=loanInfo.getIntValue("amount");
        shuyiModel.setLoanAmount(loanAmount);*/
//        int age=loanInfo.getIntValue("age");
//        shuyiModel.setAge(age);
        int recentCredit=loanInfo.getIntValue("status");
        if (recentCredit<=1){
            formBody.add("RecentCredit","1");
//            shuyiModel.setRecentCredit(1);
        }else{
            formBody.add("RecentCredit","2");
//            shuyiModel.setRecentCredit(2);
        }
        String cityName=loanInfo.getString("location");
        if (cityName.contains("市")){
            cityName=cityName.substring(0,cityName.length()-1);
        }
        formBody.add("CityName",cityName);
//        shuyiModel.setCityName(cityName);
        int careerType=loanInfo.getIntValue("job");
        if (careerType==1){
            formBody.add("CareerType","0");
//            shuyiModel.setCareerType(1);
        }else if (careerType==3){
            formBody.add("CareerType","2");
//            shuyiModel.setCareerType(2);
        }else if (careerType==4){
            formBody.add("CareerType","1");
//            shuyiModel.setCareerType(1);
        }else{
            formBody.add("CareerType","1");
//            shuyiModel.setCareerType(1);
        }
        formBody.add("MonthlySalary",String.valueOf(loanInfo.getIntValue("monthlyIncome")));
//        Integer monthlySalary=loanInfo.getIntValue("monthlyIncome");
//        shuyiModel.setMonthlySalary(monthlySalary);
        formBody.add("Ip",loanInfo.getString("ip"));
//        String ip=loanInfo.getString("ip");
//        shuyiModel.setIp(ip);

        String cert="110556_2104200_"+loanInfo.getString("phone");
        formBody.add("Cert",MD5.md5(cert));
        if (loanInfo.get("webank")==null||loanInfo.getString("webank").equals("无")){
            formBody.add("IsUsedWLD","N");
//            shuyiModel.setIsUsedWLD("N");
        }else{
            formBody.add("IsUsedWLD","Y");
//            shuyiModel.setIsUsedWLD("Y");
        }
        int houseProperty=loanInfo.getIntValue("houseProperty");
        if (houseProperty==1){
            formBody.add("HouseProperty","1");
//            shuyiModel.setHouseProperty(1);
        }else{
            formBody.add("HouseProperty","2");
//            shuyiModel.setHouseProperty(2);
        }
        int carProperty=loanInfo.getIntValue("carProperty");
        if (carProperty==1){
            formBody.add("CarProperty","1");
//            shuyiModel.setHouseProperty(1);
        }else{
            formBody.add("CarProperty","2");
//            shuyiModel.setHouseProperty(2);
        }
        int isHasBx=loanInfo.getIntValue("chit");
        if (isHasBx<=1){
            formBody.add("IsHasBx","0");

//            shuyiModel.setIsHasBx(0);
        }else{
            formBody.add("IsHasBx","1");
//            shuyiModel.setIsHasBx(0);
        }

        int shebaoOrGongjijin=loanInfo.getIntValue("sheBao");//gongJiJin
        if (shebaoOrGongjijin<=1){
            formBody.add("ShebaoOrGongjijin","3");
//            shuyiModel.setShebaoOrGongjijin(3);
        }else if (shebaoOrGongjijin==5){
            formBody.add("ShebaoOrGongjijin","2");
//            shuyiModel.setShebaoOrGongjijin(2);
        }else{
            formBody.add("ShebaoOrGongjijin","1");
//            shuyiModel.setShebaoOrGongjijin(1);
        }
        int payWay=loanInfo.getIntValue("income");
        if (payWay==3){
            formBody.add("PayWay","1");
//            shuyiModel.setPayWay(1);
        }else{
            formBody.add("PayWay","0");
//            shuyiModel.setPayWay(0);
        }
        if (loanInfo.get("workYear")==null){
        }else{
            int workLong=loanInfo.getIntValue("workYear");
            if (workLong==1){
                formBody.add("WorkLong","1");
//                shuyiModel.setWorkLong(1);
            }else{
                formBody.add("WorkLong","2");
//                shuyiModel.setWorkLong(2);
            }
        }
        String response=HttpUtil.post(Constant.SuYiUrl,formBody.build());
        log.info("速易网络贷款的返回结果："+response);
        String json=GsonUtil.gson().toJson(formBody);
        log.info("传给速易网络的参数："+json);
        if (response == null) {
            apiService.addExportResult(3,"网络异常",loanInfo.getString("merchantNo"),null,null,json,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
            return 2;
        }else{
            try {
                JSONObject info=JSON.parseObject(response);
                if (info.getString("Ret").equals("0")){
                    apiService.addExportResult(0,loanInfo.getString("merchantNo"),info.getString("Ret"),info.getString("Msg"),info.getString("Data"),json,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                    return 0;
                }else if (info.getString("Ret").equals("1123")||info.getString("Ret").equals("1124")){
                    apiService.addExportResult(1,loanInfo.getString("merchantNo"),info.getString("Ret"),info.getString("Msg"),info.getString("Data"),json,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                    return 1;
                }else{
                    apiService.addExportResult(2,loanInfo.getString("merchantNo"),info.getString("Ret"),info.getString("Msg"),info.getString("Data"),json,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                    return 2;
                }


            }catch (Exception e){
                log.info("速易网络贷款JSON异常");
                apiService.addExportResult(4,loanInfo.getString("merchantNo"),null,"展业王JSON异常",null,json,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                return 2;
            }
        }
    }

    private int exportDaJinDai(JSONObject loanInfo, JSONObject channelExport) throws IllegalAccessException {
        log.info("进入大金贷");

        if (loanInfo.getString("amount") == null || loanInfo.getIntValue("amount") < channelExport.getIntValue("min_amount") || loanInfo.getIntValue("amount") > channelExport.getIntValue("max_amount")) {
            return 1;
        }

        //判断订单的贷款金额大小
        if(loanInfo.getIntValue("amount") < 3){
            Integer cou=apiMapper.queryLoanAmount();
            loanInfo.put("amount",cou);
        }

        if (loanInfo.getString("ip") == null || loanInfo.getString("ip").equals("")) {
            return 1;
        }
        if(loanInfo.getString("location")==null||loanInfo.getString("location").equals("")){
            return  1;
        }
        Integer age = loanInfo.getIntValue("age");
        if (age<20||age>60){
            return  1;
        }
        /*if (loanInfo.getHouseProperty()==1 && loanInfo.getCarProperty()==1 && loanInfo.getSheBao()==1 && loanInfo.getGongJiJin()==1) {
            return 1;
        }*/

        /* FormBody formBody = new FormBody.Builder()
//                .add("channel","888888_dajindai_0")
                .add("channel","294381_dajindai_0")
                .add("mobiles",loanInfo.getString("phone"))
                .build();
        String  result1=HttpUtil.post(Constant.DaJinDaiPhone,formBody);
        log.info("大金贷查重的返回结果："+result1);
        if (result1 == null) {
            apiService.addExportResult(3,"网络异常",loanInfo.getString("merchantNo"),null,null,null,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
            return 2;
        }
        try {
            JSONObject jsonObject=JSONObject.parseObject(result1);
            DaJinDaiModelResponse response = GsonUtil.gson().fromJson(result1, DaJinDaiModelResponse.class);
            if (response.getSuccess() != null && response.getSuccess()) {
                log.info("手机号未重复");
            } else{
                apiService.addExportResult(1,loanInfo.getString("merchantNo"),String.valueOf(response.getErrorCode()),response.getMsg(),jsonObject.getString("data"),null,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                return 1;
            }
        } catch (Exception e) {
            log.error("大金贷JSON异常", e);
            apiService.addExportResult(4,loanInfo.getString("merchantNo"),null,"大金贷JSON异常",null,null,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
            return 2;
        }*/
        String phone=(loanInfo.getString("phone").substring(0,7))+"****";
        DaJinDaiModel model = new DaJinDaiModel();
        //测试渠道号
//        model.setChannel("888888_dajindai_0");
        //正式渠道号
        model.setChannel("294381_dajindai_0");
        model.setReserved(loanInfo.getString("merchantNo"));
        model.setIp(loanInfo.getString("ip"));
        model.setMobile(phone);
        model.setName(loanInfo.getString("userName"));
        model.setId_card(loanInfo.getString("idCard"));
        model.setAge(age);
        if (loanInfo.getString("sex").equals("女")){
            model.setSex("female");
        }else {
            model.setSex("male");
        }
        JSONObject unified_location = apiService.daJinDaiCity(loanInfo.getString("location"));
        if (unified_location==null) {
            return 1;
        }else{
            model.setWork_city_name(unified_location.getString("city"));
            model.setWork_city(unified_location.getIntValue("city_code"));
        }
        int amount = loanInfo.getIntValue("amount");
        if (amount==1){
            model.setMoney(10000);
        }else if (amount==2){
            model.setMoney(20000);
        }else if (amount==3){
            model.setMoney(30000);
        }else if (amount == 5) {
            model.setMoney(50000);
        } else if (amount == 8) {
            model.setMoney(80000);
        } else if (amount == 10) {
            model.setMoney(100000);
        }else if (amount==20){
            model.setMoney(200000);
        }else if (amount==30){
            model.setMoney(300000);
        }else if (amount==50){
            model.setMoney(500000);
        }else if (amount> 50){
            model.setMoney(1000000);
        }

        if (loanInfo.getIntValue("term") == 3) {
            model.setLimit(3);
        } else if (loanInfo.getIntValue("term") == 6) {
            model.setLimit(6);
        } else if (loanInfo.getIntValue("term") == 12) {
            model.setLimit(12);
        } else if (loanInfo.getIntValue("term") == 24) {
            model.setLimit(24);
        } else if (loanInfo.getIntValue("term") == 36) {
            model.setLimit(36);
        }
        model.setPurpose(8);

        if (loanInfo.getIntValue("sheBao") == 1||loanInfo.getIntValue("sheBao") == 0) {
            model.setSb_security(0);
        }
        if (loanInfo.getIntValue("sheBao") == 2||loanInfo.getIntValue("sheBao") == 6||loanInfo.getIntValue("sheBao") == 7) {
            model.setSb_security(3);
        }
        if (loanInfo.getIntValue("sheBao") == 4) {
            model.setSb_security(12);
        }
        if (loanInfo.getIntValue("sheBao") == 5) {
            model.setSb_security(36);
        }

        if (loanInfo.getIntValue("sheBao") == 1||loanInfo.getIntValue("sheBao") == 0) {
            model.setSb_fund(0);
        }
        if (loanInfo.getIntValue("gongJiJin") == 2||loanInfo.getIntValue("gongJiJin") == 6||loanInfo.getIntValue("gongJiJin") == 7) {
            model.setSb_fund(3);
        }
        if (loanInfo.getIntValue("gongJiJin") == 4) {
            model.setSb_fund(12);
        }
        if (loanInfo.getIntValue("gongJiJin") == 5) {
            model.setSb_fund(36);
        }

        if (loanInfo.getIntValue("status") == 1) {
            model.setCredit_card_money(0);
        }
        if (loanInfo.getIntValue("status") == 2) {
            model.setCredit_card_money(30000);
        }
        if (loanInfo.getIntValue("status") == 3) {
            model.setCredit_card_money(10000);
        }
        if (loanInfo.getIntValue("status") == 4 || loanInfo.getIntValue("status") == 5) {
            model.setCredit_card_money(3000);
        }
        if (loanInfo.getIntValue("status") == 0) {
            model.setCredit_card_money(-1);
        }


        if (loanInfo.getString("webank")==null||loanInfo.getString("webank").equals("无")) {
            model.setWld_money(0);
        }else  if (loanInfo.getString("webank").contains("~")){
            String webank=loanInfo.getString("webank");
            int a = webank.indexOf("~");
            Integer webank1 = Integer.parseInt(webank.substring(0, a));
            Integer webank2 = Integer.parseInt(webank.substring(a + 1, webank.length()));
            Integer res=((webank1+webank2)*10000)/2;
            if (res > 500 && res <= 1000) {
                model.setWld_money(1000);
            } else if (res > 1000 && res <= 3000) {
                model.setWld_money(3000);
            } else if (res > 3000 && res <= 5000) {
                model.setWld_money(5000);
            } else if (res > 5000 && res <= 10000) {
                model.setWld_money(10000);
            } else if (res > 10000 && res <= 30000) {
                model.setWld_money(30000);
            } else if (res > 30000 && res <= 50000) {
                model.setWld_money(50000);
            } else if (res > 50000 && res <= 100000) {
                model.setWld_money(100000);
            } else if (res > 100000 && res <= 150000) {
                model.setWld_money(150000);
            } else if (res > 150000 && res <= 200000) {
                model.setWld_money(20000);
            } else if (res > 200000 && res <= 3000000) {
                model.setWld_money(3000000);
            }
        } else if (loanInfo.getString("webank").equals("有")){
            model.setWld_money(1000);
        }else{
            Double webank=(loanInfo.getDoubleValue("webank"))*10000;
            if (webank > 500 && webank <= 1000) {
                model.setWld_money(1000);
            } else if (webank > 1000 && webank <= 3000) {
                model.setWld_money(3000);
            } else if (webank > 3000 && webank <= 5000) {
                model.setWld_money(5000);
            } else if (webank > 5000 && webank <= 10000) {
                model.setWld_money(10000);
            } else if (webank > 10000 && webank <= 30000) {
                model.setWld_money(30000);
            } else if (webank > 30000 && webank <= 50000) {
                model.setWld_money(50000);
            } else if (webank > 50000 && webank <= 100000) {
                model.setWld_money(100000);
            } else if (webank > 100000 && webank <= 150000) {
                model.setWld_money(150000);
            } else if (webank > 150000 && webank <= 200000) {
                model.setWld_money(20000);
            } else if (webank > 200000 && webank <= 3000000) {
                model.setWld_money(3000000);
            }
        }



        if (loanInfo.getIntValue("carProperty") == 4) {
            model.setCar(3);
        } else if (loanInfo.getIntValue("carProperty") == 1) {
            model.setCar(0);
        } else if (loanInfo.getIntValue("carProperty") == 2) {
            model.setCar(1);
        } else if (loanInfo.getIntValue("carProperty") == 3) {
            model.setCar(2);
        }

        model.setCar_value(-1);


        if (loanInfo.getIntValue("houseProperty") == 1) {
            model.setHouse(0);
        } else {
            model.setHouse(3);
            model.setHouse_type(1);
        }
        model.setZy_insurance_value(-1);
        if (loanInfo.getString("houseAssessment")==null){
            model.setHouse_value(-1);
        }else {
            if (loanInfo.getIntValue("houseAssessment")==1){
                model.setHouse_value(50);
            }else if (loanInfo.getIntValue("houseAssessment") == 2) {
                model.setHouse_value(100);
            } else if (loanInfo.getIntValue("houseAssessment") == 3) {
                model.setHouse_value(200);
            } else if (loanInfo.getIntValue("houseAssessment") == 4) {
                model.setHouse_value(500);
            } else if (loanInfo.getIntValue("houseAssessment") == 5) {
                model.setHouse_value(1000);
            }
        }


        if (loanInfo.getIntValue("chit") == 0||loanInfo.getIntValue("chit") == 1) {
            model.setZy_insurance(0);
        } else {
            model.setZy_insurance(1);
        }

        if (loanInfo.getIntValue("job") == 1||loanInfo.getIntValue("job") == 3) {
            model.setProfession(1);
        } else if (loanInfo.getIntValue("job") == 2) {
            model.setProfession(1);
        } else if (loanInfo.getIntValue("job") == 4) {
            model.setProfession(4);
        }

        if (loanInfo.getIntValue("job") == 1||loanInfo.getIntValue("job") == 2||loanInfo.getIntValue("job") ==3||loanInfo.getIntValue("job") ==4) {
            model.setSb_company_type(5);
            if (loanInfo.getString("monthlyIncome").contains("~")){
                String monthlyIncome=loanInfo.getString("monthlyIncome");
                int a = monthlyIncome.indexOf("~");
                Integer monthlyIncome1 = Integer.parseInt(monthlyIncome.substring(0, a));
                Integer monthlyIncome2 = Integer.parseInt(monthlyIncome.substring(a + 1, monthlyIncome.length()));
                Integer res=(monthlyIncome1+monthlyIncome2)/2;
                if (res <= 1000) {
                    model.setSb_income(1000);
                } else if (res> 1000 && res <= 3000) {
                    model.setSb_income(3000);
                } else if (res > 3000 && res <= 5000) {
                    model.setSb_income(5000);
                } else if (res > 5000 && res <= 8000) {
                    model.setSb_income(8000);
                } else if (res > 1000 && res <= 3000) {
                    model.setSb_income(3000);
                } else if (res > 8000 && res <= 12000) {
                    model.setSb_income(12000);
                } else if (res > 12000 && res <= 20000) {
                    model.setSb_income(20000);
                } else if (res > 20000 && res <= 50000) {
                    model.setSb_income(50000);
                } else if (res > 50000 && res <= 100000) {
                    model.setSb_income(100000);
                }

            }else {
                Integer res=loanInfo.getIntValue("monthlyIncome");
                if (res <= 1000) {
                    model.setSb_income(1000);
                } else if (res> 1000 && res <= 3000) {
                    model.setSb_income(3000);
                } else if (res > 3000 && res <= 5000) {
                    model.setSb_income(5000);
                } else if (res > 5000 && res <= 8000) {
                    model.setSb_income(8000);
                } else if (res > 1000 && res <= 3000) {
                    model.setSb_income(3000);
                } else if (res > 8000 && res <= 12000) {
                    model.setSb_income(12000);
                } else if (res > 12000 && res <= 20000) {
                    model.setSb_income(20000);
                } else if (res > 20000 && res <= 50000) {
                    model.setSb_income(50000);
                } else if (res > 50000 && res <= 100000) {
                    model.setSb_income(100000);
                }
            }
            if (loanInfo.getIntValue("income")==1){
                model.setSb_pay_type(1);
            } else if (loanInfo.getIntValue("income") == 2) {
                model.setSb_pay_type(2);
            } else if (loanInfo.getIntValue("income") == 3) {
                model.setSb_pay_type(3);
            }
            if (loanInfo.getString("workYear")==null){

            }else {
                if (loanInfo.getIntValue("workYear")==1||loanInfo.getIntValue("workYear")==2){
                    model.setSb_work_time(1);
                }else {
                    model.setSb_work_time(3);
                }
            }
        }

        model.setIp(loanInfo.getString("ip"));
        String json = GsonUtil.gson().toJson(model);
        log.info("导给大金贷的数据是："+json);
        String result = HttpUtil.post(Constant.DaJinDaiUrl, model);
        log.info("大金贷的返回结果是："+result);
      /*  String result = HttpUtil.post(Constant.kuailaiqian2Url, model);*/
        if (result == null) {
            apiService.addExportResult(3,"网络异常",loanInfo.getString("merchantNo"),null,null,json,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
            return 2;
        } else {
            try {
                // result = Utils.unicodeToString(result);
                JSONObject js=JSONObject.parseObject(result);
                DaJinDaiModelResponse response = GsonUtil.gson().fromJson(result, DaJinDaiModelResponse.class);
                if (response.getSuccess() != null && response.getSuccess()) {
                    apiService.addExportResult(0,loanInfo.getString("merchantNo"),String.valueOf(response.getErrorCode()),"成功",JSONObject.parseObject(js.getString("data")).getString("id"),json,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                    return 0;
                } else if ("客户已存在".equals(response.getMsg())) {
                    apiService.addExportResult(1,loanInfo.getString("merchantNo"),String.valueOf(response.getErrorCode()),response.getMsg(),js.getString("data"),json,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                    return 1;
                } else {
                    apiService.addExportResult(2,loanInfo.getString("merchantNo"),String.valueOf(response.getErrorCode()),response.getMsg(),js.getString("data"),json,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                    return 1;
                }
            } catch (Exception e) {
                log.error("大金贷JSON异常", e);
                apiService.addExportResult(4,loanInfo.getString("merchantNo"),null,"大金贷JSON异常",null,json,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                return 2;
            }

        }
    }

    private int exportZhanWang(JSONObject loanInfo, JSONObject channelExport) {
        log.info("进入展业王");

        //判断订单的贷款金额大小
        if(loanInfo.getIntValue("amount") < 3){
            Integer cou=apiMapper.queryLoanAmount();
            loanInfo.put("amount",cou);
        }

        if (loanInfo.getString("amount") == null || loanInfo.getIntValue("amount") < channelExport.getIntValue("min_amount") || loanInfo.getIntValue("amount") > channelExport.getIntValue("max_amount")) {
            return 1;
        }
       if (loanInfo.get("location")==null){
            return 1;
       }
        Integer count=apiService.zhanYeWangCity(loanInfo.getString("location"));
        if (count<1){
            return 1;
        }

        String phone="";
        if (loanInfo.get("phone")==null){
           return 1;
        }else {
            String a=loanInfo.getString("phone").substring(0,8);
            phone=a+"***";
        }
        String city="";
        if (loanInfo.getString("location").contains("市")){
            city=loanInfo.getString("location").substring(0,(loanInfo.getString("location").length())-1);
        } else {
            city=loanInfo.getString("location");
        }
        String fangchan="";
        if (loanInfo.getIntValue("houseProperty")==1){
            fangchan="1";
        }else {
            fangchan="3";
        }
        String car="";
        if (loanInfo.getIntValue("carProperty")==1){
            car="1";
        }else {
            car="3";
        }
        String job="";
        if (loanInfo.getIntValue("job")==1){
            job="1";
        }else if (loanInfo.getIntValue("job")==3){
            job="2";
        }else {
            job="3";
        }
        String shebao="";
        if (loanInfo.getIntValue("sheBao")==1){
            shebao="3";
        }else if (loanInfo.getIntValue("sheBao")==5){
            shebao="2";
        }else {
            shebao="1";
        }
        String workYear="";
        if (loanInfo.get("workYear")==null){
            workYear="5";
        }else if (loanInfo.getIntValue("workYear")==1){
            workYear="1";
        }else if (loanInfo.getIntValue("workYear")==2){
            workYear="2";
        }else if (loanInfo.getIntValue("workYear")==3){
            workYear="3";
        }
        String chit="";
        if (loanInfo.getIntValue("chit")<=1){
            chit="1";
        }else if (loanInfo.getIntValue("chit")>1){
            chit="2";
        }
        String webank="";
        if (loanInfo.get("webank")==null||loanInfo.getString("webank").equals("无")){
            webank="2";
        }else {
            webank="2";
        }
        FormBody formBody = new FormBody.Builder()
                .add("code", "9B884347A9B24514ACA30BF008F04F96")
                .add("truename", loanInfo.getString("userName"))
                .add("mobile", phone)
                .add("city", city)
                .add("loanamount", loanInfo.getString("amount"))
                .add("birthday",loanInfo.getString("birthday"))
                .add("gender", "男".equals(loanInfo.getString("sex"))?"M":"F")
                .add("fangchan", fangchan)
                .add("car", car)
                .add("zhiye", job)
                .add("gongzi", "3".equals(loanInfo.getString("income"))?"2":"1")
                .add("shouru", loanInfo.getString("monthlyIncome"))
                .add("shebao", shebao)
                .add("gongling", workYear)
                .add("zhizhao", "3")
                .add("havedan", chit)
                .add("havewld", webank)
                .build();
        String jsons = GsonUtil.gson().toJson(formBody);
        log.info("传给展业王的数据是："+jsons);
        String result = HttpUtil.post(Constant.zhanwangUrl1, formBody);
        log.info("展业王的返回结果1："+result);
        if (result == null) {
            apiService.addExportResult(3,loanInfo.getString("merchantNo"),"网络异常",null,null,jsons,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
            return 2;
        }
        String TradeNo="";
        try {
            JSONObject jsonObject = JSONObject.parseObject(result);
            String code = jsonObject.getString("resultCode");
            String msg = jsonObject.getString("Msg");
            if ("0".equals(code)) {
                TradeNo=jsonObject.getString("TradeNo");

            } else if (msg.equals("该数据与我司重复")){
                apiService.addExportResult(1,loanInfo.getString("merchantNo"),code,msg,null,jsons,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                return 1;
            }else {
                apiService.addExportResult(1,loanInfo.getString("merchantNo"),code,msg,null,jsons,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                return 2;
            }

            FormBody formBody1 = new FormBody.Builder()
                    .add("TradeNo",TradeNo)
                    .add("Mobile",loanInfo.getString("phone"))
                    .build();
            String result1 = HttpUtil.post(Constant.zhanwangUrl2, formBody1);
            log.info("展业王手机号返回结果："+result1);
            if (result1 == null) {
                apiService.addExportResult(3,loanInfo.getString("merchantNo"),"网络异常",null,null,jsons,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                return 2;
            }
            JSONObject jsonObject1 = JSONObject.parseObject(result);
            String code1 = jsonObject1.getString("resultCode");
            String msg1 = jsonObject1.getString("Msg");
             if ("0".equals(code1)){
                 apiService.addExportResult(0,loanInfo.getString("merchantNo"),code1,"成功",TradeNo,jsons,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                return 0;
             }else {
                 apiService.addExportResult(2,loanInfo.getString("merchantNo"),code1,msg1,null,jsons,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                return 2;
             }
        } catch (Exception e) {
            log.error("展业王JSON异常", e);
            apiService.addExportResult(4,loanInfo.getString("merchantNo"),null,"展业王JSON异常",null,jsons,loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
            return 2;
        }
    }
    private int exportGuaZi(JSONObject loanInfo,JSONObject channelExport){
        log.info("进入到秒单之家");

        //判断订单的贷款金额大小
        if(loanInfo.getIntValue("amount") < 3){
            Integer cou=apiMapper.queryLoanAmount();
            loanInfo.put("amount",cou);
        }

        if (loanInfo.get("amount")==null||(loanInfo.getIntValue("amount"))*10000<20000){
            return 1;
        }
        if (loanInfo.getIntValue("age")<22||loanInfo.getIntValue("age")>55){
            return 1;
        }
        if (loanInfo.get("idCard")==null){
            return 1;
        }
        if (!loanInfo.getString("location").equals(loanInfo.getString("mobileAttribution"))){
            log.info("所在地和归属地不一致");
            return 1;
        }
        if (loanInfo.getIntValue("houseProperty") == 1 &&
                loanInfo.getIntValue("carProperty") == 1 &&
                (loanInfo.getIntValue("gongJiJin") == 1 || loanInfo.getIntValue("gongJiJin") == 0) &&
                (loanInfo.getIntValue("sheBao") == 1 || loanInfo.getIntValue("sheBao") == 0) &&
                (loanInfo.getIntValue("chit") == 1 || loanInfo.getIntValue("chit") == 0) &&
                ( loanInfo.getString("webank")==null|| loanInfo.getString("webank").equals("无") )) {
            return 1;
        }
        MiaoDanModel model=new MiaoDanModel();
        model.setChannelNo(Constant.miaodaichannel);
        String signYuan=Constant.miaodaichannel+loanInfo.getString("userName")+Constant.miaodaiKey+loanInfo.getString("applyTime");
        model.setSign(MD5.md5(signYuan));
        model.setName(loanInfo.getString("userName"));
        if (loanInfo.getString("sex").equals("男")){
            model.setSex(0);
        }else {
            model.setSex(1);
        }
        model.setIdCard(loanInfo.getString("idCard"));
        JSONObject city=apiService.findGuaziCity(loanInfo.getString("location"));
        if (city==null||city.equals("")){
            return 1;
        }
        model.setAreaName(city.getString("areaName"));
        model.setAreaCode(city.getString("areaCode"));
        model.setBirthday(loanInfo.getString("birthday"));
        model.setExpectAmount(String.valueOf(loanInfo.getIntValue("amount")*10000));
        if (loanInfo.getString("policySituation")==null){
            model.setPolicy(1);
        }else {
            model.setPolicy( loanInfo.getIntValue("policySituation")+1);
        }
        if (loanInfo.get("status")==null||loanInfo.getIntValue("status")<2){
            model.setCredit(1);
        }else {
            model.setCredit(2);
        }
        if (loanInfo.get("houseProperty")==null||loanInfo.getIntValue("houseProperty")==1){
            model.setHouse(1);
        }else {
            model.setHouse(3);
        }
        if (loanInfo.get("carProperty")==null||loanInfo.getIntValue("carProperty")==1){
            model.setCar(1);
        }else {
            model.setCar(3);
        }
        if (loanInfo.get("gongJiJin")==null||loanInfo.getIntValue("gongJiJin")<2){
            model.setFund(0);
        }else if (loanInfo.getIntValue("gongJiJin")==5){
            model.setFund(2);
        }else {
            model.setFund(1);
        }
        if (loanInfo.get("sheBao")==null||loanInfo.getIntValue("sheBao")<2){
            model.setSocialSecurity(0);
        }else if (loanInfo.getIntValue("sheBao")==5){
            model.setSocialSecurity(2);
        }else {
            model.setSocialSecurity(1);
        }
        if (loanInfo.get("webank")==null||loanInfo.getString("webank").equals("无")){
            model.setWxloan(2);
        }else {
            model.setWxloan(1);
        }
        if (loanInfo.get("job")==null){
            return 1;
        }else if (loanInfo.getIntValue("job")==1){
            model.setOccupation(0);
        }else if (loanInfo.getIntValue("job")==3){
            model.setOccupation(2);
        }else {
            model.setOccupation(1);
        }
        if (loanInfo.get("workYear")==null||loanInfo.getIntValue("workYear")<3){
            model.setWorkDuration(6);
        }else {
            model.setWorkDuration(24);
        }
        if (loanInfo.get("income")==null||loanInfo.getIntValue("income")<3){
            model.setSalaryMethod(1);
        }else {
            model.setSalaryMethod(2);
        }
        if (loanInfo.get("monthlyIncome")==null){
            return 1;
        }else if (loanInfo.getIntValue("monthlyIncome")<3000){
            model.setSalary(1);
        }else if (loanInfo.getIntValue("monthlyIncome")>=3000&&loanInfo.getIntValue("monthlyIncome")<5000){
            model.setSalary(2);
        }else if (loanInfo.getIntValue("monthlyIncome")>=5000&&loanInfo.getIntValue("monthlyIncome")<=8000){
              model.setSalary(3);
        }else if (loanInfo.getIntValue("monthlyIncome")>8000&&loanInfo.getIntValue("monthlyIncome")<10000){
            model.setSalary(4);
        }else {
            model.setSalary(5);
        }
        model.setApplyTime(loanInfo.getString("applyTime"));
        model.setIp(loanInfo.getString("ip"));
        model.setMobileTail(loanInfo.getString("mobileTail"));
        log.info("秒单URL："+Constant.miaodaiUrl);
        String res=HttpUtil.post(Constant.miaodaiUrl,model);
        log.info("秒单返回结果是："+res);
        if (res == null){
            apiService.addExportResult(3,loanInfo.getString("merchantNo"),"网络异常",null,null,model.toString(),loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
            return 2;
        }
        try {
            GuaZiResponse guazi= GsonUtil.gson().fromJson(res,GuaZiResponse.class);

            if (guazi.getCode().equals("200")){
            apiService.addExportResult(0,loanInfo.getString("merchantNo"),guazi.getCode(),guazi.getMsg(),guazi.getData(),model.toString(),loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
            return 0;
            }else {
                apiService.addExportResult(2,loanInfo.getString("merchantNo"),guazi.getCode(),guazi.getMsg(),guazi.getData(),model.toString(),loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                return 2;
            }

        }catch (Exception e){
            log.info("秒单JSON异常");
            apiService.addExportResult(4,loanInfo.getString("merchantNo"),null,"瓜子JSON异常",null,model.toString(),loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
            return 2;
        }

    }
    private int QianBiJi(JSONObject loanInfo,JSONObject channelExport){
        //判断订单的贷款金额大小
        if(loanInfo.getIntValue("amount") < 3){
            Integer cou=apiMapper.queryLoanAmount();
            loanInfo.put("amount",cou);
        }
        QBJModel model=new QBJModel();
        model.setChannel("guazi");
        model.setIp(loanInfo.getString("ip"));
        model.setPhone(loanInfo.getString("phone"));
        model.setAmount(loanInfo.getIntValue("amount"));
        model.setTerm(loanInfo.getIntValue("term"));
        model.setUserName(loanInfo.getString("userName"));
        model.setIdCard(loanInfo.getString("idCard"));
        model.setAge(loanInfo.getIntValue("age"));
        model.setSex(loanInfo.getString("sex"));
        model.setHousehold(loanInfo.getString("household"));
        model.setLocation(loanInfo.getString("location"));
        model.setDegree(loanInfo.getIntValue("degree"));
        model.setJob(loanInfo.getIntValue("job"));
        model.setMonthlyIncome(loanInfo.getIntValue("monthlyIncome"));
        model.setIncome(loanInfo.getIntValue("income"));
        model.setWorkYear(loanInfo.getIntValue("workYear"));
        model.setSheBao(loanInfo.getIntValue("sheBao"));
        model.setGongJiJin(loanInfo.getIntValue("gongJiJin"));
        model.setHouseProperty(loanInfo.getIntValue("houseProperty"));
        model.setHouse(loanInfo.getIntValue("houseProperty"));
        model.setHouseAssessment(loanInfo.getIntValue("houseAssessment"));
        model.setCarProperty(loanInfo.getIntValue("carProperty"));
        model.setCar(loanInfo.getIntValue("car"));
        model.setCarAssessment(loanInfo.getIntValue("carAssessment"));
        model.setDebt(loanInfo.getIntValue("debt"));
        model.setChit(loanInfo.getIntValue("chit"));
        model.setPolicySituation(loanInfo.getIntValue("policySituation"));
        model.setStatus(loanInfo.getIntValue("status"));
        model.setWebank(loanInfo.getString("webank"));
        model.setZmxy(loanInfo.getString("zmxy"));
        model.setJiebei(loanInfo.getString("jiebei"));
        model.setChannelBranch(loanInfo.getIntValue("channelBranch"));
        model.setIsSecure(loanInfo.getIntValue("isSecure"));
        model.setUserAgent(loanInfo.getString("userAgent"));
        model.setMerchantNo(loanInfo.getString("merchantNo"));
        log.info("导给钱笔记的数据是"+model.toString());
        String result= API.response(model);
        log.info("钱笔记的返回结果："+result);
        if (result == null){
            apiService.addExportResult(3,loanInfo.getString("merchantNo"),null,"网络异常",null,model.toString(),loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
            return 2;
        }
        try {
            GuaZiResponse qbj = GsonUtil.gson().fromJson(result,GuaZiResponse.class);
            if (qbj.getCode().equals("0")){
                apiService.addExportResult(0,loanInfo.getString("merchantNo"),qbj.getCode(),qbj.getMsg(),qbj.getData(),model.toString(),loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                return 0;
            }else {
                apiService.addExportResult(2,loanInfo.getString("merchantNo"),qbj.getCode(),qbj.getMsg(),qbj.getData(),model.toString(),loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
                return 2;
            }
        }catch (Exception e){
            log.info("钱笔记JSON异常");
            apiService.addExportResult(4,loanInfo.getString("merchantNo"),null,"钱笔记JSON异常",null,model.toString(),loanInfo.getString("phone"),channelExport.getString("channel"),loanInfo.getString("channel"));
            return 2;
        }

    }
}


