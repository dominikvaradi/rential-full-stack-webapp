package hu.dominikvaradi.rentialwebapp.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

// 3 * 24 * 60 * 60 * 1000 =

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    @NotNull
    private Long id;

    @Column(name = "Username", updatable = false)
    @Size(min = 5, max = 40)
    @NotNull
    @UniqueElements
    private String username;

    @Column(name = "Password")
    @NotBlank
    private String password;

    @Column(name = "Email")
    @Size(min = 10, max = 50)
    @NotNull
    @UniqueElements
    private String email;

    @Column(name = "Firstname")
    @Size(max = 30)
    private String firstname;

    @Column(name = "Lastname")
    @Size(max = 30)
    private String lastname;

    @OneToMany(mappedBy = "advertiserUser",
               cascade = { CascadeType.ALL }
    )
    private List<Advertisement> advertisements = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }
}
