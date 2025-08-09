package com.noname.wagwag.service;

import com.noname.wagwag.model.response.PostResponse;
import com.noname.wagwag.model.response.ServiceRegionResponse;
import com.noname.wagwag.repository.ServiceRegionRepository;
import com.noname.wagwag.service.PostService;
import com.wagwag.Serviceregion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceRegionService {
    
    private final ServiceRegionRepository serviceRegionRepository;
    private final PostService postService;
    
    public ServiceRegionResponse getRegion(Long regionId) {
        Serviceregion serviceregion = serviceRegionRepository.findById(regionId)
                .orElseThrow(() -> new RuntimeException("Service region not found with id: " + regionId));
        
        return ServiceRegionResponse.from(serviceregion);
    }
    
    public List<ServiceRegionResponse> getAllRegions() {
        List<Serviceregion> regions = serviceRegionRepository.findAll();
        return regions.stream()
                .map(ServiceRegionResponse::from)
                .toList();
    }
    
    public List<ServiceRegionResponse> getActiveRegions() {
        List<Serviceregion> regions = serviceRegionRepository.findByIsActiveTrue();
        return regions.stream()
                .map(ServiceRegionResponse::from)
                .toList();
    }
    
    public ServiceRegionResponse getRegionByCode(String regionCode) {
        Serviceregion serviceregion = serviceRegionRepository.findByRegionCode(regionCode)
                .orElseThrow(() -> new RuntimeException("Service region not found with code: " + regionCode));
        
        return ServiceRegionResponse.from(serviceregion);
    }
    
    public List<ServiceRegionResponse> searchRegionsByName(String regionName) {
        List<Serviceregion> regions = serviceRegionRepository.findByRegionNameContainingIgnoreCase(regionName);
        return regions.stream()
                .map(ServiceRegionResponse::from)
                .toList();
    }
    
    public List<ServiceRegionResponse> getRegionsWithMinBandwidth(Integer bandwidth) {
        List<Serviceregion> regions = serviceRegionRepository.findActiveRegionsWithMinBandwidth(bandwidth);
        return regions.stream()
                .map(ServiceRegionResponse::from)
                .toList();
    }
    
    public List<PostResponse> getPostsInRegion(Long regionId) {
        if (!serviceRegionRepository.existsById(regionId)) {
            throw new RuntimeException("Service region not found with id: " + regionId);
        }
        
        return postService.getPostsByRegion(regionId);
    }
}