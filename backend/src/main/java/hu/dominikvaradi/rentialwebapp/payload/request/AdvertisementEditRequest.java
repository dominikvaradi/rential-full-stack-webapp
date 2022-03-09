package hu.dominikvaradi.rentialwebapp.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AdvertisementEditRequest {
    @NotNull
    private Long id;

    @Size(min = 5, max = 30, message = "Advertisement name character length must be between 5 and 30!")
    @NotBlank(message = "Advertisement name must contain characters!")
    private String name;

    private String description;

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
}
