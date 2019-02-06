package com.okan.poetica.controller;
import com.okan.poetica.model.Content;
import com.okan.poetica.model.User;
import com.okan.poetica.service.ContentService;
import com.okan.poetica.service.UserService;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ContentController {


    private ContentService contentService;
    private UserService userService;


    @Autowired
    public ContentController(ContentService contentService, UserService userService) {
        this.contentService = contentService;
        this.userService = userService;
    }


    private Map<String, String> errors;

    @RequestMapping(value = "/contents", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllContent(){

        return new ResponseEntity<>(contentService.getAllContent(), HttpStatus.OK);
    }

    @RequestMapping(value = "/content/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getContent(@PathVariable Long id){
        Content content = contentService.getContentById(id);
        if (content == null) {
            return new ResponseEntity<>("User is not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @RequestMapping(value = "/content", method = RequestMethod.POST)
    public ResponseEntity<Object> createContent(@RequestBody @Valid Content content, BindingResult bindingResult){

        User creator = userService.findByUsername(content.getWriter().getUsername());

        if (creator == null){
            return new ResponseEntity<>("Creator is not found",HttpStatus.NOT_FOUND);
        }
        if (bindingResult.hasErrors()) {
            errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        content.setWriter(creator);
        contentService.createContent(content);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/content/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        contentService.deleteContent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/content", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateUser(@RequestBody @Valid Content content, BindingResult bindingResult) {

        Content savedContent = contentService.getContentById(content.getId());
        //User user = userService.findByUsername(content.getWriter().getUsername());


        if (savedContent == null) {
            String message = "User is not updated. Because content is not exist!!";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (bindingResult.hasErrors()) {
            errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        } else {
            content.setId(savedContent.getId());
            contentService.updateContent(content);
            String message = "User is updated successfully";
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @RequestMapping( value = "/contentByCreator/{userID}", method = RequestMethod.GET)
    public ResponseEntity<Object> getContentByCreator(@PathVariable String userID){
        List<Content> contents = contentService.getContentByWriter(userService.findByID(userID));

        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

}
