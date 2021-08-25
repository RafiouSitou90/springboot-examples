package com.rafdev.springboot.restful.api.demo.controllers;

import com.rafdev.springboot.restful.api.demo.payload.request.JwtLoginRequest;
import com.rafdev.springboot.restful.api.demo.payload.response.JwtResponse;
import com.rafdev.springboot.restful.api.demo.services.impls.UserServiceImpl;
import com.rafdev.springboot.restful.api.demo.utility.JwtUtility;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class JwtAuthController
{
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final JwtUtility jwtUtility;

    public JwtAuthController(AuthenticationManager authenticationManager, UserServiceImpl userService,
                             JwtUtility jwtUtility) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody JwtLoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                    loginRequest.getPassword()));
        } catch (BadCredentialsException exception) {
            throw  new Exception("INVALID_CREDENTIALS", exception);
        }

        final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        final String token = jwtUtility.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
