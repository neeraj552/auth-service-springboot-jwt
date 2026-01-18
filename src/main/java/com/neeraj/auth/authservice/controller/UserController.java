package com.neeraj.auth.authservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('USER')")
    public String userDashboard(){
        return "Welcome USER! This is your dashboard.";
    }

}
