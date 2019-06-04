package com.mianze.apiexport.controller;

import com.alibaba.fastjson.JSON;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.gson.reflect.TypeToken;
import com.mianze.apiexport.component.RedisOperator;
import com.mianze.apiexport.constant.Constant;
import com.mianze.apiexport.pojo.*;
import com.mianze.apiexport.service.ApiService;
import com.mianze.apiexport.util.GsonUtil;
import com.mianze.apiexport.util.HttpUtil;
import com.mianze.apiexport.util.PhoneTest.HttpUtils;
import com.mianze.apiexport.util.Utils;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("data")
public class TestController {


    @Autowired
    ApiService apiService;


    @Autowired
    RedisOperator redisOperator;





}
