package com.okan.poetica.service;

import com.okan.poetica.model.Content;
import com.okan.poetica.model.User;
import com.okan.poetica.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserService userService;

    public List<Content>getAllContent(){
        return contentRepository.findAll();
    }

    public Content getContentById(Long id){
        return contentRepository.findById(id).orElse(null);
    }

    public List<Content> getContentByWriter(User writer){

        return contentRepository.findByWriter(writer);
    }

    public void createContent(Content content){
        content.setLikeNumber(0);
        contentRepository.save(content);
    }

    public void updateContent(Content content){
        contentRepository.save(content);
    }

    public void deleteContent(Long id ){

        contentRepository.deleteById(id);
    }
}