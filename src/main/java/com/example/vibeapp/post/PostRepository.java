package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
@Mapper
public interface PostRepository {
    List<Post> findAll();
    Post findById(@Param("id") Long id);
    void save(Post post);
    void update(Post post);
    void deleteById(@Param("id") Long id);

    // increment view count atomically
    void incrementViews(@Param("id") Long id);

    // 페이징 및 카운트용 메서드 예시
    List<Post> findPage(@Param("limit") int limit, @Param("offset") int offset);
    int count();
}
