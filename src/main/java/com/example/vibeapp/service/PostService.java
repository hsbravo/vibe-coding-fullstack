package com.example.vibeapp.service;

import com.example.vibeapp.domain.Post;
import com.example.vibeapp.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPost(Long no) {
        Post post = postRepository.findByNo(no);
        if (post != null) {
            post.setViews(post.getViews() + 1);
        }
        return post;
    }

    @PostConstruct
    public void init() {
        for (int i = 1; i <= 10; i++) {
            postRepository.save(new Post(
                    (long) i,
                    "테스트 게시글 제목 " + i,
                    "테스트 게시글 내용입니다. " + i,
                    LocalDateTime.now().minusDays(10 - i),
                    LocalDateTime.now(),
                    i * 10
            ));
        }
    }
}
