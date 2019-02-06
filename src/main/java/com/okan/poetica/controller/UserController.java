package com.okan.poetica.controller;


import com.okan.poetica.model.User;
import com.okan.poetica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    Map<String, String> errors;

    @RequestMapping(value = "users", method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
        }

        User u = userService.findByEmail(user.getEmail());
        User u2 = userService.findByUsername(user.getUsername());

        if (u != null) {
            return new ResponseEntity<>("This user is already exist!!", HttpStatus.CONFLICT);
        }

        else if (u2!=null){
            return new ResponseEntity<>("This username is already taken!!", HttpStatus.CONFLICT);
        }

        userService.createUser(user);
        String message = "User is created successfully";
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "createAdmin")
    public ResponseEntity<Object> createAdmin(@RequestBody @Valid User admin, BindingResult bindingResult) {

        User u = userService.findByEmail(admin.getEmail());

        if (u != null) {
            return new ResponseEntity<>("This user is already exist!!", HttpStatus.CONFLICT);
        }

        userService.createAdmin(admin);
        String message = "User is created successfully";
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllUsers() {

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUser(@PathVariable String id) {

        if (userService.getUser(id) == null) {
            return new ResponseEntity<>("User is not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/userbylikename/{name}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUsersByName(@PathVariable String name) {

        List<User> userList = userService.findByName(name);
        if (userList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @RequestMapping(value = "/userbyemail/{email}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUsersByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    @RequestMapping(value = "/userbyusername/{username}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateUser(@RequestBody @Valid User user, BindingResult bindingResult) {

        User savedUsed = userService.findByEmail(user.getEmail());

        if (savedUsed == null) {
            String message = "User is not updated. Because user is not exist!!";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (bindingResult.hasErrors()) {
            errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
        } else {
            user.setId(savedUsed.getId());
            userService.updateUser(user);
            String message = "User is updated successfully";
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody User user){


        User foundUser = userService.findByEmail(user.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (user.getEmail()==null ){

            return new ResponseEntity<>("Lütfen mailinizi giriniz",HttpStatus.NO_CONTENT);
        }

        if (user.getPassword()==null ){

            return new ResponseEntity<>("Lütfen parolanızı giriniz",HttpStatus.NO_CONTENT);
        }

        if (foundUser==null){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        else if (encoder.matches(user.getPassword(), foundUser.getPassword()) ){

        return new ResponseEntity<>(foundUser, HttpStatus.OK);

        }

        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}