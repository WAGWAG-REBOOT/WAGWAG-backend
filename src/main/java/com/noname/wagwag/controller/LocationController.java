package com.noname.wagwag.controller;

import com.noname.wagwag.model.request.LocationUpdateRequest;
import com.noname.wagwag.model.response.LocationResponse;
import com.noname.wagwag.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocationController {
    
    private final LocationService locationService;
    
    @GetMapping("/location/find-by-geo-coordinate")
    public ResponseEntity<LocationResponse> findLocationByCoordinates(
            @RequestParam("latitude") BigDecimal latitude,
            @RequestParam("longitude") BigDecimal longitude) {
        
        log.info("Finding location by coordinates: lat={}, lon={}", latitude, longitude);
        
        try {
            LocationResponse response = locationService.findLocationByCoordinates(latitude, longitude);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error finding location: {}", e.getMessage());
            
            LocationResponse errorResponse = LocationResponse.builder()
                    .success(false)
                    .build();
            
            if (e.getMessage().contains("잘못된")) {
                return ResponseEntity.badRequest().body(errorResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }
    
    @PutMapping("/user/{userid}/location")
    public ResponseEntity<Map<String, Object>> updateUserLocation(
            @PathVariable("userid") Long userId,
            @Valid @RequestBody LocationUpdateRequest request) {
        
        log.info("Updating location for user: {} with dongCode: {}", userId, request.getDongCode());
        
        try {
            Map<String, Object> response = locationService.updateUserLocation(userId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error updating user location: {}", e.getMessage());
            
            Map<String, Object> errorResponse = Map.of(
                    "success", false,
                    "message", e.getMessage()
            );
            
            if (e.getMessage().contains("찾을 수 없습니다")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            } else {
                return ResponseEntity.badRequest().body(errorResponse);
            }
        }
    }
}
