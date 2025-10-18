package com.journal.journalApp.controller;

import com.journal.journalApp.entity.user;
import com.journal.journalApp.service.userService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private userService userService;


    @PutMapping
    public ResponseEntity<?> updateUsr(@RequestBody user user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        user userInDB = userService.findByUserName(userName);
        userInDB.setPassword(user.getPassword());
        userService.saveNewUser(userInDB);
        log.info("user updated");
        return new ResponseEntity<>(userInDB, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUserName(authentication.getName());
        log.info("user deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
