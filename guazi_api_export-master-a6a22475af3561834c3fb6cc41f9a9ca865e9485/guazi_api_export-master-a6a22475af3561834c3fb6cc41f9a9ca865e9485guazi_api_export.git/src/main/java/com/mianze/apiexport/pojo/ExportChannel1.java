package com.mianze.apiexport.pojo;

public class ExportChannel1 {
    private Integer id;
    private String channel;
    private  String channelName;
    private  String channelType;
    private  Integer billingMode;//结算模式1成功及结算，2有效A，3放款结算
    private String unitPrice;//单价
    private  Integer status=0;//开启关闭状态0关闭

    public ExportChannel1() {
        super();
    }

    public ExportChannel1(Integer id, String channel, String channelName, String channelType, Integer billingMode, String unitPrice, Integer status) {
        this.id = id;
        this.channel = channel;
        this.channelName = channelName;
        this.channelType = channelType;
        this.billingMode = billingMode;
        this.unitPrice = unitPrice;
        this.status = status;
    }

    @Override
    public String toString() {
        return "ExportChannel1{" +
                "id=" + id +
                ", channel='" + channel + '\'' +
                ", channelName='" + channelName + '\'' +
                ", channelType='" + channelType + '\'' +
                ", billingMode=" + billingMode +
                ", unitPrice='" + unitPrice + '\'' +
                ", status=" + status +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Integer getBillingMode() {
        return billingMode;
    }

    public void setBillingMode(Integer billingMode) {
        this.billingMode = billingMode;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
