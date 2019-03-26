package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private Connection connection;
    private static Database instance;
    private String DB_URL;
    private String DB_USER;
    private String DB_PASS;
    private String DB_DRIVER;
    private static PropertyLoader propertyLoader;

    private Database() throws SQLException, ClassNotFoundException {
        DB_URL = propertyLoader.getProperty("DB_url");
        DB_USER = propertyLoader.getProperty("DB_user");
        DB_PASS = propertyLoader.getProperty("DB_password");
        DB_DRIVER = propertyLoader.getProperty("DB_driver");;
        System.out.println("gag");
        Class.forName(DB_DRIVER);
        connect();
    }

    public static Database getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public static Database getInstance(String url, String user, String passwd, String driver) throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new Database(url, user, passwd, driver);
        }
        return instance;
    }

    private Database(String url, String user, String passwd, String driver) throws ClassNotFoundException, SQLException {
        DB_URL = url;
        DB_USER = user;
        DB_PASS = passwd;
        DB_DRIVER = driver;

        Class.forName(driver);
        connect();
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public Statement getStatement() throws Exception {
        if (connection != null) {
            return connection.createStatement();
        }

        throw new Exception("no database connection available");
    }

    public PreparedStatement prepareStatement(String query) {
        try {
            PreparedStatement pst = connection.prepareStatement(query);

            return pst;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void close() throws SQLException {
        connection.close();
        instance = null;
    }
    

    public void createTableProduct() throws SQLException {
        Statement statement = connection.createStatement();
        String sqlProduct = "CREATE TABLE productTable ("
                + "productNr SERIAL PRIMARY KEY, "
                + "description VARCHAR(50) NOT NULL, "
                + "expireDate DATE NOT NULL, "
                + "userName VARCHAR(40) NOT NULL, "
                + "category VARCHAR(50) NOT NULL);";
        String sqlProductForeignKey = "ALTER TABLE productTable ADD CONSTRAINT Product_user FOREIGN KEY(userName) REFERENCES userTable(userName); ";
        statement.execute(sqlProduct);
        statement.execute(sqlProductForeignKey);
    }

    public void createTableUser() {
        try {
            Statement statement = connection.createStatement();
            String sqlString = "CREATE TABLE userTable ("
                    + "userName VARCHAR(50) PRIMARY KEY, "
                    + "password VARCHAR(50) NOT NULL, "
                    + "dateOfBirth DATE NOT NULL);";
            statement.execute(sqlString);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
