package com.is.order.base.repository;

import com.is.order.base.domain.ISShipContainerPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ISShipContainerPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISShipContainerPriceRepository extends JpaRepository<ISShipContainerPrice, Long> {

}
