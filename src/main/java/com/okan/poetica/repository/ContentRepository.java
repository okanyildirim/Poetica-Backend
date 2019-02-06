package com.okan.poetica.repository;

import com.okan.poetica.model.Content;
import com.okan.poetica.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content,Long> {

    List<Content> findByWriter(User writer);
}
