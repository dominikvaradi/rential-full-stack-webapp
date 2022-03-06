package hu.dominikvaradi.rentialwebapp.payload.request;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserLoginRequest {
    @Size(min = 5, max = 40)
    @NotBlank
    private String username;

    @Column(name = "Password")
    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
