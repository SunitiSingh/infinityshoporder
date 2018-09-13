package com.is.order.base.service;

import com.is.order.base.service.dto.ISItemPriceNegotiationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ISItemPriceNegotiation.
 */
public interface ISItemPriceNegotiationService {

    /**
     * Save a iSItemPriceNegotiation.
     *
     * @param iSItemPriceNegotiationDTO the entity to save
     * @return the persisted entity
     */
    ISItemPriceNegotiationDTO save(ISItemPriceNegotiationDTO iSItemPriceNegotiationDTO);

    /**
     * Get all the iSItemPriceNegotiations.
     *
     * @return the list of entities
     */
    List<ISItemPriceNegotiationDTO> findAll();


    /**
     * Get the "id" iSItemPriceNegotiation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ISItemPriceNegotiationDTO> findOne(Long id);

    /**
     * Delete the "id" iSItemPriceNegotiation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
