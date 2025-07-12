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
@Table(name = "post", schema = "wagwag")
public class Post {
    @Column(name = "post_id")
    private Long postId;

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

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "region_id")
    private Long regionId;

}