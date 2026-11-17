
package com.klef.soa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.soa.entity.User;
import com.klef.soa.service.UserService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/user")
@CrossOrigin("*")

public class UserController
{
    @Autowired
    private UserService service;

    @GetMapping("/")
    public String home()
    {
        return "User Registration Project";
    }

    @GetMapping("/test")
    public String test()
    {
        return "Test API Call";
    }

    @PostMapping("/add")
    public ResponseEntity<User> saveUser(
            @Valid @RequestBody User user)
    {
        return new ResponseEntity<>(
                service.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/displayall")
    public ResponseEntity<List<User>> getAllUsers()
    {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/display/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long id)
    {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User user)
    {
        return ResponseEntity.ok(
                service.updateUser(id, user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id)
    {
        service.deleteUser(id);

        return ResponseEntity.ok(
                "User deleted successfully.");
    }
}
