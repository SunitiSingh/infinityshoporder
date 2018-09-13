package com.is.order.base.service;

import com.is.order.base.service.dto.ISOrderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ISOrder.
 */
public interface ISOrderService {

    /**
     * Save a iSOrder.
     *
     * @param iSOrderDTO the entity to save
     * @return the persisted entity
     */
    ISOrderDTO save(ISOrderDTO iSOrderDTO);

    /**
     * Get all the iSOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ISOrderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" iSOrder.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ISOrderDTO> findOne(Long id);

    /**
     * Delete the "id" iSOrder.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
