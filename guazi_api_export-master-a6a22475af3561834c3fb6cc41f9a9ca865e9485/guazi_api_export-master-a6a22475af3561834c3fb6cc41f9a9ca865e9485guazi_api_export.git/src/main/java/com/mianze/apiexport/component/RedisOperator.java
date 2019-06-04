package com.mianze.apiexport.component;

import com.alibaba.fastjson.JSONObject;
import com.mianze.apiexport.pojo.ChannelOrderExport;
import com.mianze.apiexport.pojo.LoanInfo;
import com.mianze.apiexport.util.GsonUtil;
import com.mianze.apiexport.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class RedisOperator {

    //此类的一些操作,配合定时任务一分钟内是同一个线程内跑的,没有线程安全问题,
    // 但是如果一分钟内任务太多处理不过来,会跟下一分钟的任务产生并发问题,那么就会有线程安全问题,比如限流可能会超出预定的值

    @Autowired
    StringRedisTemplate redisTemplate;


    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // seconds超时时间 小时
    public void put(String key, String value, int hours) {
        redisTemplate.opsForValue().set(key, value, hours, TimeUnit.HOURS);
    }

    public String getCityCode(String city) {
        return get("city:" + city);
    }

    public void putOrder(LoanInfo loanInfo) {
        put("order:" + loanInfo.getOrderNo(), GsonUtil.toString(loanInfo), 72);
    }

    //获取单子信息
    /*public LoanInfo getOrder(long orderNo) {
        String json = get("order:" + orderNo);
        if (json == null)
            return null;
        return GsonUtil.gson().fromJson(json, LoanInfo.class);
    }*/
    public JSONObject getOrder(String orderNo) {
        String json = get("order:" + orderNo);
        if (json == null)
            return null;
        return GsonUtil.gson().fromJson(json, JSONObject.class);
    }

    //获取渠道的限流
    public Integer getRestrictAmount(int channelId) {

        String amount = get("restrict:" + channelId + ":" + Utils.nowDateStr2());
        if (amount == null) {
            return null;
        } else {
            return Integer.parseInt(amount);
        }

    }

    //尊胜 城市限流
    public Integer getCityRestrictAmount(String city) {

        String amount = get("restrict:" + city + ":" + Utils.nowDateStr2());
        if (amount == null) {
            return null;
        } else {
            return Integer.parseInt(amount);
        }

    }

    //设置尊胜 城市限流
    public void setCityRestrictAmount(String city) {

        //可以直接用incr
        String key = "restrict:" + city + ":" + Utils.nowDateStr2();
        String amount = get(key);
        if (amount == null) {
            put(key, "1", 24);
        } else {
            put(key, String.valueOf(Integer.parseInt(amount) + 1), 24);
        }

    }

    //设置渠道的限流
    public void setRestrictAmount(int channelId) {

        //可以直接用incr
        String key = "restrict:" + channelId + ":" + Utils.nowDateStr2();
        String amount = get(key);
        if (amount == null) {
            put(key, "1", 24);
        } else {
            put(key, String.valueOf(Integer.parseInt(amount) + 1), 24);
        }

    }

    //time 201806121332  yyyyMMddHHmm
    public void putToList(String time, ChannelOrderExport info) {
        String key = "time:" + time;
        redisTemplate.opsForList().rightPush(key, GsonUtil.toString(info));
        redisTemplate.expire(key, 72, TimeUnit.HOURS);
    }

    //删除对应时间
    public void deleteTime(String time) {
        delete("time:" + time);
    }

    //获取对应时间需要导出的信息
        public List<ChannelOrderExport> getList(String time) {
        List<String> list = redisTemplate.opsForList().range("time:" + time, 0, -1);
//        System.out.println("查到的redis："+list);
        if (list == null || list.size() == 0) {
            return null;
        }
        List<ChannelOrderExport> infos = new ArrayList<>();
        for (String aList : list) {
            infos.add(GsonUtil.gson().fromJson(aList, ChannelOrderExport.class));
        }
        return infos;
    }

    //xqbToken是赠险  xqbToken2是贷款
    public String getXiaoQianBaoToken() {
        return get("xqbToken2");
    }

    public void putXiaoQianBaoToken(String token) {
        redisTemplate.opsForValue().set("xqbToken2", token, 110, TimeUnit.MINUTES);
    }


    public String getWeiDaiWangToken() {
        return get("wdwToken");
    }

    public void putWeiDaiWangToken(String token) {
        redisTemplate.opsForValue().set("wdwToken", token, 20, TimeUnit.DAYS);
    }


    //设置城市code
    public void putCityCode(String city, String code) {
        put("city:" + city, code);
    }
}
