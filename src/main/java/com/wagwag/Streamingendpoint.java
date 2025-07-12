package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Data
@Entity
@Table(name = "streamingendpoint", schema = "wagwag")
public class Streamingendpoint {
    @Column(name = "endpoint_id", nullable = false)
    private Long endpointId;

    @Column(name = "region_id", nullable = false)
    private Long regionId;

    @Column(name = "cdn_url", nullable = false)
    private String cdnUrl;

    @Column(name = "server_url", nullable = false)
    private String serverUrl;

    @Column(name = "server_capacity")
    private Integer serverCapacity;

    @ColumnDefault("'HEALTHY'")
    @Column(name = "health_status", length = 20)
    private String healthStatus;

    @Column(name = "last_health_check")
    private Instant lastHealthCheck;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("current_timestamp()")
    @Column(name = "updated_at")
    private Instant updatedAt;

}