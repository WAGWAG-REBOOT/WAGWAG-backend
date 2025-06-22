package com.noname.wagwag.repository.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_role")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    /* 복합 PK의 userId 부분을 FK로 매핑 */
    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}

