package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceItemPriceNgDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CommerceItemPriceNg.
 */
public interface CommerceItemPriceNgService {

    /**
     * Save a commerceItemPriceNg.
     *
     * @param commerceItemPriceNgDTO the entity to save
     * @return the persisted entity
     */
    CommerceItemPriceNgDTO save(CommerceItemPriceNgDTO commerceItemPriceNgDTO);

    /**
     * Get all the commerceItemPriceNgs.
     *
     * @return the list of entities
     */
    List<CommerceItemPriceNgDTO> findAll();


    /**
     * Get the "id" commerceItemPriceNg.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceItemPriceNgDTO> findOne(Long id);

    /**
     * Delete the "id" commerceItemPriceNg.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
