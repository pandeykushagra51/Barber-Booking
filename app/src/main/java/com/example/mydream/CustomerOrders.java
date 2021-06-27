package com.example.mydream;

public class CustomerOrders {

    private String orderedId;
    private String customerId;
    private int time;
    private String sellerId;
    private String productId;
    private int status;


    public CustomerOrders(String orderedId, String customerId, int time, String sellerId, String productId, int status) {
        this.orderedId = orderedId;
        this.customerId = customerId;
        this.time = time;
        this.sellerId = sellerId;
        this.productId = productId;
        this.status = status;
    }

    public String getOrderedId() {
        return orderedId;
    }

    public void setOrderedId(String orderedId) {
        this.orderedId = orderedId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
