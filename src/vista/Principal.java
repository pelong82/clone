/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Customer;
import logica.LogicaNegocio;
import logica.Utileria;
import modelo.Item;

/**
 *
 * @author Administrator
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        this.setLocationRelativeTo(null);
        logica = new LogicaNegocio();
        listCustomers = new ArrayList<>();
        listItems = new ArrayList<>();
        initTablaCustomers();
        initTablaItems();
        loadCustomers();
        loadItems();
        disableBtSaveCustomer();
        disableBtSaveItem();
        disableBtsDeleteAndUpdateCustomer();
        disableBtsDeleteAndUpdateItem();
        jTabbedPaneIPrincipal.setSelectedIndex(2);
    }
    
    /**
     * Agrega un objeto Customer al la Tabla Customer
     * @param aCustomer 
     */
    private void addLastCustomerTable(Customer aCustomer){
        registrosTablaCustomer[0] = aCustomer.getName();
        registrosTablaCustomer[1] = aCustomer.getCompany();
        modeloTablaCustomer.addRow(registrosTablaCustomer);
    }
    
    /**
     * Actualiza un registro en especifico
     * @param aCustomer
     * @param row fila ha actualizar
     */
    private void addLastCustomerTable(Customer aCustomer, int row){
        modeloTablaCustomer.setValueAt(aCustomer.getName(), row, 0);
        modeloTablaCustomer.setValueAt(aCustomer.getCompany(), row, 1);
    }
    
    /**
     * Agrega un item a la tabla Items
     * @param item item a agregar
     */
    private void addLastItemTable(Item item){
        registrosTablaItem[0] = ""+item.getItemId();
        registrosTablaItem[1] = item.getName();
        String tmp = Utileria.formatMoneda(item.getUnitPrice());
        registrosTablaItem[2] = tmp;
        modeloTablaItem.addRow(registrosTablaItem);
    }
    
    private void addLastItemTable(Item item, int row){
        modeloTablaItem.setValueAt(item.getName(), row, 1);
        String tmp = Utileria.formatMoneda(item.getUnitPrice());
        modeloTablaItem.setValueAt(tmp, row, 2);
    }
    
    /**
     * Cambia el look de la aplicacion
     */
    private void cambiarLookAndFeel(){
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    /**
     * Limpa el formulario de los clientes
     */
    private void clearCustomerForm(){
        jTextFieldCel.setText("");
        jTextFieldCity.setText("");
        jTextFieldCompany.setText("");
        jTextFieldDepto.setText("");
        jTextFieldEmail.setText("");
        jTextFieldName.setText("");
        jTextFieldPhone.setText("");
        jTextFieldSearch.setText("");
        jTextFieldState.setText("");
        jTextFieldStreet.setText("");
        jTextFieldZip.setText("");
        jTextFieldColony.setText("");
        jTableCustomers.clearSelection();
    }
    
    /**
     * limpia el formulario de items
     */
    private void clearItemForm(){
        jTextFieldItemId.setText("");
        jTextFieldItemName.setText("");
        jTextFieldItemPrice.setText("");
        jTableItems.clearSelection();
    }
    
    /**
     * Elimina un cliente 
     */
    private void deleteCustomer(){
        int row = jTableCustomers.getSelectedRow();
        if(row > -1){
            int customerId = listCustomers.get(row).getCustomerId();
            boolean res = logica.deleteCustomer(customerId);
            if(res){
                listCustomers.remove(row);
                modeloTablaCustomer.removeRow(row);
                updateTableCustomers();
                clearCustomerForm();
                JOptionPane.showMessageDialog(null, "Cliente Eliminado", "Clientes", JOptionPane.INFORMATION_MESSAGE);
                disableBtsDeleteAndUpdateCustomer();
            }
            else
                JOptionPane.showMessageDialog(null, "Error Al Eliminar El Clinete", "Clientes", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteItem(){
        int row = jTableItems.getSelectedRow();
        if(row > -1){
            int itemId = listItems.get(row).getItemId();
            boolean res = logica.deleteItem(itemId);
            if(res){
                listItems.remove(row);
                modeloTablaItem.removeRow(row);
                updateItem();
                clearItemForm();
                JOptionPane.showMessageDialog(null, "Item Eliminado", "Items", JOptionPane.INFORMATION_MESSAGE);
                disableBtsDeleteAndUpdateItem();
            }
            else
                JOptionPane.showMessageDialog(null, "Error Al Eliminar El Item", "Items", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Desactiva los Botones delete y update
     * del panel de Clientes
     */
    private void disableBtsDeleteAndUpdateCustomer(){
        jButtonDeleteCustomer.setEnabled(false);
        jButtonUpdateCustomer.setEnabled(false);
    }
    
    /**
     * Desactiva los botones delete y update
     * del panel Items
     */
    private void disableBtsDeleteAndUpdateItem(){
        jButtonDeleteItem.setEnabled(false);
        jButtonUpdateItem.setEnabled(false);
    }
    
    /**
     * Desactiva el boton save del panel
     * clientes
     */
    private void disableBtSaveCustomer(){
        jButtonSaveCustomer.setEnabled(false);
    }
    
    /**
     * Desactiva el boton save
     * del panel de Items
     */
    private void disableBtSaveItem(){
        jButtonSaveItem.setEnabled(false);
    }
    
    /**
     * Activa los Botones delete y update
     * del panel de Clientes
     */
    private void enableBtsDeleteAndUpdateCustomer(){
        jButtonDeleteCustomer.setEnabled(true);
        jButtonUpdateCustomer.setEnabled(true);
    }
    
    /**
     * Activa los Botones delete y update del panel de
     * items
     */
    private void enableBtsDeleteAndUpdateItem(){
        jButtonDeleteItem.setEnabled(true);
        jButtonUpdateItem.setEnabled(true);
    }
    
    /**
     * Activa el boton save del 
     * panel clientes
     *
     */
    private void enableBtSaveCustomer(){
        jButtonSaveCustomer.setEnabled(true);
    }
    
    /**
     * Activa el boton save del
     * panel de items
     */
    private void enableBtSaveItem(){
        jButtonSaveItem.setEnabled(true);
    }
    
    
    /**
     * Inicia la configuracion de la Tabla Clientes
     */
    private void initTablaCustomers(){
        this.makeModelTableCustomers();
        jTableCustomers.setModel(modeloTablaCustomer);
        this.formatoTablaCustomers();
    }
    
    private void initTablaItems(){
        makeModelTableItems();
        jTableItems.setModel(modeloTablaItem);
        formatoTablaItems();
    }
    
    /**
     * Da formato a la tabla clientes
     */
    private void formatoTablaCustomers(){
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        //tamano del texto de las celdas y centrado
        //jTableCustomers.getColumnModel().getColumn(0).setPreferredWidth(300);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.LEFT );
        jTableCustomers.setDefaultRenderer(String.class, centerRenderer);
        
        int c = jTableCustomers.getColumnCount();
        for(int x=1;x<c;x++)
            jTableCustomers.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        
        Font fuente=new Font("Arial", Font.BOLD, 12);
        jTableCustomers.setFont(fuente);
        jTableCustomers.setRowHeight(16);
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
    }
    
    private void formatoTablaItems(){
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        //tamano del texto de las celdas y centrado
        jTableItems.getColumnModel().getColumn(1).setPreferredWidth(300);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.LEFT );
        jTableItems.setDefaultRenderer(String.class, centerRenderer);
        
        int c = jTableCustomers.getColumnCount();
        for(int x=1;x<c;x++)
            jTableCustomers.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        
        Font fuente=new Font("Arial", Font.BOLD, 12);
        jTableItems.setFont(fuente);
        jTableItems.setRowHeight(16);
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
    }
    
    /**
     * Llena el formulario de los Clietes
     */
    private void fillDataForm(){
        int row = jTableCustomers.getSelectedRow();
        if(row != -1){
            Customer customer = listCustomers.get(row);
            jTextFieldCel.setText(""+customer.getCelPhone());
            jTextFieldCity.setText(customer.getCity());
            jTextFieldCompany.setText(customer.getCompany());
            jTextFieldDepto.setText(customer.getDepartment());
            jTextFieldEmail.setText(customer.getEmail());
            jTextFieldName.setText(customer.getName());
            jTextFieldPhone.setText(""+customer.getPhone());
            jTextFieldState.setText(customer.getState());
            jTextFieldStreet.setText(customer.getStreet());
            jTextFieldZip.setText(customer.getZip());
            jTextFieldColony.setText(customer.getColony());
        }
    }
    
    private void fillItemForm(){
        int row = jTableItems.getSelectedRow();
        if(row > -1){
            Item item = listItems.get(row);
            jTextFieldItemId.setText(""+item.getItemId());
            jTextFieldItemName.setText(item.getName());
            jTextFieldItemPrice.setText(""+item.getUnitPrice());
        }
    }
    
    /**
     * Crea el Modelo de la table
     * clientes
     */
    private void makeModelTableCustomers(){
        DefaultTableModel mod;
        String[] columnas = {"NOMBRE","COMPAÑIA"};
        String[] filas = new String[2];
        mod = new DefaultTableModel(null,columnas){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTablaCustomer = mod;
        registrosTablaCustomer = filas;
    }
    
    /**
     * Crea el Modelo de la tabla items
     */
    private void makeModelTableItems(){
        DefaultTableModel mod;
        String[] columnas = {"ID","Nombre","Precio"};
        String[] filas = new String[3];
        mod = new DefaultTableModel(null,columnas){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTablaItem = mod;
        registrosTablaItem = filas;
    }
    
    /**
     * carga los clientes en la tabla
     */
    private void loadCustomers(){
        listCustomers = logica.getCustomers();
        for(Customer custo : listCustomers){
            addLastCustomerTable(custo);
        }
        updateTableCustomers();
    }
    
    private void loadItems(){
        listItems = logica.getItems();
        for(Item item : listItems){
            addLastItemTable(item);
        }
        updateTableItems();
    }
    
    /**
     * Guarda un clientes en la DB
     */
    private void saveCustomer(){
        String cel = jTextFieldCel.getText();
        String city = jTextFieldCity.getText();
        String company = jTextFieldCompany.getText();
        String deptop = jTextFieldDepto.getText();
        String email = jTextFieldEmail.getText();
        String name = jTextFieldName.getText();
        String phone = jTextFieldPhone.getText();
        String state = jTextFieldState.getText();
        String street = jTextFieldStreet.getText();
        String zip = jTextFieldZip.getText();
        String colony = jTextFieldColony.getText();
        Customer customer = validateCustomerForm(name, company, deptop, email, phone, cel, state, street, colony, city, zip);
        if(customer != null){
            boolean res = logica.saveCustomer(customer);
            if(res){
                addLastCustomerTable(customer);
                updateTableCustomers();
                listCustomers.add(customer);
                JOptionPane.showMessageDialog(this, "Cliente Guardado", "Clientes", JOptionPane.INFORMATION_MESSAGE);
                enableBtsDeleteAndUpdateCustomer();
                disableBtSaveCustomer();
            }
            else{
                JOptionPane.showMessageDialog(this, "No Se Pudo Guardar El Nuevo Cliente Contactar con El Administrador", "Clientes", JOptionPane.ERROR_MESSAGE);
            }   
        }
    }
    
    private void saveItem(){
        String name = jTextFieldItemName.getText();
        String price = jTextFieldItemPrice.getText();
        Item item = validateItemForm(name, price);
        if(item != null){
            boolean res = logica.saveItem(item);
            if(res){
                listItems.add(item);
                addLastItemTable(item);
                updateTableItems();
                enableBtsDeleteAndUpdateItem();
                disableBtSaveItem();
                JOptionPane.showMessageDialog(this, "Item Guardado", "Items", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, "No Se Pudo Guardar El Item", "Items", JOptionPane.ERROR_MESSAGE);
            }   
        }
    }
    
    /**
     * Busca un cliente por "Compañia", "Nombre" o "Telefono"
     * @param search cliente a buscar
     */
    private void searchNameAndCompany(String search){
        ArrayList<Customer> listSeatch = new ArrayList<>();
        Object clone = listCustomers.clone();
        for(Customer customer : listCustomers){
            String searchFor = ""+jComboBoxSearch.getSelectedItem();
            boolean res = Utileria.searchNameAndCompanyCustomer(customer, searchFor, search);
            if(res){
                listSeatch.add(customer);
                addLastCustomerTable(customer);
            }
        }
        updateTableCustomers();
    }
    
    /**
     * Actualiza un cliente
     */
    private void updateCustomer(){
        int row = jTableCustomers.getSelectedRow();
        if(row > -1){
            String cel = jTextFieldCel.getText();
            String city = jTextFieldCity.getText();
            String company = jTextFieldCompany.getText();
            String deptop = jTextFieldDepto.getText();
            String email = jTextFieldEmail.getText();
            String name = jTextFieldName.getText();
            String phone = jTextFieldPhone.getText();
            String state = jTextFieldState.getText();
            String street = jTextFieldStreet.getText();
            String zip = jTextFieldZip.getText();
            String colony = jTextFieldColony.getText();
            Customer customerNew = validateCustomerForm(name, company, deptop, email, phone, cel, state, street, colony, city, zip);
            if(customerNew != null){
                Customer customerOld = listCustomers.get(row);
                customerNew.setCustomerId(customerOld.getCustomerId());
                boolean res = logica.updateCustomer(customerNew);
                if(res){
                    listCustomers.remove(row);
                    listCustomers.add(row, customerNew);
                    addLastCustomerTable(customerNew, row);
                    updateTableCustomers();
                    JOptionPane.showMessageDialog(null, "Datos Actualizados", "Clientes", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(null, "Error Al Actualizar Los Datos", "Clientes", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
    
    
    private void updateItem(){
        int row = jTableItems.getSelectedRow();
        if(row > -1){
            String name = jTextFieldItemName.getText();
            String price = jTextFieldItemPrice.getText();
            Item itemNew = validateItemForm(name, price);
            if(itemNew != null){
                boolean res = logica.updateItem(itemNew);
                Item itemOld = listItems.get(row);
                itemNew.setItemId(itemOld.getItemId());
                if(res){
                    listItems.remove(row);
                    listItems.add(row, itemNew);
                    addLastItemTable(itemNew, row);
                    updateTableItems();
                    JOptionPane.showMessageDialog(null, "Datos Actualizados", "Items", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(null, "Error Al Actualizar Los Datos", "Items", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * actualiza los datos de la tabla cliente
     * cuando hay cambios
     */
    private void updateTableCustomers(){
        modeloTablaCustomer.fireTableStructureChanged();
    }
    
    private void updateTableItems(){
        modeloTablaItem.fireTableStructureChanged();
    }
    
    /**
     * Valida la informacion del formulario
     * de los clientes
     * @param name Nombre
     * @param company Compañia
     * @param deptop Departamento
     * @param email Email
     * @param phone Telefono
     * @param cel Celular
     * @param state Estado
     * @param street Calle 
     * @param city Ciudad
     * @param zip Codigo postal
     * @return Regresa un objeto Customer si tuvo exito o 
     * null en caso contrario
     */
    private Customer validateCustomerForm(String name, String company, String deptop, String email, String phone, String cel, String state, String street, String colony, String city, String zip) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
       Customer customer = new Customer();
       if(!name.isEmpty()){
           customer.setName(name);
           if(!company.isEmpty()){
               customer.setCompany(company);
               if(!deptop.isEmpty()){
                   customer.setDepartment(deptop);
                   if(!email.isEmpty()){
                       customer.setEmail(email);
                       if(!phone.isEmpty()){
                           long tmp = Utileria.converPhone(phone);
                           customer.setPhone(tmp);
                           if(!cel.isEmpty()){
                               tmp = Utileria.converPhone(cel);
                               customer.setCelPhone(tmp);
                               if(!state.isEmpty()){
                                   customer.setState(state);
                                   if(!street.isEmpty()){
                                       customer.setStreet(street);
                                       if(!city.isEmpty()){
                                           customer.setCity(city);
                                           if(!zip.isEmpty()){
                                               customer.setZip(zip);
                                               if(!colony.isEmpty()){
                                                   customer.setColony(colony);
                                                   return customer;
                                               }
                                               else{
                                                   JOptionPane.showMessageDialog(null, "El Campo Colonia No Puede Estar Vacio", "Clientes", JOptionPane.ERROR_MESSAGE);
                                                   return null;
                                               }
                                           }
                                           else{
                                               JOptionPane.showMessageDialog(null, "El Campo CP No Puede Estar Vacio", "Clientes", JOptionPane.ERROR_MESSAGE);
                                               return null;
                                           }
                                       }
                                       else{
                                           JOptionPane.showMessageDialog(null, "El Campo Ciudad No Puede Estar Vacio", "Clientes", JOptionPane.ERROR_MESSAGE);
                                           return null;
                                       }
                                   }
                                   else{
                                       JOptionPane.showMessageDialog(null, "El Campo Calle No Puede Estar Vacio", "Clientes", JOptionPane.ERROR_MESSAGE);
                                       return null;
                                   }
                               }
                               else{
                                   JOptionPane.showMessageDialog(null, "El Campo Estado No Puede Estar Vacio", "Clientes", JOptionPane.ERROR_MESSAGE);
                                   return null;
                               }
                           }
                           else{
                               JOptionPane.showMessageDialog(null, "El Campo Cel No Puede Estar Vacio", "Clientes", JOptionPane.ERROR_MESSAGE);
                               return null;
                           }
                       }
                       else{
                           JOptionPane.showMessageDialog(null, "El Campo Tel No Puede Estar Vacio", "Clientes", JOptionPane.ERROR_MESSAGE);
                           return null;
                       }
                   }
                   else{
                       JOptionPane.showMessageDialog(null, "El Campo Email No Puede Estar Vacio", "Clientes", JOptionPane.ERROR_MESSAGE);
                       return null;
                   }
               }
               else{
                   JOptionPane.showMessageDialog(null, "El Campo Deptop No Puede Estar Vacio", "Clientes", JOptionPane.ERROR_MESSAGE);
                   return null;
               }
           }
           else{
               JOptionPane.showMessageDialog(null, "El Campo Compañia No Puede Estar Vacio", "Clientes", JOptionPane.ERROR_MESSAGE);
               return null;
           }
       }
       else{
           JOptionPane.showMessageDialog(null, "El Campo Nombre No Puede Estar Vacio", "Clientes", JOptionPane.ERROR_MESSAGE);
           return null;
       }
       //</editor-fold>
        //</editor-fold>
        //</editor-fold>
    }
    
    private Item validateItemForm(String name, String price){
        Item item = new Item();
        if(!name.isEmpty()){
            item.setName(name);
            if(!price.isEmpty()){
                String tmp = Utileria.limpiaCadena(price);
                double prc = Utileria.convertDouble(tmp);
                item.setUnitPrice(prc);
                return item;
            }
            else{
                JOptionPane.showMessageDialog(null, "El Campo Precio No Puede Estar Vacio", "Items", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "El Campo Nombre No Puede Estar Vacio", "Items", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
    }
    
    private void validatePriceItem(KeyEvent evt){
        int tecla = evt.getKeyChar();
        //permite solo enteros y un punto
        if(!Character.isDigit(tecla) && tecla != '.')
            evt.consume();
        String temp = jTextFieldItemPrice.getText();
        if(tecla == '.' && temp.contains("."))
            evt.consume();   
    }
    
    /**
     * Valida que solo numeros sean tipeados
     * @param evt el evento del teclado 
     */
    private void validateTypeInteger(KeyEvent evt){
        int tecla = evt.getKeyChar();
        //permite solo enteros
        if(!Character.isDigit(tecla))
            evt.consume();   
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPaneIPrincipal = new javax.swing.JTabbedPane();
        jPanelCustomers = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCustomers = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxSearch = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldCompany = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jTextFieldDepto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldPhone = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldCel = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldCity = new javax.swing.JTextField();
        jTextFieldStreet = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldZip = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldState = new javax.swing.JTextField();
        jButtonNewCustomer = new javax.swing.JButton();
        jButtonDeleteCustomer = new javax.swing.JButton();
        jButtonUpdateCustomer = new javax.swing.JButton();
        jButtonSaveCustomer = new javax.swing.JButton();
        jTextFieldColony = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanelOrders = new javax.swing.JPanel();
        jPanelItems = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableItems = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldSearch1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jTextFieldItemId = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldItemName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldItemPrice = new javax.swing.JTextField();
        jButtonSaveItem = new javax.swing.JButton();
        jButtonDeleteItem = new javax.swing.JButton();
        jButtonUpdateItem = new javax.swing.JButton();
        jButtonNewItem = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ClonProto");

        jTabbedPaneIPrincipal.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N

        jTableCustomers.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableCustomers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nombre", "Compañia"
            }
        ));
        jTableCustomers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableCustomersMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableCustomers);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Buscar Por");

        jComboBoxSearch.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Telefono", "Compañia" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Texto");

        jTextFieldSearch.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12)
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(6, 6, 6)
                        .addComponent(jComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("Nombre:");

        jTextFieldName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jTextFieldCompany.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setText("Compañia:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setText("Email:");

        jTextFieldEmail.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jTextFieldDepto.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setText("Depto:");

        jTextFieldPhone.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPhoneKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel7.setText("Tel:");

        jTextFieldCel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldCel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCelKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setText("Cel:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel9.setText("Ciudad:");

        jTextFieldCity.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jTextFieldStreet.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setText("Calle:");

        jTextFieldZip.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldZip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldZipKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel11.setText("Cp:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel12.setText("Estado:");

        jTextFieldState.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jButtonNewCustomer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonNewCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/document_32.png"))); // NOI18N
        jButtonNewCustomer.setText("Nuevo");
        jButtonNewCustomer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonNewCustomer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonNewCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewCustomerActionPerformed(evt);
            }
        });

        jButtonDeleteCustomer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonDeleteCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_32.png"))); // NOI18N
        jButtonDeleteCustomer.setText("Eliminar");
        jButtonDeleteCustomer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonDeleteCustomer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonDeleteCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteCustomerActionPerformed(evt);
            }
        });

        jButtonUpdateCustomer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonUpdateCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        jButtonUpdateCustomer.setText("update");
        jButtonUpdateCustomer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonUpdateCustomer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonUpdateCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateCustomerActionPerformed(evt);
            }
        });

        jButtonSaveCustomer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonSaveCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save_32.png"))); // NOI18N
        jButtonSaveCustomer.setText("Guardar");
        jButtonSaveCustomer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSaveCustomer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonSaveCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveCustomerActionPerformed(evt);
            }
        });

        jTextFieldColony.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel13.setText("Colonia:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(jTextFieldCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel12)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldZip, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldCel, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDepto, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldStreet, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextFieldState, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                                .addComponent(jTextFieldCity, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldColony, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonNewCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButtonDeleteCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jButtonSaveCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jButtonUpdateCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldDepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldColony, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldZip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonNewCustomer)
                    .addComponent(jButtonDeleteCustomer)
                    .addComponent(jButtonUpdateCustomer)
                    .addComponent(jButtonSaveCustomer))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout jPanelCustomersLayout = new javax.swing.GroupLayout(jPanelCustomers);
        jPanelCustomers.setLayout(jPanelCustomersLayout);
        jPanelCustomersLayout.setHorizontalGroup(
            jPanelCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustomersLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelCustomersLayout.setVerticalGroup(
            jPanelCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPaneIPrincipal.addTab("Customers", jPanelCustomers);

        javax.swing.GroupLayout jPanelOrdersLayout = new javax.swing.GroupLayout(jPanelOrders);
        jPanelOrders.setLayout(jPanelOrdersLayout);
        jPanelOrdersLayout.setHorizontalGroup(
            jPanelOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1003, Short.MAX_VALUE)
        );
        jPanelOrdersLayout.setVerticalGroup(
            jPanelOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 621, Short.MAX_VALUE)
        );

        jTabbedPaneIPrincipal.addTab("Orders", jPanelOrders);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Items"));

        jTableItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio Unitario"
            }
        ));
        jTableItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableItemsMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTableItems);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Busqueda");

        jTextFieldSearch1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldSearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearch1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jTextFieldSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jLabel17)))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos ITem"));

        jTextFieldItemId.setEditable(false);
        jTextFieldItemId.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel14.setText("ID");

        jTextFieldItemName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel15.setText("Nombre:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel16.setText("Precio:");

        jTextFieldItemPrice.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldItemPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldItemPriceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldItemPriceFocusLost(evt);
            }
        });
        jTextFieldItemPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldItemPriceKeyTyped(evt);
            }
        });

        jButtonSaveItem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonSaveItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save_32.png"))); // NOI18N
        jButtonSaveItem.setText("Guardar");
        jButtonSaveItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSaveItem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonSaveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveItemActionPerformed(evt);
            }
        });

        jButtonDeleteItem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonDeleteItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_32.png"))); // NOI18N
        jButtonDeleteItem.setText("Eliminar");
        jButtonDeleteItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonDeleteItem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonDeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteItemActionPerformed(evt);
            }
        });

        jButtonUpdateItem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonUpdateItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        jButtonUpdateItem.setText("update");
        jButtonUpdateItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonUpdateItem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonUpdateItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateItemActionPerformed(evt);
            }
        });

        jButtonNewItem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonNewItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/document_32.png"))); // NOI18N
        jButtonNewItem.setText("Nuevo");
        jButtonNewItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonNewItem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonNewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel14))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldItemId, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldItemPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButtonNewItem, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jButtonDeleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jButtonSaveItem, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jButtonUpdateItem, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldItemId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextFieldItemName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextFieldItemPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(76, 76, 76)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonNewItem)
                    .addComponent(jButtonDeleteItem)
                    .addComponent(jButtonUpdateItem)
                    .addComponent(jButtonSaveItem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelItemsLayout = new javax.swing.GroupLayout(jPanelItems);
        jPanelItems.setLayout(jPanelItemsLayout);
        jPanelItemsLayout.setHorizontalGroup(
            jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelItemsLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelItemsLayout.setVerticalGroup(
            jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPaneIPrincipal.addTab("Items", jPanelItems);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/home_48.png"))); // NOI18N
        jMenu1.setText("HOME");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/group-icon.png"))); // NOI18N
        jMenuItem1.setText("Clientes");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Salir");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneIPrincipal)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneIPrincipal)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButtonNewCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewCustomerActionPerformed
        // TODO add your handling code here:
        this.clearCustomerForm();
        disableBtsDeleteAndUpdateCustomer();
        enableBtSaveCustomer();
    }//GEN-LAST:event_jButtonNewCustomerActionPerformed

    private void jButtonDeleteCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteCustomerActionPerformed
        // TODO add your handling code here:
        this.deleteCustomer();
    }//GEN-LAST:event_jButtonDeleteCustomerActionPerformed

    private void jButtonUpdateCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateCustomerActionPerformed
        // TODO add your handling code here:
        this.updateCustomer();
    }//GEN-LAST:event_jButtonUpdateCustomerActionPerformed

    private void jButtonSaveCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveCustomerActionPerformed
        // TODO add your handling code here:
        this.saveCustomer();
    }//GEN-LAST:event_jButtonSaveCustomerActionPerformed

    private void jTableCustomersMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCustomersMouseReleased
        // TODO add your handling code here:
        fillDataForm();
        enableBtsDeleteAndUpdateCustomer();
        disableBtSaveCustomer();
    }//GEN-LAST:event_jTableCustomersMouseReleased

    private void jTextFieldZipKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldZipKeyTyped
        // TODO add your handling code here:
        validateTypeInteger(evt);
    }//GEN-LAST:event_jTextFieldZipKeyTyped

    private void jTextFieldCelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCelKeyTyped
        // TODO add your handling code here:
        validateTypeInteger(evt);
    }//GEN-LAST:event_jTextFieldCelKeyTyped

    private void jTextFieldPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPhoneKeyTyped
        // TODO add your handling code here:
        validateTypeInteger(evt);
    }//GEN-LAST:event_jTextFieldPhoneKeyTyped

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextFieldSearchKeyReleased

    private void jButtonSaveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveItemActionPerformed
        // TODO add your handling code here:
        saveItem();
    }//GEN-LAST:event_jButtonSaveItemActionPerformed

    private void jButtonDeleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteItemActionPerformed
        // TODO add your handling code here:
        deleteItem();
    }//GEN-LAST:event_jButtonDeleteItemActionPerformed

    private void jButtonUpdateItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateItemActionPerformed
        // TODO add your handling code here:
        updateItem();
    }//GEN-LAST:event_jButtonUpdateItemActionPerformed

    private void jButtonNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewItemActionPerformed
        // TODO add your handling code here:
        clearItemForm();
        enableBtSaveItem();
        disableBtsDeleteAndUpdateItem();
    }//GEN-LAST:event_jButtonNewItemActionPerformed

    private void jTextFieldItemPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldItemPriceKeyTyped
        // TODO add your handling code here:
        validatePriceItem(evt);
    }//GEN-LAST:event_jTextFieldItemPriceKeyTyped

    private void jTextFieldSearch1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearch1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSearch1KeyReleased

    private void jTextFieldItemPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldItemPriceFocusLost
        // TODO add your handling code here:
        String tmp = jTextFieldItemPrice.getText();
        if(!tmp.isEmpty()){
            double price = Double.parseDouble(tmp);
            tmp = Utileria.formatMoneda(price);
            jTextFieldItemPrice.setText(tmp);
        }
    }//GEN-LAST:event_jTextFieldItemPriceFocusLost

    private void jTextFieldItemPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldItemPriceFocusGained
        // TODO add your handling code here:
        jTextFieldItemPrice.setText("");
    }//GEN-LAST:event_jTextFieldItemPriceFocusGained

    private void jTableItemsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableItemsMouseReleased
        // TODO add your handling code here:
        fillItemForm();
        disableBtSaveItem();
        enableBtsDeleteAndUpdateItem();
    }//GEN-LAST:event_jTableItemsMouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDeleteCustomer;
    private javax.swing.JButton jButtonDeleteItem;
    private javax.swing.JButton jButtonNewCustomer;
    private javax.swing.JButton jButtonNewItem;
    private javax.swing.JButton jButtonSaveCustomer;
    private javax.swing.JButton jButtonSaveItem;
    private javax.swing.JButton jButtonUpdateCustomer;
    private javax.swing.JButton jButtonUpdateItem;
    private javax.swing.JComboBox jComboBoxSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelCustomers;
    private javax.swing.JPanel jPanelItems;
    private javax.swing.JPanel jPanelOrders;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPaneIPrincipal;
    private javax.swing.JTable jTableCustomers;
    private javax.swing.JTable jTableItems;
    private javax.swing.JTextField jTextFieldCel;
    private javax.swing.JTextField jTextFieldCity;
    private javax.swing.JTextField jTextFieldColony;
    private javax.swing.JTextField jTextFieldCompany;
    private javax.swing.JTextField jTextFieldDepto;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldItemId;
    private javax.swing.JTextField jTextFieldItemName;
    private javax.swing.JTextField jTextFieldItemPrice;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTextField jTextFieldSearch1;
    private javax.swing.JTextField jTextFieldState;
    private javax.swing.JTextField jTextFieldStreet;
    private javax.swing.JTextField jTextFieldZip;
    // End of variables declaration//GEN-END:variables
    
    private LogicaNegocio logica;
    private ArrayList<Customer> listCustomers;
    private ArrayList<Item> listItems;
    private DefaultTableModel modeloTablaCustomer;
    private DefaultTableModel modeloTablaItem;
    private String[] registrosTablaCustomer;
    private String[] registrosTablaItem;

    
            
}
