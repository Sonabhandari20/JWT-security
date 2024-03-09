package com.youtube.jwt.controller;

import com.youtube.jwt.entity.User;
import com.youtube.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
@PostConstruct
    public void  initRolesAndUsers(){
        userService.initRoleAndUser();
    }
    @PostMapping("/registerNewUser")
    public ResponseEntity<String> registerNewUser(@RequestBody User user) {
        User registeredUser = userService.registerNewUser(user);
        String successMessage = "User registration successful for " + registeredUser.getUserName();

        // You can return a JSON response with a success message
        return ResponseEntity.ok("{\"message\": \"" + successMessage + "\"}");
    }


    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<String> forAdmin() {
        String responseMessage = "This URL is only accessible to the admin";
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('ROLE_User')")
    public ResponseEntity<String> forUser() {
        String responseMessage = "This URL is only accessible to the user";
        return ResponseEntity.ok(responseMessage);
    }
}
