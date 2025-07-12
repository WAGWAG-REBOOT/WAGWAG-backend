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
@Table(name = "userserviceregion", schema = "wagwag")
public class Userserviceregion {
    @EmbeddedId
    private UserserviceregionId id;

    @Column(name = "assigned_at")
    private Instant assignedAt;

    @ColumnDefault("current_timestamp()")
    @Column(name = "last_updated")
    private Instant lastUpdated;

    @Size(max = 20)
    @ColumnDefault("'AUTO'")
    @Column(name = "assignment_method", length = 20)
    private String assignmentMethod;

}