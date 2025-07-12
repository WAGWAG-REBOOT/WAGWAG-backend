package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "streaminglog", schema = "wagwag")
public class Streaminglog {
    @Column(name = "log_id")
    private Long logId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "video_id", nullable = false)
    private Long videoId;

    @NotNull
    @Column(name = "region_id", nullable = false)
    private Long regionId;

    @Column(name = "endpoint_id")
    private Long endpointId;

    @Size(max = 45)
    @Column(name = "client_ip", length = 45)
    private String clientIp;

    @NotNull
    @Column(name = "stream_start", nullable = false)
    private Instant streamStart;

    @Column(name = "stream_end")
    private Instant streamEnd;

    @Column(name = "bandwidth_used")
    private Integer bandwidthUsed;

    @Size(max = 20)
    @Column(name = "quality_level", length = 20)
    private String qualityLevel;

    @Size(max = 50)
    @Column(name = "error_code", length = 50)
    private String errorCode;

    @Column(name = "created_at")
    private Instant createdAt;

}