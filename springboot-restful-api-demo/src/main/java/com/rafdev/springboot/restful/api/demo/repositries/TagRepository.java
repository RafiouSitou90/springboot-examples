package com.rafdev.springboot.restful.api.demo.repositries;

import com.rafdev.springboot.restful.api.demo.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>
{
}
