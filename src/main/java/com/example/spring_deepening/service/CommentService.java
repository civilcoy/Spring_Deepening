package com.example.spring_deepening.service;

import com.example.spring_deepening.dto.CommentRequestDto;
import com.example.spring_deepening.dto.CommentResponseDto;
import com.example.spring_deepening.entity.Comment;
import com.example.spring_deepening.entity.Post;
import com.example.spring_deepening.entity.User;
import com.example.spring_deepening.entity.UserRoleEnum;
import com.example.spring_deepening.repository.CommentRepository;
import com.example.spring_deepening.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    @Transactional // 댓글 작성
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.save(new Comment(commentRequestDto, post, user));
        return new CommentResponseDto(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long postId, Long cmtId, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment;

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(cmtId).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );
        }else {
            comment = commentRepository.findByIdAndUserId(cmtId, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );
        }
        comment.update(commentRequestDto);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public CommentResponseDto deleteComment(Long postId, Long cmtId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment;

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(cmtId).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );
        } else {
            comment = commentRepository.findByIdAndUserId(cmtId, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );
        }
        commentRepository.deleteById(cmtId);
        return new CommentResponseDto(comment);
    }
}
