package com.example.spring_deepening.controller;

import com.example.spring_deepening.dto.CommentRequestDto;
import com.example.spring_deepening.dto.CommentResponseDto;
import com.example.spring_deepening.dto.ResponseDto;
import com.example.spring_deepening.security.UserDetailsImpl;
import com.example.spring_deepening.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    // 댓글 작성
    @PostMapping("/{id}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long id,
                                                            @RequestBody CommentRequestDto commentRequestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.createComment(id, commentRequestDto, userDetails.getUser()));
    }
    // 댓글 수정
    @PutMapping("/{postId}/{cmtId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long postId, @PathVariable Long cmtId,
                                                            @RequestBody CommentRequestDto commentRequestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.updateComment(postId, cmtId, commentRequestDto, userDetails.getUser()));
    }
    // 댓글 삭제
    @DeleteMapping("/{postId}/{cmtId}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable Long postId, @PathVariable Long cmtId,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(postId, cmtId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDto("식제 성공", HttpStatus.OK.value()));
    }
}
