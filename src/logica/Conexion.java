/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Customer;
import modelo.Item;

/**
 *
 * Esta clase realiza la conexion con la base de datos
 * y el CRUD de la aplicacion
 * @author Hector Manuel Rodriguez Mendez
 * @version 04/06/2015
 */
public class Conexion implements ConexionSQL{
    
    private Connection conexion;
    private ResultSet resultset;
    private int lastIdCustomer;
    private PreparedStatement prdstCustomerSearch;
    private PreparedStatement prdstCustomerAll;
    private PreparedStatement prdstCustomerInsert;
    private PreparedStatement prdstCustomerDelete;
    private PreparedStatement prdstCustomerUpdate;
    private PreparedStatement prdstCustomerSearchLike;
    private PreparedStatement prdstItem;
    private PreparedStatement prdstItemAll;
    private PreparedStatement prdstItemInsert;
    private PreparedStatement prdstItemDelete;
    private PreparedStatement prdstItemUpdate;
    private PreparedStatement prdstOrder;
    private PreparedStatement prdstOrderAll;
    private PreparedStatement prdstOrderInsert;
    private PreparedStatement prdstOrderDelete;
    private PreparedStatement prdstOrderUpdate;
    private PreparedStatement prdstOrderDetails;
    private PreparedStatement prdstOrderDetailsAll;
    private PreparedStatement prdstOrderDetailsInsert;
    private PreparedStatement prdstOrderDetailsDelete;
    private PreparedStatement prdstOrderDetailsUpdate;
    
    
    public Conexion(){
        conectar();
        makesSqls();
    }
    
