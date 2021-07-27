package com.byoungju94.blog.post.repository;

import java.time.Instant;
import java.util.List;

import com.byoungju94.blog.post.PostState;
import com.byoungju94.blog.post.dto.PostDTO;

import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class PostReadRepositoryImpl implements PostReadRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostReadRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    RowMapper<PostDTO> rowMapperDTO = ((rs, rowNum) -> new PostDTO(
            rs.getString("id"), 
            rs.getString("title"), 
            rs.getString("state"),
            rs.getString("created_at")
    ));

    @Override
    public List<PostDTO> findByCategoryIdWithPaging(Long categoryId, Pageable pageable) {
        var param = new MapSqlParameterSource()
                .addValue("categoryId", (categoryId))
                .addValue("state", PostState.OPENED.toString())
                .addValue("number", pageable.getPageNumber())
                .addValue("size", pageable.getPageSize());
        var query = PostNativeQuerySQL.findByCategoryId;

        return this.jdbcOperations.query(query, param, rowMapperDTO);
    }

    @Override
    public PostDTO findByIdLatestEvent(String id) {
        var param = new MapSqlParameterSource()
                .addValue("id", id);
        var query = PostNativeQuerySQL.findByIdLatestEvent;

        return this.jdbcOperations.queryForObject(query, param, rowMapperDTO);
    }
}
