package com.is.order.base.repository;

import com.is.order.base.domain.ISItemPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ISItemPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISItemPriceRepository extends JpaRepository<ISItemPrice, Long> {

}
