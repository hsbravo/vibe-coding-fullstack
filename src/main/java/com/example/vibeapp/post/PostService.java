package com.example.vibeapp.post;

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

    public List<Post> findAll() {
        return postRepository.findAll().stream()
                .sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
                .toList();
    }

    public Page<Post> findAllPaged(int page, int size) {
        List<Post> allPosts = findAll();
        int totalItems = allPosts.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        int start = (page - 1) * size;
        int end = Math.min(start + size, totalItems);

        List<Post> pagedItems = (start < totalItems) ? allPosts.subList(start, end) : List.of();

        return new Page<>(pagedItems, page, totalPages, size, totalItems);
    }

    public Post findById(Long id) {
        Post post = postRepository.findById(id);
        if (post != null) {
            post.setViews(post.getViews() + 1);
        }
        return post;
    }

    public void create(String title, String content) {
        Long nextId = postRepository.findAll().stream()
                .mapToLong(Post::getId)
                .max()
                .orElse(0L) + 1;

        Post newPost = new Post(
                nextId,
                title,
                content,
                LocalDateTime.now(),
                null,
                0);
        postRepository.save(newPost);
    }

    public void update(Long id, String title, String content) {
        Post post = postRepository.findById(id);
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
        }
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
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
                    i * 10));
        }
    }
}
