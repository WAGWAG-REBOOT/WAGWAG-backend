package com.wagwag;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "contentpolicy", schema = "wagwag")
public class Contentpolicy {
    @Id
    @Column(name = "content_policy_id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "policy_name", nullable = false, length = 100)
    private String policyName;

    @Lob
    @Column(name = "policy_rules")
    private String policyRules;

    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("current_timestamp()")
    @Column(name = "updated_at")
    private Instant updatedAt;

}