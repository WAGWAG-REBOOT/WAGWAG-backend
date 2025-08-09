package com.noname.wagwag.repository;

import com.wagwag.Serviceregion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRegionRepository extends JpaRepository<Serviceregion, Long> {
    
    List<Serviceregion> findByIsActiveTrue();
    
    Optional<Serviceregion> findByRegionCode(String regionCode);
    
    List<Serviceregion> findByRegionNameContainingIgnoreCase(String regionName);
    
    @Query("SELECT sr FROM Serviceregion sr WHERE sr.maxBandwidth >= :bandwidth AND sr.isActive = true")
    List<Serviceregion> findActiveRegionsWithMinBandwidth(@Param("bandwidth") Integer bandwidth);
    
    boolean existsByRegionCode(String regionCode);
}