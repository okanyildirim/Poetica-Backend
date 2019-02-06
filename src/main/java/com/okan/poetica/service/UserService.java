package com.okan.poetica.service;

import com.okan.poetica.model.Content;
import com.okan.poetica.model.User;
import com.okan.poetica.repository.ContentRepository;
import com.okan.poetica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {


    private UserRepository userRepository;

    private ContentRepository contentRespository;


    @Autowired
    public UserService(ContentRepository contentRespository, UserRepository userRepository) {
        this.contentRespository = contentRespository;
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(String id){
        return userRepository.findById(id).orElse(null);
    }

    public void createUser(User user){

        UUID uuid = UUID.randomUUID();
        user.setId(uuid.toString());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        user.setCurrentPageContentList(contentRespository.findAll());
        user.setRole("USER");
        userRepository.save(user);
    }

    public void createAdmin(User user){
        UUID uuid = UUID.randomUUID();
        user.setId(uuid.toString());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        user.setRole("ADMIN");

        userRepository.save(user);
    }

    public void deleteUser(String id){
        userRepository.delete(userRepository.findById(id).orElse(null));
    }

    public List<User> findByName(String name) {
        return  userRepository.findByNameLike("%"+name+"%");
    }

    public User findByEmail(String email){

        return userRepository.findByEmail(email);
    }

    public void updateUser(User user){
        //userRepository.deleteById(email);
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByID(String id){

        return userRepository.findById(id).orElse(null);
    }
}
