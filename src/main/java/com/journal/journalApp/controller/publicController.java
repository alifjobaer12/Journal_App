package com.journal.journalApp.controller;

import com.journal.journalApp.entity.user;
import com.journal.journalApp.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class publicController {

    @Autowired
    private userService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/create-user")
    public user createUser(@RequestBody user user){
        userService.saveNewUser(user);
        return user;
    }
}
