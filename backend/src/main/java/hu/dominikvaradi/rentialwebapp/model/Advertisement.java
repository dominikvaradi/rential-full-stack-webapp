package hu.dominikvaradi.rentialwebapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Advertisements")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    @NotNull
    private Long id;

    @Column(name = "Name")
    @Size(min = 5, max = 30)
    private String name;

    @Column(name = "Description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_ID")
    @NotNull
    private User advertiserUser;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAdvertiserUser() {
        return advertiserUser;
    }

    public void setAdvertiserUser(User advertiserUser) {
        this.advertiserUser = advertiserUser;
    }
}
