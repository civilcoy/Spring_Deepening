package com.example.spring_deepening.repository;

import com.example.spring_deepening.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllPostByOrderByModifiedAtDesc();
    Optional<Post> findById(Long id);
    Boolean existsByIdAndUsername(Long id, String username);
}
