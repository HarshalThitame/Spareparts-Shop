package com.sparesparts.repository;

import com.sparesparts.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    /**
     * Finds all offers that are valid based on the current time.
     * The query checks if the start date is before or equal to the current time
     * and if the end date is after or equal to the current time.
     *
     * @param startDate The current time to compare with the offer's start date.
     * @param endDate The current time to compare with the offer's end date.
     * @return List of offers that are active.
     */
    List<Offer> findByStartDateBeforeAndEndDateAfter(LocalDateTime startDate, LocalDateTime endDate);
}
