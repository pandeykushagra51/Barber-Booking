package com.example.mydream;

public class CustomerOrders {

    private String orderedId;
    private String customerId;
    private Long time;
    private String sellerId;
    private String productId;
    private Long status;


    public CustomerOrders(String orderedId, String customerId, Long time, String sellerId, String productId, Long status) {
    //    this.orderedId = orderedId;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
