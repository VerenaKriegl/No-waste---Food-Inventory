package beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

public class Product implements Serializable {

    private String productName;
    private Date expirationDate;                           
    private String category;
    private int productNr;
    private boolean reminder;
    private int daysTillExpiration;

    public Product(String productName, Date expirationDate, String category, int productNr, boolean reminder) {
        this.productName = productName;
        this.expirationDate = expirationDate;
        this.category = category;
        this.productNr = productNr;
        this.reminder = reminder;
    }

    public String getProductName() {
        return productName;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getCategory() {
        return category;
    }

    public int getProductNr() {
        return productNr;
    }

    public int getDaysTillExpiration() {
        java.util.Date utilDate = new java.util.Date();
        Date sysdate = new Date(utilDate.getTime());
        
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(sysdate);
        
        Calendar calendarExpiration = Calendar.getInstance();
        calendarExpiration.setTime(expirationDate);
        
        daysTillExpiration = calendarExpiration.get(Calendar.DAY_OF_YEAR) 
                    - calendarNow.get(Calendar.DAY_OF_YEAR);
        
        return daysTillExpiration;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        if (reminder) {
            return "      R!";
        } else {
            return "";
        }
    }
}
