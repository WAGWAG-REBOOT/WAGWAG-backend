package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Data
@Entity
@Table(name = "address", schema = "wagwag")
public class Address {
    @Column(name = "address_id", nullable = false)
    private Long addressId;

    @Column(name = "city_id", nullable = false, length = 50)
    private String cityId;

    @Column(name = "district_id", nullable = false, length = 50)
    private String districtId;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("current_timestamp()")
    @Column(name = "updated_at")
    private Instant updatedAt;

}