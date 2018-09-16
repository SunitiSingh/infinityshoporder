package com.is.order.base.repository;

import com.is.order.base.domain.CommerceItemPayInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommerceItemPayInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceItemPayInfoRepository extends JpaRepository<CommerceItemPayInfo, Long> {

}
