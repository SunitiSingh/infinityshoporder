package com.is.order.base.repository;

import com.is.order.base.domain.CommerceBillingAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommerceBillingAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceBillingAddressRepository extends JpaRepository<CommerceBillingAddress, Long> {

}
