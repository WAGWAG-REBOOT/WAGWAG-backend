package com.noname.wagwag.controller;

import com.noname.wagwag.model.request.CreateAddressRequest;
import com.noname.wagwag.model.request.UpdateAddressRequest;
import com.noname.wagwag.model.response.AddressResponse;
import com.noname.wagwag.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    
    private final AddressService addressService;
    
    /**
     * 모든 주소 조회
     * GET /api/addresses
     */
    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAllAddresses() {
        List<AddressResponse> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }
    
    /**
     * ID로 특정 주소 조회
     * GET /api/addresses/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) {
        AddressResponse address = addressService.getAddress(id);
        return ResponseEntity.ok(address);
    }
    
    /**
     * 새 주소 생성
     * POST /api/addresses
     */
    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@Valid @RequestBody CreateAddressRequest request) {
        AddressResponse address = addressService.createAddress(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }
    
    /**
     * 기존 주소 수정
     * PUT /api/addresses/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAddressRequest request) {
        
        AddressResponse address = addressService.updateAddress(id, request);
        return ResponseEntity.ok(address);
    }
    
    /**
     * 주소 삭제
     * DELETE /api/addresses/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 도시별 주소 조회
     * GET /api/addresses?cityId=cityValue
     */
    @GetMapping(params = "cityId")
    public ResponseEntity<List<AddressResponse>> getAddressesByCity(@RequestParam String cityId) {
        List<AddressResponse> addresses = addressService.getAddressesByCity(cityId);
        return ResponseEntity.ok(addresses);
    }
    
    /**
     * 구/군별 주소 조회
     * GET /api/addresses?districtId=districtValue
     */
    @GetMapping(params = "districtId")
    public ResponseEntity<List<AddressResponse>> getAddressesByDistrict(@RequestParam String districtId) {
        List<AddressResponse> addresses = addressService.getAddressesByDistrict(districtId);
        return ResponseEntity.ok(addresses);
    }
    
    /**
     * 우편번호별 주소 조회
     * GET /api/addresses?postalCode=postalValue
     */
    @GetMapping(params = "postalCode")
    public ResponseEntity<List<AddressResponse>> getAddressesByPostalCode(@RequestParam String postalCode) {
        List<AddressResponse> addresses = addressService.getAddressesByPostalCode(postalCode);
        return ResponseEntity.ok(addresses);
    }
}