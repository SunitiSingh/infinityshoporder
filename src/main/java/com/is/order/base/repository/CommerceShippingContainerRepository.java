package com.is.order.base.repository;

import com.is.order.base.domain.CommerceShippingContainer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommerceShippingContainer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceShippingContainerRepository extends JpaRepository<CommerceShippingContainer, Long> {

}
