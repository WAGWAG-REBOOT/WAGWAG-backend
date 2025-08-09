package com.noname.wagwag.repository;

import com.wagwag.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
    List<Address> findByCityIdOrderByDistrictId(String cityId);
    
    List<Address> findByDistrictId(String districtId);
    
    Optional<Address> findByCityIdAndDistrictId(String cityId, String districtId);
    
    @Query("SELECT a FROM Address a WHERE a.postalCode = :postalCode")
    List<Address> findByPostalCode(@Param("postalCode") String postalCode);
    
    boolean existsByCityIdAndDistrictId(String cityId, String districtId);
}