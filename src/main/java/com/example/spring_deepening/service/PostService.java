package com.example.spring_deepening.service;

import com.example.spring_deepening.dto.CommentResponseDto;
import com.example.spring_deepening.dto.PostRequestDto;
import com.example.spring_deepening.dto.PostResponseDto;
import com.example.spring_deepening.dto.ResponseDto;
import com.example.spring_deepening.entity.Comment;
import com.example.spring_deepening.entity.Post;
import com.example.spring_deepening.entity.User;
import com.example.spring_deepening.entity.UserRoleEnum;
import com.example.spring_deepening.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    // 게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto, user.getUsername());
        post = postRepository.save(post);
        post.setUser(user);
        return new PostResponseDto(post);
    }

    // 전체 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        List<Post> postList = postRepository.findAllPostByOrderByModifiedAtDesc();
        List<PostResponseDto> postResponseDto = new ArrayList<>();

        for (Post post : postList) {
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : post.getComments()) {
                commentList.add(new CommentResponseDto(comment));
            }
            postResponseDto.add(new PostResponseDto(post, commentList));
        }
        return postResponseDto;
    }

    // 선택 게시글 조회
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : post.getComments()) {
            commentList.add(new CommentResponseDto(comment));
        }
        return new PostResponseDto(post, commentList);
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        UserRoleEnum userRoleEnum = user.getRole();

        if (userRoleEnum == UserRoleEnum.ADMIN) {
            Post post = postRepository.findById(id).get();
            post.update(requestDto, user.getUsername());
            return new PostResponseDto(post);
        } else if (postRepository.existsByIdAndUsername(id, user.getUsername()) && userRoleEnum == UserRoleEnum.USER) {
            Post post = postRepository.findById(id).get();
            post.update(requestDto, user.getUsername());
            return new PostResponseDto(post);
        } else {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
    }

    // 게시글 삭제
    @Transactional
    public ResponseDto deletePost(Long id, User user) {
        UserRoleEnum userRoleEnum = user.getRole();
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            postRepository.deleteById(id);
            return new ResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
        } else if (postRepository.existsByIdAndUsername(id, user.getUsername()) && userRoleEnum == UserRoleEnum.USER) {
            postRepository.deleteById(id);
            return new ResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
        } else {
            throw new IllegalArgumentException("게시글 삭제 실패");
        }
    }
}
