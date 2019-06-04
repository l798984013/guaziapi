package com.mianze.apiexport.pojo;

public class ChannelOrderExport {

    private String orderNo;

    private int channelId;

    private int acceptAll;


    public ChannelOrderExport(){}

    public ChannelOrderExport(long String,int channelId,int acceptAll){
        this.orderNo = orderNo;
        this.channelId = channelId;
        this.acceptAll = acceptAll;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getAcceptAll() {
        return acceptAll;
    }

    public void setAcceptAll(int acceptAll) {
        this.acceptAll = acceptAll;
    }
}
