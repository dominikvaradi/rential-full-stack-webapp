package hu.dominikvaradi.rentialwebapp.controller;

import hu.dominikvaradi.rentialwebapp.model.Advertisement;
import hu.dominikvaradi.rentialwebapp.repository.AdvertisementRepository;
import hu.dominikvaradi.rentialwebapp.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    @GetMapping("/{id}/advertisements")
    @Transactional
    public ResponseEntity<List<Advertisement>> getAdvertisementsByUser(@PathVariable Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) && id != userDetails.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Advertisement> advertisements = advertisementRepository.findAllByUser_Id(id);

        return ResponseEntity.ok(advertisements);
    }
}
