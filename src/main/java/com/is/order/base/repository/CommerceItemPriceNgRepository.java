package com.is.order.base.repository;

import com.is.order.base.domain.CommerceItemPriceNg;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommerceItemPriceNg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceItemPriceNgRepository extends JpaRepository<CommerceItemPriceNg, Long> {

}
