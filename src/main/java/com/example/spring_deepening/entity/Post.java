package com.example.spring_deepening.entity;

import com.example.spring_deepening.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String tittle;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int likeCount;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // 게시글이 삭제되면 댓글도 삭제
    @OrderBy("id asc ")
    private List<Comment> comments = new ArrayList<>();

    public Post(PostRequestDto requestDto, String username) {
        this.tittle = requestDto.getTittle();
        this.content = requestDto.getContent();
        this.username = username;

    }

    public void update(PostRequestDto requestDto, String username) {
        this.tittle = requestDto.getTittle();
        this.content = requestDto.getContent();
        this.username = username;
    }
}
