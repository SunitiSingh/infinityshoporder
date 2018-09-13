package com.is.order.base.repository;

import com.is.order.base.domain.CommercePaymentCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercePaymentCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercePaymentCardRepository extends JpaRepository<CommercePaymentCard, Long> {

}
