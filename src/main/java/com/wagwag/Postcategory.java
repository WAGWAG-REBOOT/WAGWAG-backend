package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "postcategory", schema = "wagwag")
public class Postcategory {
    @Column(name = "post_category_id")
    private Long postCategoryId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "created_at")
    private Instant createdAt;

}