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
@Table(name = "post", schema = "wagwag")
public class Post {
    @Id
    @Column(name = "post_id")
    private Long postId;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("current_timestamp()")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Lob
    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "view_cnt")
    private Integer viewCnt;

    @Column(name = "share_cnt")
    private Integer shareCnt;

    @Column(name = "access_status")
    private Integer accessStatus;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "region_id")
    private Long regionId;

}