package database;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyLoader {

    private static final String PROPFILENAME = System.getProperty("user.dir") + File.separator + "src" + File.separator + "Database" + File.separator + "db_props.properties";
    private static Properties props = new Properties();
    private static FileInputStream fis;

    static {
        try {
            fis = new FileInputStream(PROPFILENAME);
            props.load(fis);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String getProperty(String prop) {
        return props.getProperty(prop);
    }
}