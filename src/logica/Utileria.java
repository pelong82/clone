/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import modelo.Customer;

/**
 *
 * @author Administrator
 */
public class Utileria {
    
    
    public static double convertDouble(String cadena){
        try{
            double num = Double.parseDouble(cadena);
            return num;
        }
        catch(NumberFormatException | NullPointerException e){
            return -1;
        }
    }
    
    public static int convertInteger(String cadena){
        try{
            int num = Integer.parseInt(cadena);
            return num;
        }
        catch(NumberFormatException | NullPointerException e){
            return -1;
        }
    }
    
    public static long convertLong(String cadena){
        try{
            long num = Long.parseLong(cadena);
            return num;
        }
        catch(NumberFormatException | NullPointerException e){
            return -1;
        }
    }
    
    
    /**
     * convierte texto a telefono
     * @param cadena texto a convertir en telefono
     * @return regresa un entore positivo
     */
    public static long converPhone(String cadena){
        try{
            long num = Long.parseLong(cadena);
            return num;
        }
        catch(NumberFormatException | NullPointerException e){
            return -1;
        }
    }
    
    public static boolean searchString(String str, String search){
        return str.startsWith(search);
    }
    
    /**
     * Busca si una cadena en piesa por otra
     * @param customer
     * @param column
     * @param search
     * @return 
     */
    public static boolean searchNameAndCompanyCustomer(Customer customer, String column, String search){
        if(column.equals("Nombre")){
            String tmp = customer.getName();
            return tmp.startsWith(search);
        }
        else
            if(column.equals("Compañia")){
                String tmp = customer.getCompany();
                return tmp.startsWith(search);
            }
            else{
                String tmp = ""+customer.getPhone();
                return tmp.startsWith(search);
            }
    }
    
    public static String formateaFecha(Date d){
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        String f = ""+fecha.format(d);
        return f;
    }
    
    public static String formatMoneda(double aValor){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return format.format(aValor);
    }
    
    public static String limpiaCadena(String cdn){
        StringTokenizer token = new StringTokenizer(cdn,"$,");
        int numIngre = token.countTokens();
        String tmp = "";
        while(token.hasMoreTokens())
            tmp += token.nextToken();
        return tmp;
    }
    
}
