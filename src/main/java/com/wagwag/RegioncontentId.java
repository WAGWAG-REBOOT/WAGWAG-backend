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
public class RegioncontentId implements java.io.Serializable {
    private static final long serialVersionUID = 1026787417996712266L;
    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "post_id")
    private Long postId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RegioncontentId entity = (RegioncontentId) o;
        return Objects.equals(this.regionId, entity.regionId) &&
                Objects.equals(this.postId, entity.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionId, postId);
    }

}