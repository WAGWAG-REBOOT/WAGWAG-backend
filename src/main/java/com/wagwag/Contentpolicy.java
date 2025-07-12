package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Data
@Entity
@Table(name = "contentpolicy", schema = "wagwag")
public class Contentpolicy {
    @Column(name = "content_policy_id", nullable = false)
    private Long contentPolicyId;

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