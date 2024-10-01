package com.sparesparts.service.serviceImpl;

import com.sparesparts.entity.SavedAddress;
import com.sparesparts.repositories.SavedAddressRepository;
import com.sparesparts.service.SavedAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedAddressServiceImpl implements SavedAddressService {
    private final SavedAddressRepository savedAddressRepository;

    @Autowired
    public SavedAddressServiceImpl(SavedAddressRepository savedAddressRepository) {
        this.savedAddressRepository = savedAddressRepository;
    }

    @Override
    public SavedAddress saveAddress(SavedAddress savedAddress) {
        return savedAddressRepository.save(savedAddress);
    }

    @Override
    public List<SavedAddress> getAddressesByUserId(Long userId) {
        return savedAddressRepository.findByUserId(userId);
    }

    @Override
    public void deleteAddress(Long id) {
        savedAddressRepository.deleteById(id);
    }
}