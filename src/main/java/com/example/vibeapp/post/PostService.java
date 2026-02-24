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

    public List<Post> getAllPosts() {
        return postRepository.findAll().stream()
                .sorted((p1, p2) -> p2.getNo().compareTo(p1.getNo()))
                .toList();
    }

    public Page<Post> getPagedPosts(int page, int size) {
        List<Post> allPosts = getAllPosts();
        int totalItems = allPosts.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        int start = (page - 1) * size;
        int end = Math.min(start + size, totalItems);

        List<Post> pagedItems = (start < totalItems) ? allPosts.subList(start, end) : List.of();

        return new Page<>(pagedItems, page, totalPages, size, totalItems);
    }

    public Post getPost(Long no) {
        Post post = postRepository.findByNo(no);
        if (post != null) {
            post.setViews(post.getViews() + 1);
        }
        return post;
    }

    public void addPost(String title, String content) {
        Long nextNo = postRepository.findAll().stream()
                .mapToLong(Post::getNo)
                .max()
                .orElse(0L) + 1;

        Post newPost = new Post(
                nextNo,
                title,
                content,
                LocalDateTime.now(),
                null,
                0);
        postRepository.save(newPost);
    }

    public void updatePost(Long no, String title, String content) {
        Post post = postRepository.findByNo(no);
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
        }
    }

    public void deletePost(Long no) {
        postRepository.deleteByNo(no);
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
