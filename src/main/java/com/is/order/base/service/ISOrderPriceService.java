package com.is.order.base.service;

import com.is.order.base.service.dto.ISOrderPriceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ISOrderPrice.
 */
public interface ISOrderPriceService {

    /**
     * Save a iSOrderPrice.
     *
     * @param iSOrderPriceDTO the entity to save
     * @return the persisted entity
     */
    ISOrderPriceDTO save(ISOrderPriceDTO iSOrderPriceDTO);

    /**
     * Get all the iSOrderPrices.
     *
     * @return the list of entities
     */
    List<ISOrderPriceDTO> findAll();


    /**
     * Get the "id" iSOrderPrice.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ISOrderPriceDTO> findOne(Long id);

    /**
     * Delete the "id" iSOrderPrice.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
