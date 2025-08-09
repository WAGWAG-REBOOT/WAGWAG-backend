package com.noname.wagwag.model.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateAddressRequest {
    
    @Size(max = 50, message = "City ID must not exceed 50 characters")
    private String cityId;
    
    @Size(max = 50, message = "District ID must not exceed 50 characters")
    private String districtId;
    
    @Size(max = 10, message = "Postal code must not exceed 10 characters")
    private String postalCode;
}