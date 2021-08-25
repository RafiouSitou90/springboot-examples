package com.rafdev.springboot.restful.api.demo.controllers;

import com.rafdev.springboot.restful.api.demo.entities.ERole;
import com.rafdev.springboot.restful.api.demo.entities.Role;
import com.rafdev.springboot.restful.api.demo.entities.User;
import com.rafdev.springboot.restful.api.demo.payload.request.JwtLoginRequest;
import com.rafdev.springboot.restful.api.demo.payload.request.JwtSignUpRequest;
import com.rafdev.springboot.restful.api.demo.payload.response.JwtResponse;
import com.rafdev.springboot.restful.api.demo.repositries.RoleRepository;
import com.rafdev.springboot.restful.api.demo.repositries.UserRepository;
import com.rafdev.springboot.restful.api.demo.services.impls.UserDetailsImpl;
import com.rafdev.springboot.restful.api.demo.services.impls.UserServiceImpl;
import com.rafdev.springboot.restful.api.demo.utility.JwtUtility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class JwtAuthController
{
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserServiceImpl userService;
    private final PasswordEncoder encoder;
    private final JwtUtility jwtUtility;

    public JwtAuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                             RoleRepository roleRepository, UserServiceImpl userService, PasswordEncoder encoder,
                             JwtUtility jwtUtility) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.encoder = encoder;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody JwtLoginRequest loginRequest) throws Exception {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                    loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException exception) {
            throw  new Exception("INVALID_CREDENTIALS", exception);
        }

        final UserDetailsImpl userDetails =
                (UserDetailsImpl) userService.loadUserByUsername(loginRequest.getUsername());
        final String accessToken = jwtUtility.generateToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(accessToken, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(),
                roles);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody JwtSignUpRequest jwtSignUpRequest) {
        if (userRepository.existsByUsername(jwtSignUpRequest.getUsername())) {
            return new ResponseEntity<>("Error: Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(jwtSignUpRequest.getEmail())) {
            return new ResponseEntity<>("Error: Email is already in use!", HttpStatus.BAD_REQUEST);
        }

        // Create new user's account
        User user = new User(jwtSignUpRequest.getUsername(),
                jwtSignUpRequest.getEmail(),
                encoder.encode(jwtSignUpRequest.getPassword()));

        Set<String> strRoles = jwtSignUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "super_admin" -> {
                        Role superAdminRole = roleRepository.findByName(ERole.ROLE_SUPER_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(superAdminRole);
                    }
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
}
