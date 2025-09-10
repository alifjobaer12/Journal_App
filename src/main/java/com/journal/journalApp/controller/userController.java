package com.journal.journalApp.controller;

import com.journal.journalApp.entity.user;
import com.journal.journalApp.service.userService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private userService userService;

    @GetMapping
    public List<user> getAllUser() {
        return userService.getAll();
    }
    @PostMapping
    public user createUser(@RequestBody user user){
        userService.saveEntry(user);
        return user;
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUsr(@RequestBody user user, @PathVariable String userName){
        user userInDB = userService.findByUserName(userName);
        if(userInDB!=null){
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            userService.saveEntry(userInDB);
        }
        return new ResponseEntity<>(userInDB, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id){
        Optional<user> findId = userService.findById(id);
        if(findId.isPresent()) {
            userService.deleteById(id);
            return new ResponseEntity<>(findId.get(),HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
