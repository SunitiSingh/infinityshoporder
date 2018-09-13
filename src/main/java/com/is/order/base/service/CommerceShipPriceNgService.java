package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceShipPriceNgDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CommerceShipPriceNg.
 */
public interface CommerceShipPriceNgService {

    /**
     * Save a commerceShipPriceNg.
     *
     * @param commerceShipPriceNgDTO the entity to save
     * @return the persisted entity
     */
    CommerceShipPriceNgDTO save(CommerceShipPriceNgDTO commerceShipPriceNgDTO);

    /**
     * Get all the commerceShipPriceNgs.
     *
     * @return the list of entities
     */
    List<CommerceShipPriceNgDTO> findAll();


    /**
     * Get the "id" commerceShipPriceNg.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceShipPriceNgDTO> findOne(Long id);

    /**
     * Delete the "id" commerceShipPriceNg.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
