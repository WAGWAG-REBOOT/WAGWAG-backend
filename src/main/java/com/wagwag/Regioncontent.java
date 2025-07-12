package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Data
@Entity
@Table(name = "regioncontent", schema = "wagwag")
public class Regioncontent {
    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "available_from")
    private Instant availableFrom;

    @Column(name = "available_until")
    private Instant availableUntil;

    @Column(name = "restriction_reason")
    private String restrictionReason;

    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("current_timestamp()")
    @Column(name = "updated_at")
    private Instant updatedAt;

}