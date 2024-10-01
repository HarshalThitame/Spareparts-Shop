package com.sparesparts.service;

import com.sparesparts.entity.SavedAddress;

import java.util.List;

public interface SavedAddressService {
    SavedAddress saveAddress(SavedAddress savedAddress);

    List<SavedAddress> getAddressesByUserId(Long userId);

    void deleteAddress(Long id);
}