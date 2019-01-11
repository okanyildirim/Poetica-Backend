package com.okan.poetica.repository;

import com.okan.poetica.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findByEmail(String email);

    List<User> findByNameLike(String nameLike);

    User findById(Long id);
}
