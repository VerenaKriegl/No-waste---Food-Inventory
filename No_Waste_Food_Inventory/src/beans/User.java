package beans;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable{
    private String userName, password;
    private LocalDate dateOfBirth;

    public User(String userName, String password, LocalDate dateOfBirth) {
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
