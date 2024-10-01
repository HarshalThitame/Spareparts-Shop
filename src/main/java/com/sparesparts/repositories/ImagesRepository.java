package com.sparesparts.repositories;

import com.sparesparts.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {
    List<Images> findByProductId(Long product_id);
}
