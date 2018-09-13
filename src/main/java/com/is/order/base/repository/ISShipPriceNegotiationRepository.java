package com.is.order.base.repository;

import com.is.order.base.domain.ISShipPriceNegotiation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ISShipPriceNegotiation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISShipPriceNegotiationRepository extends JpaRepository<ISShipPriceNegotiation, Long> {

}
