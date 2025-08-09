package com.noname.wagwag.model.response;

import com.wagwag.Serviceregion;
import lombok.Data;

import java.time.Instant;

@Data
public class ServiceRegionResponse {
    
    private Long id;
    private String regionName;
    private String regionCode;
    private String streamingServerUrl;
    private String cdnEndpoint;
    private Integer maxBandwidth;
    private Long contentPolicyId;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    
    public static ServiceRegionResponse from(Serviceregion serviceregion) {
        ServiceRegionResponse response = new ServiceRegionResponse();
        response.setId(serviceregion.getId());
        response.setRegionName(serviceregion.getRegionName());
        response.setRegionCode(serviceregion.getRegionCode());
        response.setStreamingServerUrl(serviceregion.getStreamingServerUrl());
        response.setCdnEndpoint(serviceregion.getCdnEndpoint());
        response.setMaxBandwidth(serviceregion.getMaxBandwidth());
        response.setContentPolicyId(serviceregion.getContentPolicyId());
        response.setIsActive(serviceregion.getIsActive());
        response.setCreatedAt(serviceregion.getCreatedAt());
        response.setUpdatedAt(serviceregion.getUpdatedAt());
        return response;
    }
}