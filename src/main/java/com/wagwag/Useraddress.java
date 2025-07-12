package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Data
@Entity
@Table(name = "useraddress", schema = "wagwag")
public class Useraddress {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "address_id")
    private Long addressId;

    @ColumnDefault("'HOME'")
    @Column(name = "address_type", length = 20)
    private String addressType;

    @Column(name = "created_at")
    private Instant createdAt;

}