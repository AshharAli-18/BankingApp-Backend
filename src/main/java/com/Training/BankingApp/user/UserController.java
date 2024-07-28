package com.Training.BankingApp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/auth/loginCustomer")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequest loginRequest) {
        try{
            System.out.println("LOGIN CUSTOMER API CALEDDDDDDD");
            ResponseEntity<?> response = userService.loginCustomer(loginRequest);
                return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/api/auth/loginAdmin")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequest loginRequest) {
        try {
            ResponseEntity<?> response = userService.loginAdmin(loginRequest);

            // Return the response directly without modifying it
            return response;
        } catch (Exception e) {
            // Handle exceptions and return a bad request response with the exception message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/getUsers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/api/getUserByUserId/{userId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public User getUserByUserId(@PathVariable("userId") long userId) {
        return userService.getUserByUserId(userId);
    }
}
