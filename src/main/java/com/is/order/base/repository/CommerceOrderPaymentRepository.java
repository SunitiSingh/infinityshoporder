package com.is.order.base.repository;

import com.is.order.base.domain.CommerceOrderPayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommerceOrderPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceOrderPaymentRepository extends JpaRepository<CommerceOrderPayment, Long> {

}
