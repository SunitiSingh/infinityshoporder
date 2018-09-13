package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceOrderPriceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CommerceOrderPrice.
 */
public interface CommerceOrderPriceService {

    /**
     * Save a commerceOrderPrice.
     *
     * @param commerceOrderPriceDTO the entity to save
     * @return the persisted entity
     */
    CommerceOrderPriceDTO save(CommerceOrderPriceDTO commerceOrderPriceDTO);

    /**
     * Get all the commerceOrderPrices.
     *
     * @return the list of entities
     */
    List<CommerceOrderPriceDTO> findAll();


    /**
     * Get the "id" commerceOrderPrice.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceOrderPriceDTO> findOne(Long id);

    /**
     * Delete the "id" commerceOrderPrice.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
