package com.noname.wagwag.service;

import com.noname.wagwag.model.request.LocationUpdateRequest;
import com.noname.wagwag.model.response.LocationResponse;
import com.noname.wagwag.repository.GeoLocationRepository;
import com.noname.wagwag.repository.ServiceRegionRepository;
import com.noname.wagwag.repository.UserRepository;
import com.noname.wagwag.repository.entity.GeoLocation;
import com.noname.wagwag.repository.entity.ServiceRegion;
import com.noname.wagwag.repository.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {
    
    private final GeoLocationRepository geoLocationRepository;
    private final ServiceRegionRepository serviceRegionRepository;
    private final UserRepository userRepository;
    
    public LocationResponse findLocationByCoordinates(BigDecimal latitude, BigDecimal longitude) {
        log.info("Finding location by coordinates: lat={}, lon={}", latitude, longitude);
        
        try {
            // 좌표 유효성 검사
            validateCoordinates(latitude, longitude);
            
            // 가까운 위치 찾기 (tolerance 0.01도 = 약 1km)
            BigDecimal tolerance = new BigDecimal("0.01");
            List<GeoLocation> nearbyLocations = geoLocationRepository.findNearbyLocations(
                    latitude, longitude, tolerance);
            
            if (nearbyLocations.isEmpty()) {
                // 실제 서비스에서는 외부 지오코딩 API를 사용해야 함
                // 여기서는 기본 지역 정보를 반환
                return createDefaultLocationResponse(latitude, longitude);
            }
            
            // 가장 가까운 위치의 지역 정보 반환
            GeoLocation nearestLocation = nearbyLocations.get(0);
            
            // 해당 좌표에 맞는 ServiceRegion 찾기 (실제로는 더 복잡한 로직 필요)
            List<ServiceRegion> regions = serviceRegionRepository.findAll();
            ServiceRegion defaultRegion = regions.isEmpty() ? null : regions.get(0);
            
            if (defaultRegion == null) {
                throw new RuntimeException("서비스 지역을 찾을 수 없습니다");
            }
            
            LocationResponse.LocationInfo locationInfo = LocationResponse.LocationInfo.builder()
                    .regionId(defaultRegion.getId())
                    .regionName(defaultRegion.getRegionName())
                    .dongCode(generateDongCode(latitude, longitude))
                    .address(generateAddress(latitude, longitude))
                    .build();
            
            return LocationResponse.builder()
                    .success(true)
                    .location(locationInfo)
                    .build();
                    
        } catch (Exception e) {
            log.error("Error finding location by coordinates", e);
            throw new RuntimeException("위치 정보를 찾을 수 없습니다: " + e.getMessage());
        }
    }
    
    @Transactional
    public Map<String, Object> updateUserLocation(Long userId, LocationUpdateRequest request) {
        log.info("Updating location for user: {} with dongCode: {}", userId, request.getDongCode());
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId));
        
        // User의 dongCode 업데이트
        user.setDongCode(request.getDongCode());
        userRepository.save(user);
        
        log.info("User location updated successfully for user: {}", userId);
        
        return Map.of(
                "success", true,
                "message", "위치가 성공적으로 업데이트되었습니다",
                "updatedLocation", Map.of(
                        "userId", userId,
                        "dongCode", request.getDongCode(),
                        "regionName", request.getRegionName() != null ? request.getRegionName() : ""
                )
        );
    }
    
    private void validateCoordinates(BigDecimal latitude, BigDecimal longitude) {
        // 위도는 -90 ~ 90, 경도는 -180 ~ 180
        if (latitude.compareTo(new BigDecimal("-90")) < 0 || 
            latitude.compareTo(new BigDecimal("90")) > 0) {
            throw new RuntimeException("잘못된 위도값입니다: " + latitude);
        }
        
        if (longitude.compareTo(new BigDecimal("-180")) < 0 || 
            longitude.compareTo(new BigDecimal("180")) > 0) {
            throw new RuntimeException("잘못된 경도값입니다: " + longitude);
        }
    }
    
    private LocationResponse createDefaultLocationResponse(BigDecimal latitude, BigDecimal longitude) {
        // 기본 지역 정보 (실제로는 외부 API나 더 정교한 로직 필요)
        List<ServiceRegion> regions = serviceRegionRepository.findAll();
        ServiceRegion defaultRegion = regions.isEmpty() ? null : regions.get(0);
        
        if (defaultRegion == null) {
            throw new RuntimeException("기본 서비스 지역이 설정되지 않았습니다");
        }
        
        LocationResponse.LocationInfo locationInfo = LocationResponse.LocationInfo.builder()
                .regionId(defaultRegion.getId())
                .regionName("알 수 없는 위치")
                .dongCode(generateDongCode(latitude, longitude))
                .address(String.format("위도: %s, 경도: %s", latitude, longitude))
                .build();
        
        return LocationResponse.builder()
                .success(true)
                .location(locationInfo)
                .build();
    }
    
    private String generateDongCode(BigDecimal latitude, BigDecimal longitude) {
        // 실제로는 행정구역 API를 사용해야 함
        // 임시로 좌표 기반 코드 생성
        return String.format("%d%d", 
                latitude.multiply(new BigDecimal("1000")).intValue() % 100000,
                longitude.multiply(new BigDecimal("1000")).intValue() % 100);
    }
    
    private String generateAddress(BigDecimal latitude, BigDecimal longitude) {
        // 실제로는 역지오코딩 API를 사용해야 함
        // 임시로 기본 주소 반환
        return String.format("위도 %s, 경도 %s 근처", latitude, longitude);
    }
}
