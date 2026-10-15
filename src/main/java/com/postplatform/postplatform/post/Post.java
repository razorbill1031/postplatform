package com.postplatform.postplatform.post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    protected Post() {
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //게시글 수정
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}