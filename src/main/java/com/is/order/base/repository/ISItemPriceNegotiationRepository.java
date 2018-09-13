package com.is.order.base.repository;

import com.is.order.base.domain.ISItemPriceNegotiation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ISItemPriceNegotiation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISItemPriceNegotiationRepository extends JpaRepository<ISItemPriceNegotiation, Long> {

}
