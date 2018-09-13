package com.is.order.base.repository;

import com.is.order.base.domain.CommerceOrderPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommerceOrderPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceOrderPriceRepository extends JpaRepository<CommerceOrderPrice, Long> {

}
