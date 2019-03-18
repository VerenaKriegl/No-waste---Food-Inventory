package beans;

import java.time.LocalDate;

public class Product
{

    private String productName;
    private LocalDate expirationDate;
    private String category;
    private int productNr;
    private boolean reminder;

    //kommentar m
    public Product(String productName, LocalDate expirationDate, String category, int productNr, boolean reminder)
    {
        this.productName = productName;
        this.expirationDate = expirationDate;
        this.category = category;
        this.productNr = productNr;
        this.reminder = reminder;
    }

    public String getProductName()
    {
        return productName;
    }

    public LocalDate getExpirationDate()
    {
        return expirationDate;
    }

    public String getCategory()
    {
        return category;
    }

    public int getProductNr()
    {
        return productNr;
    }

    public int getDaysTillExpiration()
    {
        return expirationDate.getDayOfYear() - LocalDate.now().getDayOfYear();
    }

    public boolean isReminder()
    {
        return reminder;
    }

    public void setReminder(boolean reminder)
    {
        this.reminder = reminder;
    }

    @Override
    public String toString()
    {
        if (reminder)
        {
            return "      R!";
        } else
        {
            return "";
        }
    }

}
