package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceItemPriceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CommerceItemPrice.
 */
public interface CommerceItemPriceService {

    /**
     * Save a commerceItemPrice.
     *
     * @param commerceItemPriceDTO the entity to save
     * @return the persisted entity
     */
    CommerceItemPriceDTO save(CommerceItemPriceDTO commerceItemPriceDTO);

    /**
     * Get all the commerceItemPrices.
     *
     * @return the list of entities
     */
    List<CommerceItemPriceDTO> findAll();


    /**
     * Get the "id" commerceItemPrice.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceItemPriceDTO> findOne(Long id);

    /**
     * Delete the "id" commerceItemPrice.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
