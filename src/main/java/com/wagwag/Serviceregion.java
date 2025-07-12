package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Data
@Entity
@Table(name = "serviceregion", schema = "wagwag")
public class Serviceregion {
    @Column(name = "region_id", nullable = false)
    private Long regionId;

    @Column(name = "region_name", nullable = false, length = 100)
    private String regionName;

    @Column(name = "region_code", nullable = false, length = 20)
    private String regionCode;

    @Column(name = "streaming_server_url")
    private String streamingServerUrl;

    @Column(name = "cdn_endpoint")
    private String cdnEndpoint;

    @Column(name = "max_bandwidth")
    private Integer maxBandwidth;

    @Column(name = "content_policy_id")
    private Long contentPolicyId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private Instant createdAt;
    @ColumnDefault("current_timestamp()")
    @Column(name = "updated_at")
    private Instant updatedAt;

/*
 TODO [Reverse Engineering] create field to map the 'coverage_polygon' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "coverage_polygon", columnDefinition = "geometry")
    private Object coveragePolygon;
*/
}