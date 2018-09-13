package com.is.order.base.service;

import com.is.order.base.service.dto.ISItemPriceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ISItemPrice.
 */
public interface ISItemPriceService {

    /**
     * Save a iSItemPrice.
     *
     * @param iSItemPriceDTO the entity to save
     * @return the persisted entity
     */
    ISItemPriceDTO save(ISItemPriceDTO iSItemPriceDTO);

    /**
     * Get all the iSItemPrices.
     *
     * @return the list of entities
     */
    List<ISItemPriceDTO> findAll();


    /**
     * Get the "id" iSItemPrice.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ISItemPriceDTO> findOne(Long id);

    /**
     * Delete the "id" iSItemPrice.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
