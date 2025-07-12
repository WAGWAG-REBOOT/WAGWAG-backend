package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "addressserviceregion", schema = "wagwag")
public class Addressserviceregion {
    @EmbeddedId
    private AddressserviceregionId id;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "mapped_at")
    private Instant mappedAt;

}