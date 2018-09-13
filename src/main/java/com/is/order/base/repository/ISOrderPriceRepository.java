package com.is.order.base.repository;

import com.is.order.base.domain.ISOrderPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ISOrderPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISOrderPriceRepository extends JpaRepository<ISOrderPrice, Long> {

}
