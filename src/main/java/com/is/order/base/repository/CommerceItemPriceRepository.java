package com.is.order.base.repository;

import com.is.order.base.domain.CommerceItemPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommerceItemPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceItemPriceRepository extends JpaRepository<CommerceItemPrice, Long> {

}
