package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "address", schema = "wagwag")
public class Address {
    @Id
    @Column(name = "address_id", nullable = false)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "city_id", nullable = false, length = 50)
    private String cityId;

    @Size(max = 50)
    @NotNull
    @Column(name = "district_id", nullable = false, length = 50)
    private String districtId;

    @Size(max = 10)
    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("current_timestamp()")
    @Column(name = "updated_at")
    private Instant updatedAt;

}