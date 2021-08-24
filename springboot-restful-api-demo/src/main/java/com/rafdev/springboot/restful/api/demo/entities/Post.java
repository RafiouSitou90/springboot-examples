package com.rafdev.springboot.restful.api.demo.entities;

import com.github.slugify.Slugify;
import com.rafdev.springboot.restful.api.demo.entities.listeners.PostListener;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EntityListeners(PostListener.class)
@Table(name = "tab_posts")
public class Post extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    @NotBlank
    private String summary;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @NotBlank
    private String content;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @ManyToOne(
            targetEntity = Category.class,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id"
    )
    private Category category;

    @ManyToMany(
            targetEntity = Tag.class,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "tab_post_tags",
            joinColumns = @JoinColumn(
                    name = "post_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "tag_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Tag> tags = new HashSet<>();

    public void computeSlug() {
        Slugify slugify = new Slugify();
        this.slug = slugify.slugify(title);
    }
}
