package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDto;
import com.example.vibeapp.post.dto.PostUpdateDto;
import jakarta.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional // every public method is run in a transactional boundary by default
public class PostService {
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;
    private final JdbcTemplate jdbcTemplate;

    public PostService(PostRepository postRepository, PostTagRepository postTagRepository, JdbcTemplate jdbcTemplate) {
        this.postRepository = postRepository;
        this.postTagRepository = postTagRepository;
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

        // load tags for this post and map to string list
        java.util.List<String> tagNames = java.util.List.of();
        try {
            var tags = postTagRepository.findByPostNo(id);
            if (tags != null && !tags.isEmpty()) {
                tagNames = tags.stream().map(t -> t.getTagName()).toList();
            }
        } catch (Exception ignored) {}

        return PostResponseDto.from(updated, tagNames);
    }

    // create and tag persistence should occur together; the class-level
    // annotation already opens a transaction so both operations are
    // committed or rolled back as one unit.
    public void create(PostCreateDto createDto) {
        Long nextId = postRepository.findAll().stream()
                .mapToLong(Post::getId)
                .max()
                .orElse(0L) + 1;

        Post newPost = createDto.toEntity(nextId);
        postRepository.save(newPost);
        // tags 처리
        if (createDto.tagString() != null && !createDto.tagString().isBlank()) {
            String[] parts = createDto.tagString().split(",");
            for (String p : parts) {
                String tag = p.trim();
                if (!tag.isEmpty()) {
                    postTagRepository.add(new PostTag(null, nextId, tag));
                }
            }
        }
    }

    // update post and refresh tags in the same transaction
    public void update(PostUpdateDto updateDto) {
        Post post = postRepository.findById(updateDto.id());
        if (post != null) {
            post.setTitle(updateDto.title());
            post.setContent(updateDto.content());
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
            // tags 갱신: 기존 태그 삭제 후 신규 태그 저장
            postTagRepository.deleteByPostNo(updateDto.id());
            if (updateDto.tagString() != null && !updateDto.tagString().isBlank()) {
                String[] parts = updateDto.tagString().split(",");
                for (String p : parts) {
                    String tag = p.trim();
                    if (!tag.isEmpty()) {
                        postTagRepository.add(new PostTag(null, updateDto.id(), tag));
                    }
                }
            }
        }
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public PostUpdateDto getEditDto(Long id) {
        Post post = postRepository.findById(id);
        if (post == null) return null;
        // build tag string from tag repository
        String tagString = null;
        try {
            var tags = postTagRepository.findByPostNo(id);
            if (tags != null && !tags.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tags.size(); i++) {
                    if (i > 0) sb.append(',');
                    sb.append(tags.get(i).getTagName());
                }
                tagString = sb.toString();
            }
        } catch (Exception ignored) {}

        return new PostUpdateDto(post.getId(), post.getTitle(), post.getContent(), tagString);
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
