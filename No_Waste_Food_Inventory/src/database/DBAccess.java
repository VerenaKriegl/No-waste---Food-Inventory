package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import beans.Product;
import beans.User;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBAccess {

    private Database database;
    private ArrayList<Product> products = new ArrayList();
    private DateFormat dateFormat;

    public DBAccess() throws ClassNotFoundException, SQLException {
        database = Database.getInstance();
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    }

    public DBAccess(String url, String user, String passwd, String driver) throws ClassNotFoundException, SQLException {
        database = Database.getInstance(url, user, passwd, driver);
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    }

    public void insertProduct(Product product, String username) throws Exception {

        String sqlString = "INSERT INTO producttable"
                + "(productnr, description, expiredate, username, category) "
                + "VALUES ('" + product.getProductNr()
                + "','" + product.getProductName()
                + "', '" + product.getExpirationDate()
                + "', '" + username
                + "','" + product.getCategory()
                + "');";

        Statement statement = database.getStatement();
        statement.execute(sqlString);

        statement.close();
    }

    public void insertUser(User user) {
        try {
            String sqlString = "INSERT INTO usertable"
                    + "(username, password, dateofbirth) "
                    + "VALUES ('" + user.getUserName()
                    + "','" + user.getPassword()
                    + "', '" + user.getDateOfBirth()
                    + "');";

            Statement statement = database.getStatement();
            statement.execute(sqlString);
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteEntries(String username) throws Exception {
        String sqlString = "DELETE FROM producttable WHERE username = '" + username + "' ;";

            Statement statement = database.getStatement();
            statement.execute(sqlString);
            statement.close();
    }
    
    public static void main(String[] args) {
        DBAccess db;
        try {
            db = new DBAccess();
            db.deleteEntries("");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getPassword(String userName) throws Exception {
        Statement statement = database.getStatement();
        String sqlQueryPassword = "SELECT * FROM usertable WHERE username = '" + userName + "';";
        System.out.println("");
        ResultSet resultSet = statement.executeQuery(sqlQueryPassword);
        String password = "";
        while (resultSet.next()) {
            password = resultSet.getString("password");
        }
        statement.close();
        return password;
    }

    public ArrayList<String> showUser() throws Exception {
        Statement statement = database.getStatement();
        String sqlQueryProduct = "SELECT * FROM usertable";

        ResultSet resultSet = statement.executeQuery(sqlQueryProduct);
        ArrayList<String> user = new ArrayList<>();
        while (resultSet.next()) {
            String username = resultSet.getString("username");
            user.add(username);
        }
        statement.close();
        return user;
    }

    public ArrayList<Product> showAllProducts(String userName) throws Exception {
        Statement statement = database.getStatement();
        String sqlQueryProduct = "SELECT * FROM producttable WHERE username = '" + userName + "' ;";

        ResultSet resultSet = statement.executeQuery(sqlQueryProduct);

        while (resultSet.next()) {
            int productNr = resultSet.getInt("productNr");
            String productName = resultSet.getString("description");
            Date expDate = resultSet.getDate("expireDate");
            String category = resultSet.getString("category");

            Product product = new Product(productName, expDate, category, productNr, false);
            products.add(product);
        }
        statement.close();
        return products;
    }
}
