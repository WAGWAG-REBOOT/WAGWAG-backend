package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "`like`", schema = "wagwag")
public class Like {
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "user_id", nullable = false)
    private Long userId;

}