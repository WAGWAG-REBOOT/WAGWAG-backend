package com.noname.wagwag.repository;

import com.noname.wagwag.repository.entity.GeoLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface GeoLocationRepository extends JpaRepository<GeoLocation, Long> {
    
    @Query("SELECT g FROM GeoLocation g WHERE " +
           "g.latitude BETWEEN :minLat AND :maxLat AND " +
           "g.longitude BETWEEN :minLon AND :maxLon")
    List<GeoLocation> findByLocationRange(
            @Param("minLat") BigDecimal minLat,
            @Param("maxLat") BigDecimal maxLat,
            @Param("minLon") BigDecimal minLon,
            @Param("maxLon") BigDecimal maxLon);
    
    @Query("SELECT g FROM GeoLocation g WHERE " +
           "ABS(g.latitude - :latitude) < :tolerance AND " +
           "ABS(g.longitude - :longitude) < :tolerance " +
           "ORDER BY ABS(g.latitude - :latitude) + ABS(g.longitude - :longitude) ASC")
    List<GeoLocation> findNearbyLocations(
            @Param("latitude") BigDecimal latitude,
            @Param("longitude") BigDecimal longitude,
            @Param("tolerance") BigDecimal tolerance);
}
