package com.byoungju94.blog.post;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Post implements Persistable<UUID> {

    @Id
    private UUID id;

    private String title;
    private String contentFilePath;
    private Long authorId;
    
    private PostState state;

    @JsonIgnore
    private Boolean isInsert;

    @Builder.Default
    private Instant createdAt = Instant.now();

    public void lock() {
        this.state = PostState.LOCKED;
    }

    public void delete() {
        this.state = PostState.DELETED;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return isInsert;
    }
}