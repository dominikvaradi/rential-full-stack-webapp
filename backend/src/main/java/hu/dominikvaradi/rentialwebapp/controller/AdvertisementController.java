package hu.dominikvaradi.rentialwebapp.controller;

import hu.dominikvaradi.rentialwebapp.model.Advertisement;
import hu.dominikvaradi.rentialwebapp.model.User;
import hu.dominikvaradi.rentialwebapp.payload.request.AdvertisementEditRequest;
import hu.dominikvaradi.rentialwebapp.payload.request.AdvertisementRequest;
import hu.dominikvaradi.rentialwebapp.payload.response.AdvertisementWithUsernameResponse;
import hu.dominikvaradi.rentialwebapp.payload.response.MessageResponse;
import hu.dominikvaradi.rentialwebapp.repository.AdvertisementRepository;
import hu.dominikvaradi.rentialwebapp.repository.UserRepository;
import hu.dominikvaradi.rentialwebapp.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/advertisement")
public class AdvertisementController {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @Transactional
    public ResponseEntity<List<AdvertisementWithUsernameResponse>> getAll() {
        return ResponseEntity.ok(
            advertisementRepository.findAll()
            .stream()
            .map(advertisement -> {
                return new AdvertisementWithUsernameResponse(
                    advertisement.getId(),
                    advertisement.getName(),
                    advertisement.getDescription(),
                    advertisement.getUser().getUsername()
                );
            })
            .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Advertisement> advertisement = advertisementRepository.findById(id);

        if (advertisement.isPresent()) {
            Advertisement adv = advertisement.get();
            return ResponseEntity.ok(new AdvertisementWithUsernameResponse(adv.getId(), adv.getName(), adv.getDescription(), adv.getUser().getUsername()));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Advertisement not found for specified id!"));
        }
    }

    @PostMapping
    @Transactional
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

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteAdvertisement(@PathVariable Long id) {
        Optional<Advertisement> advertisement = advertisementRepository.findById(id);

        if (advertisement.isPresent()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || advertisement.get().getUser().getId() == userDetails.getId()) {
                advertisementRepository.deleteAdvertisementById(id);

                return ResponseEntity.ok().body(new MessageResponse("Advertisement (id = " + id.toString() + ") deleted successfully!"));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> editAdvertisement(@PathVariable Long id, @Valid @RequestBody AdvertisementEditRequest advertisementEditRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userWhoCreated = userRepository.findByUsername(userDetails.getUsername());

        if (!userWhoCreated.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("You are not logged in!"));
        }

        if (advertisementEditRequest.getId() != id) {
            return ResponseEntity.badRequest().body(new MessageResponse("Path ID is not equal to the request object id!"));
        }

        Optional<Advertisement> advertisementToEdit = advertisementRepository.findById(id);
        if(!advertisementToEdit.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Advertisement advertisement = advertisementToEdit.get();

        if(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || advertisement.getUser().getId() == userDetails.getId()) {
            advertisement.setName(advertisementEditRequest.getName());
            advertisement.setDescription(advertisementEditRequest.getDescription());
            advertisementRepository.save(advertisement);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
