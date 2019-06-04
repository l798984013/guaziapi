package com.mianze.apiexport.pojo;

import com.alibaba.fastjson.JSON;

public class MiaoDanModel {

    /**
     * channelNo : dp_channel_001
     * sign : 77a1a37e9ba3092a677760d6b1e9d892
     * mobile : 13800456186
     * name : 李四
     * sex : 1
     * idCard : 142327199006051234
     * areaCode : 110100
     * areaName : 北京
     * birthday : 1983-05-01
     * expectAmount : 30000
     * policy : 1
     * credit : 1
     * house : 1
     * car : 1
     * fund : 1
     * socialSecurity : 1
     * wxloan : 1
     * occupation : 1
     * workDuration : 6
     * salaryMethod : 1
     * salary : 1
     * applyTime : 2018-12-14 133:03:21
     * ip : 192.168.21.198
     * mobileRegistration : 北京
     * mobileTail : 6186
     */

    private String channelNo;
    private String sign;
    private String mobile;
    private String name;
    private Integer sex;
    private String idCard;
    private String areaCode;
    private String areaName;
    private String birthday;
    private String expectAmount;

    private Integer credit;
    private Integer house;
    private Integer car;
    private Integer fund;
    private Integer socialSecurity;
    private Integer wxloan;
    private Integer occupation;
    private Integer workDuration;
    private Integer salaryMethod;
    private Integer salary;
    private String applyTime;
    private String ip;
    private String mobileRegistration;
    private String mobileTail;
    private Integer policy;
    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getExpectAmount() {
        return expectAmount;
    }

    public void setExpectAmount(String expectAmount) {
        this.expectAmount = expectAmount;
    }

    public int getPolicy() {
        return policy;
    }

    public void setPolicy(int policy) {
        this.policy = policy;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public int getFund() {
        return fund;
    }

    public void setFund(int fund) {
        this.fund = fund;
    }

    public int getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(int socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public int getWxloan() {
        return wxloan;
    }

    public void setWxloan(int wxloan) {
        this.wxloan = wxloan;
    }

    public int getOccupation() {
        return occupation;
    }

    public void setOccupation(int occupation) {
        this.occupation = occupation;
    }

    public int getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(int workDuration) {
        this.workDuration = workDuration;
    }

    public int getSalaryMethod() {
        return salaryMethod;
    }

    public void setSalaryMethod(int salaryMethod) {
        this.salaryMethod = salaryMethod;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMobileRegistration() {
        return mobileRegistration;
    }

    public void setMobileRegistration(String mobileRegistration) {
        this.mobileRegistration = mobileRegistration;
    }

    public String getMobileTail() {
        return mobileTail;
    }

    public void setMobileTail(String mobileTail) {
        this.mobileTail = mobileTail;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
