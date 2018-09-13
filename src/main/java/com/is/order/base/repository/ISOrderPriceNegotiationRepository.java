package com.is.order.base.repository;

import com.is.order.base.domain.ISOrderPriceNegotiation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ISOrderPriceNegotiation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISOrderPriceNegotiationRepository extends JpaRepository<ISOrderPriceNegotiation, Long> {

}
