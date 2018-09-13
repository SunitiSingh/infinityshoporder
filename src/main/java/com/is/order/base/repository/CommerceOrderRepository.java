package com.is.order.base.repository;

import com.is.order.base.domain.CommerceOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommerceOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceOrderRepository extends JpaRepository<CommerceOrder, Long> {

}
