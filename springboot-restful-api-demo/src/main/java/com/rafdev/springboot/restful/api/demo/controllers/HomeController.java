package com.rafdev.springboot.restful.api.demo.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class HomeController
{
    @GetMapping("/home")
    public String homeContent() {
        return "Welcome to SpringBoot RESTful API demo Example with PUBLIC ACCESS";
    }
/*
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public String userAccess() {
        return "Welcome to SpringBoot RESTful API demo Example with USER ACCESS!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public String adminAccess() {
        return "Welcome to SpringBoot RESTful API demo Example with ADMIN ACCESS!";
    }

    @GetMapping("/super-admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String superAdminAccess() {
        return "Welcome to SpringBoot RESTful API demo Example with SUPER ADMIN ACCESS!";
    }
 */
}
