package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceOrderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CommerceOrder.
 */
public interface CommerceOrderService {

    /**
     * Save a commerceOrder.
     *
     * @param commerceOrderDTO the entity to save
     * @return the persisted entity
     */
    CommerceOrderDTO save(CommerceOrderDTO commerceOrderDTO);

    /**
     * Get all the commerceOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CommerceOrderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" commerceOrder.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceOrderDTO> findOne(Long id);

    /**
     * Delete the "id" commerceOrder.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
