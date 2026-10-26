package com.postplatform.postplatform.post;

import com.postplatform.postplatform.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //게시글 작성
    @Transactional
    public Post createPost(String title, String content, User author) {
        Post post = new Post(title, content, author);
        return postRepository.save(post);
    }

    //게시글 목록 조회
    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    //게시글 상세 목록 조회
    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }

    //게시글 수정
    @Transactional
    public Post updatePost(Long id, String title, String content, Long loginUserId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        if (!post.getAuthor().getId().equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "게시글 작성자만 수정할 수 있습니다.");
        }

        post.update(title, content);

        return post;
    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long id, Long loginUserId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        if (!post.getAuthor().getId().equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "게시글 작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }
}