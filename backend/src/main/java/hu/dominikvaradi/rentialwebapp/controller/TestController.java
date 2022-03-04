package hu.dominikvaradi.rentialwebapp.controller;

import hu.dominikvaradi.rentialwebapp.model.User;
import hu.dominikvaradi.rentialwebapp.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/test")
@RestController
public class TestController {
    private final UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @GetMapping()
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public void insertNewUser(@RequestBody User user) {
        userRepository.save(user);
    }
}
