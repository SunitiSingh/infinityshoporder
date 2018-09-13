package com.is.order.base.repository;

import com.is.order.base.domain.ISOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ISOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISOrderRepository extends JpaRepository<ISOrder, Long> {

}
