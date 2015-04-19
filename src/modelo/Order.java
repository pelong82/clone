/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Timestamp;



/**
 *
 * @author Administrator
 */
public class Order {
    
    private int orderId;
    private int customerId;
    private Timestamp orderDate;
    private String attn;
    private String supplier;
    private String billTo;
    private String comments;
    private short review;
    private Timestamp lastUpdateDate;
    private double subtotal;
    private double total;
    
    public Order(){
        
    }

    public Order(int orderId, int customerId, Timestamp orderDate, String attn, String supplier, String billTo, String comments, short review, Timestamp lastUpdateDate, double subtotal, double total) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.attn = attn;
        this.supplier = supplier;
        this.billTo = billTo;
        this.comments = comments;
        this.review = review;
        this.lastUpdateDate = lastUpdateDate;
        this.subtotal = subtotal;
        this.total = total;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getAttn() {
        return attn;
    }

    public void setAttn(String attn) {
        this.attn = attn;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getBillTo() {
        return billTo;
    }

    public void setBillTo(String billTo) {
        this.billTo = billTo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public short getReview() {
        return review;
    }

    public void setReview(short review) {
        this.review = review;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
    
    
}
