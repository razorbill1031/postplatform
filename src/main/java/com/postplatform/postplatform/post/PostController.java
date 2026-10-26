package com.postplatform.postplatform.post;

import com.postplatform.postplatform.user.User;
import com.postplatform.postplatform.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    //게시글 작성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody CreatePostRequest request, HttpSession session) {

        Long loginUserId = (Long) session.getAttribute("loginUserId");

        if (loginUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        User author = userService.findById(loginUserId);

        return postService.createPost(
                request.title(),
                request.content(),
                author
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

    //게시글 수정
    @PutMapping("/{id}")
    public Post updatePost(
            @PathVariable Long id,
            @RequestBody UpdatePostRequest request, HttpSession session) {

        Long loginUserId = (Long) session.getAttribute("loginUserId");

        if (loginUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        return postService.updatePost(
                id,
                request.title(),
                request.content(),
                loginUserId
        );
    }

    public record UpdatePostRequest(String title, String content) {
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long id, HttpSession session) {

        Long loginUserId = (Long) session.getAttribute("loginUserId");

        if (loginUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        postService.deletePost(id, loginUserId);
    }
}