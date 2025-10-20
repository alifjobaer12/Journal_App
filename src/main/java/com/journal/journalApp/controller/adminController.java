package com.journal.journalApp.controller;

import com.journal.journalApp.entity.user;
import com.journal.journalApp.service.userService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private userService userService;


    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<user> allUsers = userService.getAllUsers();
        if(allUsers!=null && !allUsers.isEmpty()) {
            log.info("All users found");
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        else {
            log.info("No users found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> addAdminUser(@RequestBody user adminUser){
        try {
            userService.saveAdminUser(adminUser);
            log.info("Admin user added");
            return new ResponseEntity<>(adminUser, HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("Error adding Admin User");
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
