package com.mianze.apiexport.pojo;

public class ShuYiModel {
    private String name;//中文姓名（2-8位中文字符）
    private String phone;//手机号码（11位数字）
    private String sex;//性别M:男 F:女
    private int loanAmount;//期望贷款（范围1-100万元），单位：万元；要求必须为大于1万，小于或等于1万将拒绝
    private int age;//年龄
    private int recentCredit;//有无信用卡1:无2:有
    private String cityName;//当前居住地城市，如：深圳、上海等
    private int careerType;//职业信息0: 上班族(公司职员)1: 自雇人士(私营业主)2: 公务员
    private int monthlySalary;//月收入(税后月收入0- 100000元) ，单位：元
    private String ip;//注册用户IP地址
    private String cert;//安全验证串，该验证串用于服务端判断该请求是否合法cert的生成规则如：MD5(ChannelId_AdsId_Phone)例如，ChannelId= 100001, AdsId= 2000001,phone=15813814012MD5(“100001_2000001_15813814012”)
    private String isUsedWLD;//有无微粒贷 （没有默认为N）Y：是 N：否
    private int houseProperty;//名下房产（没有默认为1）1: 无房产2: 有房产，还贷中（有房贷）3: 有房产，无房贷（全款房）
    private int carProperty;//名下车辆（没有默认为1）1: 无汽车2: 有汽车，还贷中（有车贷）3: 有汽车，无车贷（全款车）
    private int isHasBx;//是否有寿险保单（没有默认为0）1：是0：否
    private int shebaoOrGongjijin;//社保或公积金（没有默认为3）1:缴费未满一年2:缴费一年以上3:无社保公积金
    private int payWay;//发薪方式0:银行代发1:现金
    private int workLong;//工作时间1: 现单位6个月以内2: 现单位6-12个月3: 现单位12-24个月4: 现单位24个月以上
    private int companyRegistLong;//营业执照1: 注册一年以下2: 注册一年以上

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRecentCredit() {
        return recentCredit;
    }

    public void setRecentCredit(int recentCredit) {
        this.recentCredit = recentCredit;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCareerType() {
        return careerType;
    }

    public void setCareerType(int careerType) {
        this.careerType = careerType;
    }

    public int getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(int monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getIsUsedWLD() {
        return isUsedWLD;
    }

    public void setIsUsedWLD(String isUsedWLD) {
        this.isUsedWLD = isUsedWLD;
    }

    public int getHouseProperty() {
        return houseProperty;
    }

    public void setHouseProperty(int houseProperty) {
        this.houseProperty = houseProperty;
    }

    public int getCarProperty() {
        return carProperty;
    }

    public void setCarProperty(int carProperty) {
        this.carProperty = carProperty;
    }

    public int getIsHasBx() {
        return isHasBx;
    }

    public void setIsHasBx(int isHasBx) {
        this.isHasBx = isHasBx;
    }

    public int getShebaoOrGongjijin() {
        return shebaoOrGongjijin;
    }

    public void setShebaoOrGongjijin(int shebaoOrGongjijin) {
        this.shebaoOrGongjijin = shebaoOrGongjijin;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public int getWorkLong() {
        return workLong;
    }

    public void setWorkLong(int workLong) {
        this.workLong = workLong;
    }

    public int getCompanyRegistLong() {
        return companyRegistLong;
    }

    public void setCompanyRegistLong(int companyRegistLong) {
        this.companyRegistLong = companyRegistLong;
    }

}
