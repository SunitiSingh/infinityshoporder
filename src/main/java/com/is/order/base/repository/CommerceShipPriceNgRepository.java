package com.is.order.base.repository;

import com.is.order.base.domain.CommerceShipPriceNg;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommerceShipPriceNg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceShipPriceNgRepository extends JpaRepository<CommerceShipPriceNg, Long> {

}
