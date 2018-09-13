package com.is.order.base.repository;

import com.is.order.base.domain.ISOrderPayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ISOrderPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISOrderPaymentRepository extends JpaRepository<ISOrderPayment, Long> {

}
