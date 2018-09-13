package com.is.order.base.repository;

import com.is.order.base.domain.CommerceShipContainerPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommerceShipContainerPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceShipContainerPriceRepository extends JpaRepository<CommerceShipContainerPrice, Long> {

}