    /**
     * Crea la conexion con la DB primero cargando el driver
     * y realizando la conexion
     */
    public void conectar(){
        try {
            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection("jdbc:sqlite:"+URL);
            System.out.println("Conexion Establecida....");
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR DATABASE "+ex, "Conexion", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * crea las senticias SQL para la DB
     */
    public void makesSqls(){
        try {
            prdstCustomerAll = conexion.prepareStatement(SELECT_ALL_CUSTOMERS);
            prdstCustomerSearch = conexion.prepareStatement(SELECT_CUSTOMER);
            prdstCustomerInsert = conexion.prepareStatement(INSERT_CUSTOMER, Statement.RETURN_GENERATED_KEYS);
            prdstCustomerDelete = conexion.prepareStatement(DELETE_CUSTOMER);
            prdstCustomerUpdate =conexion.prepareStatement(UPDATE_CUSTOMER);
            prdstItemAll = conexion.prepareStatement(SELECT_ALL_ITEMS);
            prdstItem = conexion.prepareStatement(SELECT_ITEM);
            prdstItemDelete = conexion.prepareStatement(DELETE_ITEM, Statement.RETURN_GENERATED_KEYS);
            prdstItemInsert = conexion.prepareStatement(INSERT_ITEM);
            prdstItemUpdate = conexion.prepareStatement(UPDATE_ITEM);
            /*prdstOrder = conexion.prepareStatement(SELECT_ORDER);
            prdstOrderAll = conexion.prepareStatement(SELECT_ALL_ORDER);
            prdstOrderDelete = conexion.prepareStatement(DELETE_ORDER);
            prdstOrderInsert = conexion.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            prdstOrderUpdate = conexion.prepareStatement(UPDATE_ORDERS);
            prdstOrderDetails = conexion.prepareStatement(SELECT_ORDER_DETAIL);
            prdstOrderDetailsAll = conexion.prepareStatement(SELECT_ALL_ORDER_DETAIL);
            prdstOrderDetailsDelete = conexion.prepareStatement(DELETE_ORDER_DETAIL);
            prdstOrderDetailsInsert = conexion.prepareStatement(INSERT_ORDER_DETAIL, Statement.RETURN_GENERATED_KEYS);
            prdstOrderDetailsUpdate = conexion.prepareStatement(UPDATE_ORDER_DETAIL);
            */
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Recupera todos los datos de la tabla 
     * customers
     */
    public ArrayList<Customer> getCustomer(){
        try {
            ResultSet res = prdstCustomerAll.executeQuery();
            ArrayList<Customer> custs = new ArrayList<>(); 
            while(res.next()){
                Customer customer = new Customer();
                customer.setCustomerId(res.getInt("customer_id"));
                customer.setCelPhone(res.getLong("cel_phone"));
                customer.setCity(res.getString("city"));
                customer.setCompany(res.getString("company"));
                customer.setDepartment(res.getString("department"));
                customer.setEmail(res.getString("email"));
                customer.setName(res.getString("name"));
                customer.setPhone(res.getLong("phone"));
                customer.setState(res.getString("state"));
                customer.setStreet(res.getString("street"));
                customer.setZip(res.getString("zip"));
                customer.setColony(res.getString("colony"));
                custs.add(customer);
            }
            return custs;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Imposible Cargar Clientes", "Conexion", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * busca un cliente por su telefono o celular
     * @param phone numero de telefono o celular
     * @return retorna un cliente
     */
    public Customer getCustomer(int phone){
        try {
            ResultSet res = prdstCustomerSearch.executeQuery();
            Customer customer = new Customer();
            while(res.next()){
                customer.setCustomerId(res.getInt("customer_id"));
                customer.setCelPhone(res.getLong("cel_phone"));
                customer.setCity(res.getString("city"));
                customer.setCompany(res.getString("company"));
                customer.setDepartment(res.getString("department"));
                customer.setEmail(res.getString("email"));
                customer.setName(res.getString("name"));
                customer.setPhone(res.getLong("phone"));
                customer.setState(res.getString("state"));
                customer.setStreet(res.getString("street"));
                customer.setColony(res.getString("colony"));
                customer.setZip(res.getString("zip"));
            }
            return customer;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error De Comunicacion con DB Clientes", "Conexion", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public ArrayList<Item> getITem(){
        try {
            ResultSet res = prdstItemAll.executeQuery();
            ArrayList<Item> items = new ArrayList<>();
            while(res.next()){
                Item item = new Item();
                item.setName(res.getString("name"));
                item.setItemId(res.getInt("item_id"));
                item.setUnitPrice(res.getDouble("unit_price"));
                items.add(item);
            }
            return items;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error De Comunicacion con DB Items", "Conexion", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * busca un item por su id
     * @param aItemId id del item a buscar
     * @return regrea el objeto item o null sino lo encuentra
     */
    public Item getITem(int aItemId){
        try {
            prdstItem.setInt(1, aItemId);
            ResultSet res = prdstItem.executeQuery();
            Item item = new Item();
            while(res.next()){    
                item.setName(res.getString("name"));
                item.setItemId(res.getInt("item_id"));
                item.setUnitPrice(res.getDouble("unit_price"));
            }
            return item;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error De Comunicacion con DB Items", "Conexion", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean deleteCustomer(int aId){
        try {
            prdstCustomerDelete.setLong(1, aId);
            boolean res = prdstCustomerDelete.execute();
            return !res;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    /**
     * elimina un item 
     * @param aItemId id del item ha eliminar
     * @return regres true si se elimino
     */
    public boolean deleteItem(int aItemId){
        try {
            prdstItemDelete.setInt(1, aItemId);
            boolean execute = prdstItemDelete.execute();
            return !execute;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error De Comunicacion con DB Items", "Conexion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /***
     * inserta los nuevos clientes
     * @param customer define el nuevo cliente
     * @return devuelve true si realizo con exito la 
     * insercion
     */
    public boolean saveCustomer(Customer customer){
        try {
            prdstCustomerInsert.setString(1, customer.getName());
            prdstCustomerInsert.setString(2, customer.getCompany());
            prdstCustomerInsert.setString(3, customer.getDepartment());
            prdstCustomerInsert.setString(4, customer.getEmail());
            prdstCustomerInsert.setLong(5, customer.getPhone());
            prdstCustomerInsert.setLong(6, customer.getCelPhone());
            prdstCustomerInsert.setString(7, customer.getStreet());
            prdstCustomerInsert.setString(8, customer.getColony());
            prdstCustomerInsert.setString(9, customer.getCity());
            prdstCustomerInsert.setString(10, customer.getState());
            prdstCustomerInsert.setString(11, customer.getZip());
            boolean res  = prdstCustomerInsert.execute();
            ResultSet generatedKeys = prdstCustomerInsert.getGeneratedKeys();
            while(generatedKeys.next())
                lastIdCustomer = generatedKeys.getInt(1);
            customer.setCustomerId(lastIdCustomer);
            return !res;
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error De Comunicacion con DB Cliente", "Conexion", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Guarda un item en la BD
     * @param aItem item a guardar
     * @return regresa true si tuvo exito
     * o false en caso contrario
     */
    public boolean saveItem(Item aItem){
        try {
            prdstItemInsert.setString(1, aItem.getName());
            prdstItemInsert.setDouble(2, aItem.getUnitPrice());
            boolean execute = prdstItemInsert.execute();
            ResultSet res = prdstItemInsert.getGeneratedKeys();
            while(res.next())
                aItem.setItemId(res.getInt(1));  
            return !execute;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error De Comunicacion con DB Items", "Conexion", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
   
    /**
     * actualiza los datos de un cliente
     * @param phone telefono del cliente
     * @return regresa false si se realizo con exito la 
     * accion
     */
    public boolean updateCustomer(Customer customer){
        try {
            prdstCustomerUpdate.setString(1, customer.getName());
            prdstCustomerUpdate.setString(2, customer.getCompany());
            prdstCustomerUpdate.setString(3, customer.getDepartment());
            prdstCustomerUpdate.setString(4, customer.getEmail());
            prdstCustomerUpdate.setLong(5, customer.getPhone());
            prdstCustomerUpdate.setLong(6, customer.getCelPhone());
            prdstCustomerUpdate.setString(7, customer.getStreet());
            prdstCustomerUpdate.setString(8, customer.getColony());
            prdstCustomerUpdate.setString(9, customer.getCity());
            prdstCustomerUpdate.setString(10, customer.getState());
            prdstCustomerUpdate.setString(11, customer.getZip());
            prdstCustomerUpdate.setInt(12, customer.getCustomerId());
            boolean exe = prdstCustomerUpdate.execute();
            return !exe;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Al Actualizar Cliente", "Conexion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Actualiza los datos de 
     * un item  
     * @param aItem item para actualizar
     * @return regresa true si no hubo problemas 
     * y false en caso contrario
     */
    public boolean updateItem(Item aItem){
        try {
            prdstItemUpdate.setInt(1, aItem.getItemId());
            prdstItemUpdate.setString(2, aItem.getName());
            prdstItemUpdate.setDouble(3, aItem.getUnitPrice());
            boolean execute = prdstItemUpdate.execute();
            return !execute;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error De Comunicacion con DB Items", "Conexion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Regresa el id del ultimo 
     * cliente guardado
     * @return id del cliente
     */
    public int getLastIdCustomer() {
        return lastIdCustomer;
    }

    public void setLastIdCustomer(int lastIdCustomer) {
        this.lastIdCustomer = lastIdCustomer;
    }
    
    public static void main(String[] args){
        //new Conexion();
    }
}

