package com.rafdev.springboot.restful.api.demo.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "tab_tags")
public class Tag extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String name;

    @ManyToMany(
            targetEntity = Post.class,
            mappedBy = "tags",
            cascade = CascadeType.ALL
    )
    @ToString.Exclude
    private List<Post> posts;
}
