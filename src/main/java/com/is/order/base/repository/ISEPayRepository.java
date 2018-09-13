package com.is.order.base.repository;

import com.is.order.base.domain.ISEPay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ISEPay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISEPayRepository extends JpaRepository<ISEPay, Long> {

}
