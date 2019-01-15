package com.okan.poetica.service;

import com.okan.poetica.model.User;
import com.okan.poetica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(String id){
        return userRepository.findById(id).orElse(null);
    }

    public void createUser(User user){
        /*BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));*/
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

}
