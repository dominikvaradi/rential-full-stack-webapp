package hu.dominikvaradi.rentialwebapp.payload.response;

public class AdvertisementWithUsernameResponse {
    private Long id;

    private String name;

    private String description;

    private String username;

    public AdvertisementWithUsernameResponse() {

    }

    public AdvertisementWithUsernameResponse(Long id, String name, String description, String username) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
