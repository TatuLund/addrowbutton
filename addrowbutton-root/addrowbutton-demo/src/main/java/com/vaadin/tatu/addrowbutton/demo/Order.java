package com.vaadin.tatu.addrowbutton.demo;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    public enum Priority {
        HIGH, NORMAL, LOW;
    }

    private String product;
    private String customer;
    private Date orderTime;
    private int orderAmount;
    private int reservedAmount;
    private Priority priority;
    private boolean customized;

    public Order() {
        this.product = "";
        this.customer = "";
        this.orderTime = new Date();
        this.orderAmount = 0;
        this.reservedAmount = 0;
        this.priority = Priority.NORMAL;
        this.customized = false;    	
    }
    
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderDate) {
        orderTime = orderDate;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getReservedAmount() {
        return reservedAmount;
    }

    public void setReservedAmount(int reservedAmount) {
        this.reservedAmount = reservedAmount;
    }

    public boolean isCustomized() {
        return customized;
    }

    public void setCustomized(boolean customzied) {
        customized = customzied;
    }

    public double getCompletePercentage() {
    	if (orderAmount > 0) {
    		return reservedAmount / (double) orderAmount;
    	} else {
    		return 0;
    	}
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

}
