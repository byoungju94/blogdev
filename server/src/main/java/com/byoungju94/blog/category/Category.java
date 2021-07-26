package com.byoungju94.blog.category;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;

@Getter
@Table("tbl_category")
public class Category implements Persistable<Long> {

    @Id
    private Long id;

    private String uuid;
    private String name;
    private CategoryState state;

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Builder
    public Category(String uuid, String name, CategoryState state) {
        this.uuid = uuid;
        this.name = name;
        this.state = state;
    }

    @PersistenceConstructor
    public Category(Long id, String uuid, String name, CategoryState state) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.state = state;
        this.isNew = false;
    }

    @Component
    public static class CategoryAfterSaveListener implements ApplicationListener<AfterSaveEvent<?>>, Ordered {

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }

        @Override
        public void onApplicationEvent(AfterSaveEvent<?> event) {
            if (event.getEntity() instanceof Category) {
                ((Category) event.getEntity()).isNew = false;
            }
        }

    }

}
