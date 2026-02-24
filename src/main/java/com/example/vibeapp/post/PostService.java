package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDto;
import com.example.vibeapp.post.dto.PostUpdateDto;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final JdbcTemplate jdbcTemplate;

    public PostService(PostRepository postRepository, JdbcTemplate jdbcTemplate) {
        this.postRepository = postRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PostListDto> findAll() {
        return postRepository.findAll().stream()
                .sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
                .map(PostListDto::from)
                .toList();
    }

    public Page<PostListDto> findAllPaged(int page, int size) {
        List<PostListDto> allPosts = findAll();
        int totalItems = allPosts.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        int start = (page - 1) * size;
        int end = Math.min(start + size, totalItems);

        List<PostListDto> pagedItems = (start < totalItems) ? allPosts.subList(start, end) : List.of();

        return new Page<>(pagedItems, page, totalPages, size, totalItems);
    }

    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id);
        if (post == null) {
            return PostResponseDto.from(null);
        }

        // increment views atomically in DB and return updated entity
        postRepository.incrementViews(id);
        Post updated = postRepository.findById(id);
        return PostResponseDto.from(updated);
    }

    public void create(PostCreateDto createDto) {
        Long nextId = postRepository.findAll().stream()
                .mapToLong(Post::getId)
                .max()
                .orElse(0L) + 1;

        Post newPost = createDto.toEntity(nextId);
        postRepository.save(newPost);
    }

    public void update(PostUpdateDto updateDto) {
        Post post = postRepository.findById(updateDto.id());
        if (post != null) {
            post.setTitle(updateDto.title());
            post.setContent(updateDto.content());
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
        }
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @PostConstruct
    public void init() {
        // Only seed test data when POSTS table is empty
        Integer count = 0;
        try {
            count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM POSTS", Integer.class);
        } catch (Exception ignored) {
        }

        if (count == null || count == 0) {
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
}
