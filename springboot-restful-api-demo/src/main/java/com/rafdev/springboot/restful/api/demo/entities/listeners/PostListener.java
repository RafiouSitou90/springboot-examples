package com.rafdev.springboot.restful.api.demo.entities.listeners;

import com.rafdev.springboot.restful.api.demo.entities.Post;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
public class PostListener
{
    @PrePersist
    @PreUpdate
    public void onPrePersist(final Post post) {
        post.computeSlug();
    }
}
