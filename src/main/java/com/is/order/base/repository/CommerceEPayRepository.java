package com.is.order.base.repository;

import com.is.order.base.domain.CommerceEPay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommerceEPay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceEPayRepository extends JpaRepository<CommerceEPay, Long> {

}
