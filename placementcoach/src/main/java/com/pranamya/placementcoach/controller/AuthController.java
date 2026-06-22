package com.pranamya.placementcoach.controller;

import com.pranamya.placementcoach.model.User;
import com.pranamya.placementcoach.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.pranamya.placementcoach.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        return userService.saveUser(user);
    }


    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {

        boolean isValid = userService.loginUser(
                user.getEmail(),
                user.getPassword()
        );

        if (isValid) {
            return jwtUtil.generateToken(
                    user.getEmail()
            );
        }

        return "Invalid Email or Password";
    }
    @GetMapping("/test")
    public String testToken(@RequestParam String token) {
        return jwtUtil.extractEmail(token);
    }
}