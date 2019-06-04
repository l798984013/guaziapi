package com.mianze.apiexport.service;


import com.alibaba.fastjson.JSONObject;
import com.mianze.apiexport.component.RedisOperator;
import com.mianze.apiexport.mapper.ApiMapper;
import com.mianze.apiexport.pojo.*;
import com.mianze.apiexport.util.Utils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@SuppressWarnings("all")
public class ApiService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ApiMapper apiMapper;

    @Autowired
    RedisOperator redisOperator;


    public boolean isToggleOpen() {
        Integer result = apiMapper.isToggleOpen();
        return result != null && result > 0;
    }


    public void addLoanInfo(long orderNo) {
        LoanInfo loanInfo = apiMapper.queryLoanInfo(orderNo);
        if (loanInfo == null) {
            return;
        }

        redisOperator.putOrder(loanInfo);
        Date now = new Date();

        //存入redis,key为需要导出的时间
        List<ChannelExport> channelExports = apiMapper.getChannelExports();
        if (channelExports != null && channelExports.size() > 0) {

            for (int i = 0; i < channelExports.size(); i++) {
                ChannelExport channelExport = channelExports.get(i);
                ChannelOrderExport channelOrderExport = new ChannelOrderExport(orderNo, channelExport.getId(), channelExport.getAcceptAll());
                redisOperator.putToList(Utils.afterHoursTimeStr(now, channelExport.getDelay()), channelOrderExport);
                //redisOperator.putToList(Utils.afterMinutesTimeStr(now,1),channelOrderExport);
            }
        }
    }

    public JSONObject findGuaziCity(String location){
        return apiMapper.findGuaziCity(location);
    }
    @Transactional
    public void addExportResult( Integer status, String order_no, String code,
                                String msg, String data,String orderData,String phone,String exportChannel,String importChannel){
        apiMapper.addExportResult(status,order_no,code,msg,data,orderData,phone,exportChannel,importChannel);
    }

    public  Integer zhanYeWangCity(String location){
        return  apiMapper.zhanYeWangCity(location);
    }
    public  Integer suyiwangluoCity(String location){
        return  apiMapper.suyiwangluoCity(location);
    }
    public GuaZiResponse findPhone(String id) {

        GuaZiResponse guaZiResponse=new GuaZiResponse();
        try {
            String phone=apiMapper.findPhone(id);
            if (phone==null){
                guaZiResponse.setCode("406");
                guaZiResponse.setMsg("未查询到电话");
                guaZiResponse.setData(null);
            }else {
                guaZiResponse.setCode("200");
                guaZiResponse.setMsg("成功");
                guaZiResponse.setData(phone);
            }
        }catch (Exception e){
            guaZiResponse.setCode("406");
            guaZiResponse.setMsg("服务器异常");
            guaZiResponse.setData(null);
        }
       return guaZiResponse;
    }
    public JSONObject daJinDaiCity(String city) {
        return apiMapper.daJinDaiCity(city);
    }

    @Transactional(rollbackFor = Exception.class)
    public ALICloundModelResponse getMobiles(JSONObject jsonObject){

        ALICloundModelResponse response=new ALICloundModelResponse();
        try {

            String  phone=apiMapper.findExistenceOrder(jsonObject.getString("orderNo"));
            if (phone==null){
                response.setCode(1);
                response.setMessage("无此订单");
                response.setData(null);
            }else {
                response.setCode(0);
                response.setMessage("成功");
                response.setData(phone);
                apiMapper.updateBackStatus(1,jsonObject.getString("grabOrderPrice"),jsonObject.getString("orderNo"));
            }
        }catch (Exception e){
            log.info("异常原因："+e);
            response.setCode(2);
            response.setMessage("服务器异常");
            response.setData(null);
        }
        return response;
    }
    @Transactional(rollbackFor = Exception.class)
    public ALICloundModelResponse updateChargeback(JSONObject jsonObject){
        ALICloundModelResponse response=new ALICloundModelResponse();
        try {
            Integer count=apiMapper.findExistenceOrder1(jsonObject.getString("orderNo"));

            if (count<1){
                response.setCode(1);
                response.setMessage("无此订单或未被抢");
                response.setData(null);
            }else {

                apiMapper.updateChargeback(1,jsonObject.getString("chargebackPrice"),jsonObject.getString("orderNo"));
                response.setCode(0);
                response.setMessage("退单成功");
                response.setData(jsonObject.getString("orderNo"));
            }
        }catch (Exception e){
            log.info("出现的异常是："+e);
            response.setCode(2);
            response.setMessage("服务器异常");
            response.setData(null);

        }
        return response;
    }
}

