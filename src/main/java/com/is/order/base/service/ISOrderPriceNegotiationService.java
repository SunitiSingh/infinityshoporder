package com.is.order.base.service;

import com.is.order.base.service.dto.ISOrderPriceNegotiationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ISOrderPriceNegotiation.
 */
public interface ISOrderPriceNegotiationService {

    /**
     * Save a iSOrderPriceNegotiation.
     *
     * @param iSOrderPriceNegotiationDTO the entity to save
     * @return the persisted entity
     */
    ISOrderPriceNegotiationDTO save(ISOrderPriceNegotiationDTO iSOrderPriceNegotiationDTO);

    /**
     * Get all the iSOrderPriceNegotiations.
     *
     * @return the list of entities
     */
    List<ISOrderPriceNegotiationDTO> findAll();


    /**
     * Get the "id" iSOrderPriceNegotiation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ISOrderPriceNegotiationDTO> findOne(Long id);

    /**
     * Delete the "id" iSOrderPriceNegotiation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
