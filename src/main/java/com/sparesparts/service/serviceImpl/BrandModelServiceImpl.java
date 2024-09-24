package com.sparesparts.service.serviceImpl;

import com.sparesparts.entity.BrandModel;
import com.sparesparts.repositories.BrandModelRepository;
import com.sparesparts.service.BrandModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link BrandModelService}.
 * Provides the actual business logic for managing brand models.
 */
@Service
public class BrandModelServiceImpl implements BrandModelService {

    @Autowired
    private BrandModelRepository brandModelRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandModel createBrandModel(BrandModel brandModel) {
        return brandModelRepository.save(brandModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BrandModel> getBrandModelById(Long id) {
        return brandModelRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BrandModel> getAllBrandModels() {
        return brandModelRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BrandModel> findByName(String name) {
        return brandModelRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BrandModel> getBrandModelsByBrandId(Long brandId) {
        return brandModelRepository.findByBrandId(brandId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBrandModelById(Long id) {
        brandModelRepository.deleteById(id);
    }
}
