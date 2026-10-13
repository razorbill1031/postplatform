package com.postplatform.postplatform.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //게시글 작성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody CreatePostRequest request) {
        return postService.createPost(
                request.title(),
                request.content()
        );
    }

    public record CreatePostRequest(String title, String content) {
    }

    //게시글 목록 조회
    @GetMapping
    public List<Post> getposts() {
        return postService.getPosts();
    }

    //게시글 상세 목록 조회
    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }
}