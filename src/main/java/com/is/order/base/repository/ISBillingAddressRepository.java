package com.is.order.base.repository;

import com.is.order.base.domain.ISBillingAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ISBillingAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISBillingAddressRepository extends JpaRepository<ISBillingAddress, Long> {

}
