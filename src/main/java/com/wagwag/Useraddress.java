package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "useraddress", schema = "wagwag")
public class Useraddress {
    @EmbeddedId
    private UseraddressId id;

    @Size(max = 20)
    @ColumnDefault("'HOME'")
    @Column(name = "address_type", length = 20)
    private String addressType;

    @Column(name = "created_at")
    private Instant createdAt;

}