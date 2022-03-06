package hu.dominikvaradi.rentialwebapp.controller;

import hu.dominikvaradi.rentialwebapp.model.Advertisement;
import hu.dominikvaradi.rentialwebapp.model.User;
import hu.dominikvaradi.rentialwebapp.payload.request.AdvertisementRequest;
import hu.dominikvaradi.rentialwebapp.payload.response.MessageResponse;
import hu.dominikvaradi.rentialwebapp.repository.AdvertisementRepository;
import hu.dominikvaradi.rentialwebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/advertisement")
public class AdvertisementController {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Advertisement>> getAll() {
        return ResponseEntity.ok(advertisementRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Advertisement> advertisement = advertisementRepository.findById(id);

        if (advertisement.isPresent()) {
            return ResponseEntity.ok(advertisement.get());
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Advertisement not found for specified id!"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createAdvertisement(@Valid @RequestBody AdvertisementRequest advertisementRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userWhoCreated = userRepository.findByUsername(userDetails.getUsername());

        if (!userWhoCreated.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("You are not logged in!"));
        }

        Advertisement newAdvertisement = new Advertisement(advertisementRequest.getName(), advertisementRequest.getDescription(), userWhoCreated.get());
        Advertisement created = advertisementRepository.save(newAdvertisement);
        String URIForNewAdvertisement = "/api/advertisement/" + created.getId();

        return ResponseEntity
                .created(URI.create(URIForNewAdvertisement))
                .body(new MessageResponse("Advertisement created successfully! Location for the new advertisement: " + URIForNewAdvertisement));
    }
}
