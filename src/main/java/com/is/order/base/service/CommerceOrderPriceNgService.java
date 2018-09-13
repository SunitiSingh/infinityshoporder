package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceOrderPriceNgDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CommerceOrderPriceNg.
 */
public interface CommerceOrderPriceNgService {

    /**
     * Save a commerceOrderPriceNg.
     *
     * @param commerceOrderPriceNgDTO the entity to save
     * @return the persisted entity
     */
    CommerceOrderPriceNgDTO save(CommerceOrderPriceNgDTO commerceOrderPriceNgDTO);

    /**
     * Get all the commerceOrderPriceNgs.
     *
     * @return the list of entities
     */
    List<CommerceOrderPriceNgDTO> findAll();


    /**
     * Get the "id" commerceOrderPriceNg.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceOrderPriceNgDTO> findOne(Long id);

    /**
     * Delete the "id" commerceOrderPriceNg.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
