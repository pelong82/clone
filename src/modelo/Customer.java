/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Hector Manuel Rodriguez Mendez
 * class que define a los clientes
 */
public class Customer {
    
    private int customerId;
    private String name;
    private String company;
    private String department;
    private String email;
    private long phone;
    private long celPhone;
    private String street;
    private String colony;
    private String city;
    private String state;
    private String zip;
    
    public Customer(){
        customerId = 0;
        name = "";
        company = "";
        department = "";
        email = "";
        phone = 0;
        celPhone = 0;
        colony = "";
        street = "";
        city = "";
        state = "";
        zip = "";
    }

    public Customer(int customerId, String name, String company, String department, String email, long phone, long celPhone, String street, String colony, String city, String state, String zip) {
        this.customerId = customerId;
        this.name = name;
        this.company = company;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.celPhone = celPhone;
        this.street = street;
        this.colony = colony;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public long getCelPhone() {
        return celPhone;
    }

    public void setCelPhone(long celPhone) {
        this.celPhone = celPhone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    
    
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   