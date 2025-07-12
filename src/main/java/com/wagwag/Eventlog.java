package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "eventlog", schema = "wagwag")
public class Eventlog {
    @Column(name = "event_id")
    private Long eventId;

    @Size(max = 50)
    @NotNull
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

    @Size(max = 50)
    @Column(name = "source_service", length = 50)
    private String sourceService;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "created_at")
    private Instant createdAt;

}