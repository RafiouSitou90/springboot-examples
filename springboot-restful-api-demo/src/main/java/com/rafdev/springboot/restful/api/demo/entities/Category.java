package com.rafdev.springboot.restful.api.demo.entities;

import com.github.slugify.Slugify;
import com.rafdev.springboot.restful.api.demo.entities.listeners.CategoryListener;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EntityListeners(CategoryListener.class)
@Table(name = "tab_categories")
public class Category extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @OneToMany(
            targetEntity = Post.class,
            mappedBy = "category",
            cascade = CascadeType.ALL
    )
    @ToString.Exclude
    private Set<Post> posts = new HashSet<>();

    public void computeSlug() {
        Slugify slugify = new Slugify();
        this.slug = slugify.slugify(name);
    }
}
