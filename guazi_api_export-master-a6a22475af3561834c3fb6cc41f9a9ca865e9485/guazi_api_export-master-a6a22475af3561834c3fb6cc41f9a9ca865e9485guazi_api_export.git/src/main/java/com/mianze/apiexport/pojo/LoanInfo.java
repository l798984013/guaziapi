package com.mianze.apiexport.pojo;

import java.util.List;

public class LoanInfo {
    private Integer id;
    private Integer tid;
    private String userId;
    private String userName;
    private String phone;
    private String phoneMD5;
    private Integer amount;
    private Integer term;
    private String idCard="-1";
    private String location;
    private Integer married=2;
    private Integer degree=5;
    private Integer job=-1;
    private Integer companyType=0;
    private Integer monthlyIncome=-1;
    private Integer income=0;
    private Integer workYear=0;
    private Integer registerTime=6;
    private Integer companyMonthlyIncome=-1;
    private Integer sheBao=5;
    private Integer gongJiJin=5;
    private Integer houseProperty=-1;
    private Integer house=0;
    private Integer houseAssessment=0;
    private String houseAddress;
    private Integer carProperty=-1;
    private Integer car=0;
    private Integer carAssessment=0;
    private String carAddress;
    private Integer debt=-1;
    private Integer chit=2;
    private Integer status=6;
    private String webank="-1";
    private String zmxy="-1";
    private Integer jiebei = -1;
    private String ip;
    private String channel;
    private String sign;
    private List<Integer> tagArray;
    private String tags;
    private Integer result;
    private Integer valid;
    private String sex="-1";
    private Integer age=-1;
    private String registration = "-1";
    private Long orderNo;
    private Integer channelBranch=-1;
    private String isSecure="-1";
    private String userAgent="-1";
    private String is_secure = "-1";
    private String user_agent="-1";
    private Integer policySituation=0;

    public String getPhoneMD5() {
        return phoneMD5;
    }

    public void setPhoneMD5(String phoneMD5) {
        this.phoneMD5 = phoneMD5;
    }

    public Integer getPolicySituation() {
        return policySituation;
    }

    public void setPolicySituation(Integer policySituation) {
        if (policySituation!=null) {
            this.policySituation = policySituation;
        }
    }

    public String getIsSecure() {
        return isSecure;
    }

    public void setIsSecure(String isSecure) {
        if (isSecure!=null) {
            this.isSecure = isSecure;
            this.is_secure = isSecure;
        }
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        if (userAgent!=null) {
            this.userAgent =user_agent;
        }
    }

    public String getIs_secure() {
        return is_secure;
    }

    public void setIs_secure(String is_secure) {
        if (is_secure!=null) {
            this.is_secure = is_secure;
            this.isSecure = is_secure;
        }
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        if (user_agent!=null) {
            this.user_agent = userAgent;
        }
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getChannelBranch() {
        return channelBranch;
    }

    public void setChannelBranch(Integer channelBranch) {
        if (channelBranch!=null) {
            this.channelBranch = channelBranch;
        }
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if (sex!=null) {
            this.sex = sex;
        }
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        if (age!=null) {
            this.age = age;
        }
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        if (idCard!=null) {
            this.idCard = idCard;
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMarried() {
        return married;
    }

    public void setMarried(Integer married) {
        if (married!=null) {
            this.married = married;
        }
    }

    public Integer getJob() {
        return job;
    }

    public void setJob(Integer job) {
        if (job!=null) {
            this.job = job;
        }
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        if (companyType!=null) {
            this.companyType = companyType;
        }
    }

    public Integer getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Integer monthlyIncome) {
        if (monthlyIncome!=null) {
            this.monthlyIncome = monthlyIncome;
        }
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        if (income!=null) {
            this.income = income;
        }
    }

    public Integer getWorkYear() {
        return workYear;
    }

    public void setWorkYear(Integer workYear) {
        if (workYear!=null) {
            this.workYear = workYear;
        }
    }

    public Integer getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Integer registerTime) {
        if (registerTime!=null) {
            this.registerTime = registerTime;
        }
    }

    public Integer getCompanyMonthlyIncome() {
        return companyMonthlyIncome;
    }

    public void setCompanyMonthlyIncome(Integer companyMonthlyIncome) {
        if (companyMonthlyIncome!=null) {
            this.companyMonthlyIncome = companyMonthlyIncome;
        }
    }

    public Integer getSheBao() {
        return sheBao;
    }

    public void setSheBao(Integer sheBao) {
        if (sheBao!=null) {
            this.sheBao = sheBao;
        }
    }

    public Integer getGongJiJin() {
        return gongJiJin;
    }

    public void setGongJiJin(Integer gongJiJin) {
        if (gongJiJin!=null) {
            this.gongJiJin = gongJiJin;
        }
    }

    public Integer getHouseProperty() {
        return houseProperty;
    }

    public void setHouseProperty(Integer houseProperty) {
        if (houseProperty!=null) {
            this.houseProperty = houseProperty;
        }
    }

    public Integer getHouse() {
        return house;
    }

    public void setHouse(Integer house) {
        if (house!=null) {
            this.house = house;
        }
    }

    public Integer getHouseAssessment() {
        return houseAssessment;
    }

    public void setHouseAssessment(Integer houseAssessment) {
        if (houseAssessment!=null) {
            this.houseAssessment = houseAssessment;
        }
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        if (houseAddress!=null) {
            this.houseAddress = houseAddress;
        }
    }

    public Integer getCarProperty() {
        return carProperty;
    }

    public void setCarProperty(Integer carProperty) {
        if (carProperty!=null) {
            this.carProperty = carProperty;
        }
    }

    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        if (car!=null) {
            this.car = car;
        }
    }

    public Integer getCarAssessment() {
        return carAssessment;
    }

    public void setCarAssessment(Integer carAssessment) {
        if (carAssessment!=null) {
            this.carAssessment = carAssessment;
        }
    }

    public String getCarAddress() {
        return carAddress;
    }

    public void setCarAddress(String carAddress) {
        this.carAddress = carAddress;
    }

    public Integer getDebt() {
        return debt;
    }

    public void setDebt(Integer debt) {
        if (debt!=null) {
            this.debt = debt;
        }
    }

    public Integer getChit() {
        return chit;
    }

    public void setChit(Integer chit) {
        if (chit!=null) {
            this.chit = chit;
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        if (status!=null) {
            this.status = status;
        }
    }

    public String getWebank() {
        return webank;
    }

    public void setWebank(String webank) {
        if (webank!=null) {
            this.webank = webank;
        }
    }

    public String getZmxy() {
        return zmxy;
    }

    public void setZmxy(String zmxy) {
        if (zmxy!=null) {
            this.zmxy = zmxy;
        }
    }

    public List<Integer> getTagArray() {
        return tagArray;
    }

    public void setTagArray(List<Integer> tagArray) {
        this.tagArray = tagArray;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        if (degree!=null) {
            this.degree = degree;
        }
    }

    public Integer getJiebei() {
        return jiebei;
    }

    public void setJiebei(Integer jiebei) {
        if (jiebei!=null) {
            this.jiebei = jiebei;
        }
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        if (registration!=null) {
            this.registration = registration;
        }
    }

}
