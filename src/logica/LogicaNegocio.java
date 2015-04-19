/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import logica.Conexion;
import logica.Utileria;
import modelo.Customer;
import modelo.Item;

/**
 *
 * @author Administrator
 */
public class LogicaNegocio {
    
    private final Conexion conexion;
    private ArrayList<Customer> customers;
    private Customer lastCustomer;
    private ArrayList<Item> items;
    
    public LogicaNegocio(){
        conexion = new Conexion();
        items = new ArrayList<>();
        customers = new ArrayList<>();
        lastCustomer = new Customer();
    }
    
    /**
     * Elimina un cliente por su id
     * @param aId id del cliente
     * @return true si tiene exito
     * o false si falla
     */
    public boolean deleteCustomer(int aId){
        return conexion.deleteCustomer(aId);
    }   
    
    /**
     * Elimina un Ite por su id
     * @param aId id del item
     * @return true si tiene exito
     * y false en caso contrario
     */
    public boolean deleteItem(int aId){
        return conexion.deleteItem(aId);
    }
    
    /**
     * Recupera todos los clientes
     * @return un objeto con los 
     * clientes
     */
    public ArrayList<Customer> getCustomers(){
        return conexion.getCustomer();
    }
    
    /**
     * Recupera todos lo items 
     * @return y regresa un objeto
     * ArrayList que contiene a todos
     * los objetos
     */
    public ArrayList<Item> getItems(){
        return conexion.getITem();
    }
    
    /**
     * Guarda el Cliente en la DB
     * @param aCustomer Cliente nuevo
     * @return regresa true si tuvo exito
     * o false en caso contrario
     */
    public boolean saveCustomer(Customer aCustomer){
        return conexion.saveCustomer(aCustomer);
    }
    
    /**
     * Guarda un item en la DB
     * @param aItem item a guardar
     * @return true si tiene exito
     * y false en caso contrario
     */
    public boolean saveItem(Item aItem){
        return conexion.saveItem(aItem);
    }
    
    /**
     * Actualiza los datos de un cliente
     * @param customer
     * @return regresa true si tiene exito
     * y false en caso contrario
     */
    public boolean updateCustomer(Customer customer){
        return conexion.updateCustomer(customer);
    }
    
    /**
     * Actualiza los datos del item
     * @param item item con la nueva informacion
     * @return true si tuvo exito y false en caso
     * contrario
     */
    public boolean updateItem(Item item){
        return conexion.updateItem(item);
        
    }
    
    
    
}
