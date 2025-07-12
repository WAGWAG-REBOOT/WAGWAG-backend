package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Data
@Entity
@Table(name = "userserviceregion", schema = "wagwag")
public class Userserviceregion {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "assigned_at")
    private Instant assignedAt;

    @ColumnDefault("current_timestamp()")
    @Column(name = "last_updated")
    private Instant lastUpdated;

    @ColumnDefault("'AUTO'")
    @Column(name = "assignment_method", length = 20)
    private String assignmentMethod;

}