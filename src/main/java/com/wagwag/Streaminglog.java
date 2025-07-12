package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "streaminglog", schema = "wagwag")
public class Streaminglog {
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "video_id", nullable = false)
    private Long videoId;

    @Column(name = "region_id", nullable = false)
    private Long regionId;

    @Column(name = "endpoint_id")
    private Long endpointId;

    @Column(name = "client_ip", length = 45)
    private String clientIp;

    @Column(name = "stream_start", nullable = false)
    private Instant streamStart;

    @Column(name = "stream_end")
    private Instant streamEnd;

    @Column(name = "bandwidth_used")
    private Integer bandwidthUsed;

    @Column(name = "quality_level", length = 20)
    private String qualityLevel;

    @Column(name = "error_code", length = 50)
    private String errorCode;

    @Column(name = "created_at")
    private Instant createdAt;

}