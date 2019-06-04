package com.mianze.apiexport.pojo;

public class SetOrderStatus {

    private long orderNo;

    private String key;

    private Integer discountedState;//1打折 0已售出

    public SetOrderStatus(){}

    public SetOrderStatus(long orderNo, String key){
        this.orderNo = orderNo;
        this.key = key;
    }

    public SetOrderStatus(long orderNo, String key, Integer discountedState) {
        this.orderNo = orderNo;
        this.key = key;
        this.discountedState = discountedState;
    }

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getDiscountedState() {
        return discountedState;
    }

    public void setDiscountedState(Integer discountedState) {
        this.discountedState = discountedState;
    }
}
