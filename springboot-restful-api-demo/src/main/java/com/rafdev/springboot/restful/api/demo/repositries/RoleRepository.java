package com.rafdev.springboot.restful.api.demo.repositries;

import com.rafdev.springboot.restful.api.demo.entities.ERole;
import com.rafdev.springboot.restful.api.demo.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
    Optional<Role> findByName(ERole name);
}