package com.is.order.base.repository;

import com.is.order.base.domain.ISShippingContainer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ISShippingContainer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISShippingContainerRepository extends JpaRepository<ISShippingContainer, Long> {

}
