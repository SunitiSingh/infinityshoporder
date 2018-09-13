package com.is.order.base.repository;

import com.is.order.base.domain.ISItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ISItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISItemRepository extends JpaRepository<ISItem, Long> {

    @Query(value = "select distinct is_item from ISItem is_item left join fetch is_item.shipcontainers left join fetch is_item.payments",
        countQuery = "select count(distinct is_item) from ISItem is_item")
    Page<ISItem> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct is_item from ISItem is_item left join fetch is_item.shipcontainers left join fetch is_item.payments")
    List<ISItem> findAllWithEagerRelationships();

    @Query("select is_item from ISItem is_item left join fetch is_item.shipcontainers left join fetch is_item.payments where is_item.id =:id")
    Optional<ISItem> findOneWithEagerRelationships(@Param("id") Long id);

}
