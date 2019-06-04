package com.mianze.apiexport.controller;

import com.alibaba.fastjson.JSONObject;
import com.mianze.apiexport.pojo.ALICloundModelResponse;
import com.mianze.apiexport.pojo.GuaZiResponse;
import com.mianze.apiexport.service.ApiService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("miaodan")
public class MiaoDanController {
    @Autowired
    private ApiService apiService;
    @GetMapping("getPhone")
    public GuaZiResponse GuaZiResponse(@RequestParam("id") String id){
        return apiService.findPhone(id);
    }

    //大金贷抢单后回调拿手机号，并传抢单金额
    @PostMapping("/appPhone")
    public ALICloundModelResponse getMobiles(@RequestBody JSONObject jsonObject){
        ALICloundModelResponse model=new ALICloundModelResponse();
        if (jsonObject.get("orderNo")==null||jsonObject.get("grabOrderPrice")==null){
            model.setCode(1);
            model.setMessage("缺少必传参数");
            model.setData(null);
            return model;
        }
        return  apiService.getMobiles(jsonObject);
    }

    @PostMapping("retureOrder")
    public  ALICloundModelResponse chargeback(@RequestBody JSONObject jsonObject){
        ALICloundModelResponse model=new ALICloundModelResponse();
        if (jsonObject.get("orderNo")==null||jsonObject.get("chargebackPrice")==null){
            model.setCode(1);
            model.setMessage("缺少必传参数");
            model.setData(null);
            return model;
        }
        return  apiService.updateChargeback(jsonObject);
    }

}
