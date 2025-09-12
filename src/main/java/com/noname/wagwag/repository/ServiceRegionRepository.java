package com.noname.wagwag.repository;

import com.noname.wagwag.repository.entity.ServiceRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRegionRepository extends JpaRepository<ServiceRegion, Long> {
    
    List<ServiceRegion> findByIsActiveTrue();
    
    Optional<ServiceRegion> findByRegionCode(String regionCode);
    
    List<ServiceRegion> findByRegionNameContainingIgnoreCase(String regionName);
    
    @Query("SELECT sr FROM ServiceRegion sr WHERE sr.maxBandwidth >= :bandwidth AND sr.isActive = true")
    List<ServiceRegion> findActiveRegionsWithMinBandwidth(@Param("bandwidth") Integer bandwidth);
    
    boolean existsByRegionCode(String regionCode);
}