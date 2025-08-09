package com.noname.wagwag.service;

import com.noname.wagwag.model.request.CreateAddressRequest;
import com.noname.wagwag.model.request.UpdateAddressRequest;
import com.noname.wagwag.model.response.AddressResponse;
import com.noname.wagwag.repository.AddressRepository;
import com.wagwag.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {
    
    private final AddressRepository addressRepository;
    
    @Transactional
    public AddressResponse createAddress(CreateAddressRequest request) {
        if (addressRepository.existsByCityIdAndDistrictId(request.getCityId(), request.getDistrictId())) {
            throw new RuntimeException("Address already exists with cityId: " + request.getCityId() 
                    + " and districtId: " + request.getDistrictId());
        }
        
        Address address = new Address();
        address.setCityId(request.getCityId());
        address.setDistrictId(request.getDistrictId());
        address.setPostalCode(request.getPostalCode());
        address.setCreatedAt(Instant.now());
        address.setUpdatedAt(Instant.now());
        
        Address savedAddress = addressRepository.save(address);
        return AddressResponse.from(savedAddress);
    }
    
    public AddressResponse getAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));
        
        return AddressResponse.from(address);
    }
    
    public List<AddressResponse> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(AddressResponse::from)
                .collect(Collectors.toList());
    }
    
    public List<AddressResponse> getAddressesByCity(String cityId) {
        List<Address> addresses = addressRepository.findByCityIdOrderByDistrictId(cityId);
        return addresses.stream()
                .map(AddressResponse::from)
                .collect(Collectors.toList());
    }
    
    public List<AddressResponse> getAddressesByDistrict(String districtId) {
        List<Address> addresses = addressRepository.findByDistrictId(districtId);
        return addresses.stream()
                .map(AddressResponse::from)
                .collect(Collectors.toList());
    }
    
    public List<AddressResponse> getAddressesByPostalCode(String postalCode) {
        List<Address> addresses = addressRepository.findByPostalCode(postalCode);
        return addresses.stream()
                .map(AddressResponse::from)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public AddressResponse updateAddress(Long addressId, UpdateAddressRequest request) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));
        
        if (request.getCityId() != null) {
            address.setCityId(request.getCityId());
        }
        if (request.getDistrictId() != null) {
            address.setDistrictId(request.getDistrictId());
        }
        if (request.getPostalCode() != null) {
            address.setPostalCode(request.getPostalCode());
        }
        
        address.setUpdatedAt(Instant.now());
        Address updatedAddress = addressRepository.save(address);
        return AddressResponse.from(updatedAddress);
    }
    
    @Transactional
    public void deleteAddress(Long addressId) {
        if (!addressRepository.existsById(addressId)) {
            throw new RuntimeException("Address not found with id: " + addressId);
        }
        addressRepository.deleteById(addressId);
    }
}