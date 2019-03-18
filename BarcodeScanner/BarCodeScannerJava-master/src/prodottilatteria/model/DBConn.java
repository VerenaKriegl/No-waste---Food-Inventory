/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prodottilatteria.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

/**
 *
 * @author hale
 */
public class DBConn {

    static final String DB_URL
            = "jdbc:mysql://192.168.1.33:3306/db_latteria";
    static final String DB_DRV
            = "com.mysql.jdbc.Driver";
    static final String DB_USER = "admin";
    static final String DB_PASSWD = "Rainbow6";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public DBConn() {

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    public Product getProductFromBC(String BC) {

        Product product = null;
        DecimalFormat f = new DecimalFormat("##.00");

        try {
            resultSet = statement.executeQuery("SELECT * FROM prodotti WHERE codice_a_barre=" + BC);
            while (resultSet.next()) {
                product = new Product(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        f.format(resultSet.getFloat(4)),
                        f.format(resultSet.getFloat(5)),
                        resultSet.getString(6));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {

            try {
                resultSet.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return product;
    }

    public void updateProduct(Product product) throws SQLException {

        PreparedStatement pstatement;

        pstatement = connection.prepareStatement("INSERT INTO prodotti(id, codice_a_barre, nome, prezzo_acquisto, prezzo_vendita, marca)"
                + " VALUES (?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "id=VALUES(id), "
                + "codice_a_barre=VALUES(codice_a_barre), "
                + "nome=VALUES(nome), "
                + "prezzo_acquisto=VALUES(prezzo_acquisto), "
                + "prezzo_vendita=VALUES(prezzo_vendita), "
                + "marca=VALUES(marca)");

        pstatement.setInt(1, product.getid());
        pstatement.setString(2, product.getbc());
        pstatement.setString(3, product.getnome());
        pstatement.setBigDecimal(4, new BigDecimal(product.getprezzoa().replace(',', '.')));
        pstatement.setBigDecimal(5, new BigDecimal(product.getprezzov().replace(',', '.')));
        pstatement.setString(6, product.getmarca());

        pstatement.executeUpdate();

    }

    public void DBDisconn() {

        try {
            statement.close();
            connection.close();
        } catch (SQLException ex) {
        }
    }

}
