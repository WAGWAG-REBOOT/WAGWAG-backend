package com.noname.wagwag.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Builder
@Getter
@Setter
@Entity
@Table(name = "`like`", schema = "wagwag")
public class Like {
    @EmbeddedId
    private LikeId id;

    @Column(name = "created_at")
    private Instant createdAt;

}