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
public class UseraddressId implements java.io.Serializable {
    private static final long serialVersionUID = -369194506655300940L;
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "address_id")
    private Long addressId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UseraddressId entity = (UseraddressId) o;
        return Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.addressId, entity.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, addressId);
    }

}