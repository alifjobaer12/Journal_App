package com.journal.journalApp.controller;

import com.journal.journalApp.entity.user;
import com.journal.journalApp.service.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class publicController {

    @Autowired
    private userService userService;

    private static final Logger log = LoggerFactory.getLogger(publicController.class);

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody user user){
        userService.saveNewUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
