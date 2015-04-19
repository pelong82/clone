/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Hector Manuel Rodriguez Mendez
 * clase productos
 */
public class Item {
    
    private int itemId;
    private String name;
    private double unitPrice;
    
    
    public Item(){
        itemId = 0;
        name = "";
        unitPrice = 0;
    }

    public Item(int itemId, String name, double unitPrice) {
        this.itemId = itemId;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
}
