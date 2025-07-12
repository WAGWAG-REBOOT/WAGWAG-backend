package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "eventlog", schema = "wagwag")
public class Eventlog {
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_type", nullable = false, length = 50)
    private String eventType;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "timestamp")
    private Instant timestamp;

    @Lob
    @Column(name = "metadata")
    private String metadata;

    @Column(name = "source_service", length = 50)
    private String sourceService;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "created_at")
    private Instant createdAt;

}