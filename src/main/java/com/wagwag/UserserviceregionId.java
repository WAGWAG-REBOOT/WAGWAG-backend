package com.wagwag;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UserserviceregionId implements java.io.Serializable {
    private static final long serialVersionUID = 3756215546070220058L;
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "region_id")
    private Long regionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserserviceregionId entity = (UserserviceregionId) o;
        return Objects.equals(this.regionId, entity.regionId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionId, userId);
    }

}