package com.is.order.base.repository;

import com.is.order.base.domain.ISPaymentCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ISPaymentCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISPaymentCardRepository extends JpaRepository<ISPaymentCard, Long> {

}
