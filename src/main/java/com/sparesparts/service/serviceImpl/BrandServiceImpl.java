package com.sparesparts.service.serviceImpl;

import com.sparesparts.entity.Brand;
import com.sparesparts.repositories.BrandRepository;
import com.sparesparts.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link BrandService}.
 * Provides the actual business logic for managing brands.
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Brand> findByName(String name) {
        return brandRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBrandById(Long id) {
        brandRepository.deleteById(id);
    }
}
