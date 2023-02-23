package com.example.spring_deepening.repository;

import com.example.spring_deepening.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByComment_IdAndUser_Id(Long commentid, Long userid);

    void deleteByComment_IdAndUser_Id(Long commentid, Long userid);

    int countAllByComment_Id(Long commentid);
}
