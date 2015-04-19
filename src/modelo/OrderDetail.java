/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Administrator
 */
public class OrderDetail {
    
    private int orderDetailId;
    private int orderId;
    private int itemId;
    private short um;
    private short qty;
    private double totalPrice;
    
    public OrderDetail(){
        orderDetailId = 0;
        orderId = 0;
        itemId = 0;
        um = 0;
        qty = 0;
        totalPrice = 0;
    }

    public OrderDetail(int orderDatailId, int orderId, int itemId, short um, short qty, double totalPrice) {
        this.orderDetailId = orderDatailId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.um = um;
        this.qty = qty;
        this.totalPrice = totalPrice;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public short getUm() {
        return um;
    }

    public void setUm(short um) {
        this.um = um;
    }

    public short getQty() {
        return qty;
    }

    public void setQty(short qty) {
        this.qty = qty;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
}
