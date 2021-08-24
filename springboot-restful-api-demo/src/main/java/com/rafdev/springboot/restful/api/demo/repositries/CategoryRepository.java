package com.rafdev.springboot.restful.api.demo.repositries;

import com.rafdev.springboot.restful.api.demo.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>
{
}
