package com.postplatform.postplatform.post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post createPost(String title, String content) {
        Post post = new Post(title, content);
        return postRepository.save(post);
    }
}