package com.sparesparts.service.serviceImpl;

import com.sparesparts.entity.Images;
import com.sparesparts.repositories.ImagesRepository;
import com.sparesparts.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImagesRepository imagesRepository;

    @Override
    public Images uploadImages(Images images) {
        return imagesRepository.save(images);
    }
}
