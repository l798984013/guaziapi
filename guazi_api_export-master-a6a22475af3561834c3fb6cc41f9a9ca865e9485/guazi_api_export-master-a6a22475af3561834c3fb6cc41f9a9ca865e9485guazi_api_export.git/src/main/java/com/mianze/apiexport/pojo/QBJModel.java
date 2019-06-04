package com.mianze.apiexport.pojo;

import com.alibaba.fastjson.JSON;

public class QBJModel {

    /**
     * channelBranch : -1
     * webank : 有
     * channel : test1
     * term : 36
     * amount : 20
     * idCard : 43062319700412721X
     * sheBao : 4
     * ip : 223.104.63.233
     * degree : 0
     * userName : 李中毓
     * carProperty : 4
     * gongJiJin : 4
     * phone : 13726412691
     * houseProperty : 4
     * monthlyIncome : 5000
     * location : 北京市
     * job : 1
     * income : 1
     * chit : 1
     * status : 2
     * merchantNo : 22073828
     */

    private Integer channelBranch;
    private String webank;
    private String channel;
    private Integer term;
    private Integer amount;
    private String idCard;
    private Integer sheBao;
    private String ip;
    private Integer degree;
    private String userName;
    private Integer carProperty;
    private Integer gongJiJin;
    private String phone;
    private Integer houseProperty;
    private Integer monthlyIncome;
    private String location;
    private Integer job;
    private Integer income;
    private Integer chit;
    private Integer status;
    private String merchantNo;
    private  Integer age;
    private  String sex;
    private  String household;
    private  Integer workYear;
    private  Integer house;
    private  Integer houseAssessment;
    private  Integer car;
    private  Integer carAssessment;
    private  Integer debt;

    private  Integer policySituation;
    private  String zmxy;
    private  String jiebei;
    private  Integer isSecure;
    private  String userAgent;


    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Integer getIsSecure() {
        return isSecure;
    }

    public void setIsSecure(Integer isSecure) {
        this.isSecure = isSecure;
    }

    public String getJiebei() {
        return jiebei;
    }

    public void setJiebei(String jiebei) {
        this.jiebei = jiebei;
    }

    public String getZmxy() {
        return zmxy;
    }

    public void setZmxy(String zmxy) {
        this.zmxy = zmxy;
    }

    public Integer getPolicySituation() {
        return policySituation;
    }

    public void setPolicySituation(Integer policySituation) {
        this.policySituation = policySituation;
    }

    public Integer getDebt() {
        return debt;
    }

    public void setDebt(Integer debt) {
        this.debt = debt;
    }

    public Integer getCarAssessment() {
        return carAssessment;
    }

    public void setCarAssessment(Integer carAssessment) {
        this.carAssessment = carAssessment;
    }

    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }

    public Integer getHouseAssessment() {
        return houseAssessment;
    }

    public void setHouseAssessment(Integer houseAssessment) {
        this.houseAssessment = houseAssessment;
    }

    public Integer getHouse() {
        return house;
    }

    public void setHouse(Integer house) {
        this.house = house;
    }

    public Integer getWorkYear() {
        return workYear;
    }

    public void setWorkYear(Integer workYear) {
        this.workYear = workYear;
    }

    public String getHousehold() {
        return household;
    }

    public void setHousehold(String household) {
        this.household = household;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getChannelBranch() {
        return channelBranch;
    }

    public void setChannelBranch(Integer channelBranch) {
        this.channelBranch = channelBranch;
    }

    public String getWebank() {
        return webank;
    }

    public void setWebank(String webank) {
        this.webank = webank;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getSheBao() {
        return sheBao;
    }

    public void setSheBao(Integer sheBao) {
        this.sheBao = sheBao;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCarProperty() {
        return carProperty;
    }

    public void setCarProperty(Integer carProperty) {
        this.carProperty = carProperty;
    }

    public int getGongJiJin() {
        return gongJiJin;
    }

    public void setGongJiJin(Integer gongJiJin) {
        this.gongJiJin = gongJiJin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getHouseProperty() {
        return houseProperty;
    }

    public void setHouseProperty(Integer houseProperty) {
        this.houseProperty = houseProperty;
    }

    public Integer getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Integer monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getJob() {
        return job;
    }

    public void setJob(Integer job) {
        this.job = job;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public int getChit() {
        return chit;
    }

    public void setChit(Integer chit) {
        this.chit = chit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
