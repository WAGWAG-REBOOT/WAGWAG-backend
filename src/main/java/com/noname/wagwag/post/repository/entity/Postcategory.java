package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "postcategory", schema = "wagwag")
public class Postcategory {
    @Id
    @Column(name = "post_category_id")
    private Long postCategoryId;

    @NotNull
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @NotNull
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "created_at")
    private Instant createdAt;

}