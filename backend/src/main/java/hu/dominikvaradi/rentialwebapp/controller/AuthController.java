package hu.dominikvaradi.rentialwebapp.controller;

import hu.dominikvaradi.rentialwebapp.model.Role;
import hu.dominikvaradi.rentialwebapp.model.User;
import hu.dominikvaradi.rentialwebapp.payload.request.UserLoginRequest;
import hu.dominikvaradi.rentialwebapp.payload.request.UserRegisterRequest;
import hu.dominikvaradi.rentialwebapp.payload.response.JwtLoginResponse;
import hu.dominikvaradi.rentialwebapp.payload.response.MessageResponse;
import hu.dominikvaradi.rentialwebapp.repository.RoleRepository;
import hu.dominikvaradi.rentialwebapp.repository.UserRepository;
import hu.dominikvaradi.rentialwebapp.security.jwt.JwtUtils;
import hu.dominikvaradi.rentialwebapp.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequest.getUsername(),
                        userLoginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtTokenFromUsername(userLoginRequest.getUsername());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtLoginResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRepository.existsByUsername(userRegisterRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(userRegisterRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User newUser = new User(userRegisterRequest.getUsername(),
                             passwordEncoder.encode(userRegisterRequest.getPassword()),
                             userRegisterRequest.getEmail(),
                             userRegisterRequest.getFirstname(),
                             userRegisterRequest.getLastname());

        newUser.getRoles().add(roleRepository.findByName("ROLE_USER").orElse(new Role("ROLE_USER")));
        userRepository.save(newUser);

        return ResponseEntity.ok().body(new MessageResponse("User registered successfully!"));
    }


}
