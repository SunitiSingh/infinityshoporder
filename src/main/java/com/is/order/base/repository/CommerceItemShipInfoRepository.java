package com.is.order.base.repository;

import com.is.order.base.domain.CommerceItemShipInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommerceItemShipInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceItemShipInfoRepository extends JpaRepository<CommerceItemShipInfo, Long> {

}
