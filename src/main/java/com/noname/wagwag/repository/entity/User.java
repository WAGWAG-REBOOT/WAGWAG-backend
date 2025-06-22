package com.noname.wagwag.repository.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`user`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;                   // →

    /* 기본 컬럼 (길이만 지정) */
    @Column(length = 20, nullable = false)
    private String userName;

    @Column(length = 20, nullable = false)
    private String nickName;

    private Integer age;

    @Column(length = 20, nullable = false, unique = true)
    private String email;

    @Column(length = 10)
    private String dongCode;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(length = 20)
    private String userState;

    private Date created;
    private Date lastUpdated;

    /* 1:N 관계 */
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<UserRole> roles = new ArrayList<>();

}

