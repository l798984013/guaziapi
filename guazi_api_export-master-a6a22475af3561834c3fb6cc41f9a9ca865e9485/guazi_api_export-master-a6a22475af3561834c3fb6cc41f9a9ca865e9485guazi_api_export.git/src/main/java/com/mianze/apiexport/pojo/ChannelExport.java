package com.mianze.apiexport.pojo;

public class ChannelExport {

    private int id;

    //延迟时间,单位小时
    private float delay;

    //是否接收所有单子(包括二手单)
    private int acceptAll;

    //最低额度
    private int minAmount;


    //最高额度
    private int maxAmount;

    //是否限流
    private int isRestrictAmount;

    //限流值
    private Integer restrictAmount;


    //是否删除
    private int isDelete;

    //是否开启时间限流
    private int isRestrictTime;

    //开始时间
    private String beginTime;
    //结束时间
    private String endTime;

    //优接单是否打折出售
    private int discountedState ;

    //设置不参与导出（进件渠道）
    private String unsupportChannel ;

    public int getIsRestrictAmount() {
        return isRestrictAmount;
    }

    public void setIsRestrictAmount(int isRestrictAmount) {
        this.isRestrictAmount = isRestrictAmount;
    }

    public Integer getRestrictAmount() {
        return restrictAmount;
    }

    public void setRestrictAmount(Integer restrictAmount) {
        this.restrictAmount = restrictAmount;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }

    public int getAcceptAll() {
        return acceptAll;
    }

    public void setAcceptAll(int acceptAll) {
        this.acceptAll = acceptAll;
    }

    public int getIsRestrictTime() {
        return isRestrictTime;
    }

    public void setIsRestrictTime(int isRestrictTime) {
        this.isRestrictTime = isRestrictTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public int getDiscountedState() {
        return discountedState;
    }

    public void setDiscountedState(int discountedState) {
        this.discountedState = discountedState;
    }

    public String getUnsupportChannel() {
        return unsupportChannel;
    }

    public void setUnsupportChannel(String unsupportChannel) {
        this.unsupportChannel = unsupportChannel;
    }
}
