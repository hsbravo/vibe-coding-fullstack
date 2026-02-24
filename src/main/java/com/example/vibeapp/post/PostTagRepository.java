package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostTagRepository {
    // 태그 추가
    void add(PostTag postTag);

    // 태그 삭제 (ID로)
    void deleteById(@Param("id") Long id);
    
    // 게시글 기준으로 태그 전체 삭제
    void deleteByPostNo(@Param("postNo") Long postNo);

    // 게시글의 태그 목록 조회
    java.util.List<PostTag> findByPostNo(@Param("postNo") Long postNo);
}
