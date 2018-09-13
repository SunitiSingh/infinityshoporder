package com.is.order.base.repository;

import com.is.order.base.domain.CommerceOrderPriceNg;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommerceOrderPriceNg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceOrderPriceNgRepository extends JpaRepository<CommerceOrderPriceNg, Long> {

}
