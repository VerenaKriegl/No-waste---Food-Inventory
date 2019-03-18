/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prodottilatteria.model;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author hale
 */
public class ProdottiLatteriaModel {

    private final StringProperty scannedBCProperty = new SimpleStringProperty();
    private DBConn dbConn;
            
    public ProdottiLatteriaModel() {
         dbConn = new DBConn();
        //dbconn.DBDisconn();
        
    }
    
    public Product getProductFromDB() {
        
        Product product = dbConn.getProductFromBC(scannedBCProperty.get());
        return product;
        
    }
    
    public void insertProductInDB (Product product) {
        try {
            dbConn.updateProduct(product);
        } catch (SQLException ex) {
            Logger.getLogger(ProdottiLatteriaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setBC(String scannedBC) {
        this.scannedBCProperty.setValue(scannedBC);
    }
    
    public final StringProperty getScannedBCProperty() {
        return this.scannedBCProperty;
    }
    
}
