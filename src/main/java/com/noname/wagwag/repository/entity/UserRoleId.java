package com.noname.wagwag.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserRoleId implements Serializable {

    private Long userId;

    @Column(length = 20)
    private String userType;
}

