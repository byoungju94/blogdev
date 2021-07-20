package com.byoungju94.blog.post;

import java.time.Instant;
import java.util.UUID;

import com.byoungju94.blog.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Post implements Persistable<UUID> {

    @Id
    private UUID id;

    private String title;
    private String contentFilePath;
    private PostState state;
    private AggregateReference<Account, UUID> authorId;

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