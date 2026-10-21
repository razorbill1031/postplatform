package com.postplatform.postplatform.post;

import com.postplatform.postplatform.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    //게시글과 회원 연결
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    protected Post() {
    }

    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    //게시글 수정
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}