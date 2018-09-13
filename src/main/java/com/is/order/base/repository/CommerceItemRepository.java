package com.is.order.base.repository;

import com.is.order.base.domain.CommerceItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CommerceItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceItemRepository extends JpaRepository<CommerceItem, Long> {

    @Query(value = "select distinct commerce_item from CommerceItem commerce_item left join fetch commerce_item.shipcontainers left join fetch commerce_item.payments",
        countQuery = "select count(distinct commerce_item) from CommerceItem commerce_item")
    Page<CommerceItem> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct commerce_item from CommerceItem commerce_item left join fetch commerce_item.shipcontainers left join fetch commerce_item.payments")
    List<CommerceItem> findAllWithEagerRelationships();

    @Query("select commerce_item from CommerceItem commerce_item left join fetch commerce_item.shipcontainers left join fetch commerce_item.payments where commerce_item.id =:id")
    Optional<CommerceItem> findOneWithEagerRelationships(@Param("id") Long id);

}
