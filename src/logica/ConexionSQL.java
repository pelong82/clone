/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author Administrator
 */
public interface ConexionSQL {
    
    public static final String URL="C:\\protoClon\\protoClon.sqlite";
    public static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customers";
    public static final String SELECT_CUSTOMER = "SELECT * FROM customers WHERE cel_phone =? OR phone = ?";
    public static final String INSERT_CUSTOMER = "INSERT INTO customers(name, company, department, "
            + "email, phone, cel_phone, street, colony, city, state, zip) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_CUSTOMER = "DELETE FROM customers WHERE customer_id = ?";
    public static final String UPDATE_CUSTOMER = "UPDATE customers SET name=?, company=?, department=?, email=?, phone=?, cel_phone=?, street=?, colony=?, city=?, state=?, zip=? WHERE customer_id=?";
    
    public static final String SELECT_ALL_ITEMS = "SELECT * FROM items";
    public static final String SELECT_ITEM = "SELECT * FROM items WHERE item_id = ?";
    public static final String INSERT_ITEM = "INSERT INTO items(name,unit_price) VALUES(?, ?)";
    public static final String DELETE_ITEM = "DELETE FROM items WHERE item_id = ?";
    public static final String UPDATE_ITEM = "UPDATE items SET name = ?, unit_price = ? WHERE item_id = ?";
    
    public static final String SELECT_ALL_ORDERS = "SELECT * FROM orders INNER JOIN orders_details AS ords ON orders.order_id = ords.order_id";
    public static final String SELECT_ORDER = "SELECT * FROM orders INNER JOIN orders_details AS ords ON orders.order_id = ords.order_id WHERE order_id = ?";
    public static final String INSERT_ORDER = "INSERT INTO orders(customer_id, attn, dupplier, bill_to, comments,review, subtotal, total) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
}
