package beans;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private String userName, password;
    private Date dateOfBirth;

    public User(String userName, String password, Date dateOfBirth) {
        this.userName = userName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }   

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}
