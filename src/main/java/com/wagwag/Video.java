package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Data
@Entity
@Table(name = "video", schema = "wagwag")
public class Video {
    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "video_length", length = 20)
    private String videoLength;

    @Column(name = "encoding_type", length = 50)
    private String encodingType;

    @Lob
    @Column(name = "video_path", nullable = false)
    private String videoPath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "resolution", length = 20)
    private String resolution;

    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("current_timestamp()")
    @Column(name = "updated_at")
    private Instant updatedAt;

}