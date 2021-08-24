package com.rafdev.springboot.restful.api.demo.entities.listeners;

import com.rafdev.springboot.restful.api.demo.entities.Category;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
public class CategoryListener
{
    @PrePersist
    @PreUpdate
    public void onPrePersist(final Category category) {
        category.computeSlug();
    }
}
