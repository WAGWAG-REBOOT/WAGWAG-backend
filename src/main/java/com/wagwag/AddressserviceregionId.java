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
public class AddressserviceregionId implements java.io.Serializable {
    private static final long serialVersionUID = -615397414189396469L;
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "region_id")
    private Long regionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AddressserviceregionId entity = (AddressserviceregionId) o;
        return Objects.equals(this.regionId, entity.regionId) &&
                Objects.equals(this.addressId, entity.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionId, addressId);
    }

}