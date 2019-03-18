package beans;

public class Reminder
{

    private int amount;
    private String productName, timeunit;

    public Reminder(int amount, String productName, String timeunit)
    {
        this.amount = amount;
        this.productName = productName;
        this.timeunit = timeunit;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getTimeunit()
    {
        return timeunit;
    }

    public void setTimeunit(String timeunit)
    {
        this.timeunit = timeunit;
    }

    @Override
    public String toString()
    {
        return "";
    }

}
