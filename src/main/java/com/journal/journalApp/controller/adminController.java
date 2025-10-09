package com.journal.journalApp.controller;

import com.journal.journalApp.entity.user;
import com.journal.journalApp.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private userService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<user> allUsers = userService.getAllUsers();
        if(allUsers!=null && !allUsers.isEmpty())
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> addAdminUser(@RequestBody user adminUser){
        try {
            userService.saveAdminUser(adminUser);
            return new ResponseEntity<>(adminUser, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
