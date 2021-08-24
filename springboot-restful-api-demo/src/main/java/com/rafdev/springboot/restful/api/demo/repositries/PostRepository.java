package com.rafdev.springboot.restful.api.demo.repositries;

import com.rafdev.springboot.restful.api.demo.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>
{
}
