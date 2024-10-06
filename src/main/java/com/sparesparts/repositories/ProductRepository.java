package com.sparesparts.repositories;

import com.sparesparts.entity.Category;
import com.sparesparts.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for managing {@link Product} entities.
 * Provides methods for standard CRUD operations and custom queries for products.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    /**
     * Find products by name containing a search string (case insensitive).
     *
     * @param name The search string for the product name.
     * @return A list of products whose name contains the given string.
     */
    List<Product> findByNameContainingIgnoreCase(String name);


    /**
     * Find products by price within a specified range.
     *
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @return A list of products whose price is between minPrice and maxPrice.
     */
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findBySubCategoriesId(Long subCategoryId);

    @Query("SELECT p FROM Product p " +
            "JOIN p.brands b " +
            "JOIN p.brandModels bm " +
            "JOIN p.categories c " +
            "WHERE b.id = :brandId " +
            "AND bm.id = :brandModelId " +
            "AND c.id = :categoryId")
    List<Product> findByBrandIdBrandModelIdCategoryId(@Param("brandId") Long brandId,
                                                      @Param("brandModelId") Long brandModelId,
                                                      @Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p " +
            "JOIN p.brands b " +
            "JOIN p.brandModels bm " +
            "WHERE b.id = :brandId AND bm.id = :brandModelId")
    List<Product> findByBrandIdAndBrandModelId(@Param("brandId") Long brandId,
                                               @Param("brandModelId") Long brandModelId);

    @Query("SELECT p FROM Product p " +
            "JOIN p.brands b " +
            "JOIN p.categories c " +
            "WHERE b.id = :brandId AND c.id = :categoryId")
    List<Product> findByBrandIdAndCategoryId(@Param("brandId") Long brandId,
                                             @Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p " +
            "JOIN p.brandModels bm " +
            "JOIN p.categories c " +
            "WHERE bm.id = :brandModelId AND c.id = :categoryId")
    List<Product> findByBrandModelIdAndCategoryId(@Param("brandModelId") Long brandModelId,
                                                  @Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p " +
            "JOIN p.brands b " +
            "WHERE b.id = :brandId")
    List<Product> findByBrandId(@Param("brandId") Long brandId);

    @Query("SELECT p FROM Product p " +
            "JOIN p.brandModels bm " +
            "WHERE bm.id = :brandModelId")
    List<Product> findByBrandModelId(@Param("brandModelId") Long brandModelId);

    @Query("SELECT p FROM Product p " +
            "JOIN p.categories c " +
            "WHERE c.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    List<Product> findByStockQuantityLessThan(int i);

    @Query("SELECT p FROM Product p LEFT JOIN p.orderItems oi WHERE oi IS NULL")
    List<Product> findDeadProducts();

    List<Product> findByUpdatedAtAfter(LocalDateTime updatedAt, Sort sort);

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN p.categories c " +
            "LEFT JOIN p.subCategories sc " +
            "LEFT JOIN p.brands b " +
            "LEFT JOIN p.brandModels bm " +
            "WHERE lower(p.name) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(p.description) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(p.partNumber) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(p.material) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(p.mainImage) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(p.binLocation) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(c.name) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(sc.name) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(b.name) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(bm.name) LIKE lower(concat('%', :keyword, '%'))")
    List<Product> searchByKeyword(String keyword);

    List<Product> findTop18ByOrderByIdAsc(); // Method to get top 18 products ordered by ID

    Page<Product> findAll(Pageable pageable);


}
