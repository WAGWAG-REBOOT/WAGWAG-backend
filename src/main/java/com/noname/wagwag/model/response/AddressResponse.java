package com.noname.wagwag.model.response;

import com.wagwag.Address;
import lombok.Data;

import java.time.Instant;

@Data
public class AddressResponse {
    
    private Long id;
    private String cityId;
    private String districtId;
    private String postalCode;
    private Instant createdAt;
    private Instant updatedAt;
    
    public static AddressResponse from(Address address) {
        AddressResponse response = new AddressResponse();
        response.setId(address.getId());
        response.setCityId(address.getCityId());
        response.setDistrictId(address.getDistrictId());
        response.setPostalCode(address.getPostalCode());
        response.setCreatedAt(address.getCreatedAt());
        response.setUpdatedAt(address.getUpdatedAt());
        return response;
    }
}