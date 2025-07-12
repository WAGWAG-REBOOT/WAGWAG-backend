package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "addressserviceregion", schema = "wagwag")
public class Addressserviceregion {
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "mapped_at")
    private Instant mappedAt;

}