package com.wagwag;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "video", schema = "wagwag")
public class Video {
    @Id
    @Column(name = "video_id")
    private Long videoId;

    @Size(max = 20)
    @Column(name = "video_length", length = 20)
    private String videoLength;

    @Size(max = 50)
    @Column(name = "encoding_type", length = 50)
    private String encodingType;

    @NotNull
    @Lob
    @Column(name = "video_path", nullable = false)
    private String videoPath;

    @Column(name = "file_size")
    private Long fileSize;

    @Size(max = 20)
    @Column(name = "resolution", length = 20)
    private String resolution;

    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("current_timestamp()")
    @Column(name = "updated_at")
    private Instant updatedAt;

}