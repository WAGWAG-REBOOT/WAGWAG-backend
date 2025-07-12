package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "streamingendpoint", schema = "wagwag")
public class Streamingendpoint {
    @Id
    @Column(name = "endpoint_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "region_id", nullable = false)
    private Long regionId;

    @Size(max = 255)
    @NotNull
    @Column(name = "cdn_url", nullable = false)
    private String cdnUrl;

    @Size(max = 255)
    @NotNull
    @Column(name = "server_url", nullable = false)
    private String serverUrl;

    @Column(name = "server_capacity")
    private Integer serverCapacity;

    @Size(max = 20)
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