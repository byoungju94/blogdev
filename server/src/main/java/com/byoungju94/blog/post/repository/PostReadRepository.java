package com.byoungju94.blog.post.repository;

import java.util.List;

import com.byoungju94.blog.post.dto.PostDTO;

import org.springframework.data.domain.Pageable;

public interface PostReadRepository {

    List<PostDTO> findByCategoryIdWithPaging(Long categoryId, Pageable pageable);

    PostDTO findByIdLatestEvent(String id);
}
