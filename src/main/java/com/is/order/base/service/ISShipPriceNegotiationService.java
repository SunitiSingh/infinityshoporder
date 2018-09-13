package com.is.order.base.service;

import com.is.order.base.service.dto.ISShipPriceNegotiationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ISShipPriceNegotiation.
 */
public interface ISShipPriceNegotiationService {

    /**
     * Save a iSShipPriceNegotiation.
     *
     * @param iSShipPriceNegotiationDTO the entity to save
     * @return the persisted entity
     */
    ISShipPriceNegotiationDTO save(ISShipPriceNegotiationDTO iSShipPriceNegotiationDTO);

    /**
     * Get all the iSShipPriceNegotiations.
     *
     * @return the list of entities
     */
    List<ISShipPriceNegotiationDTO> findAll();


    /**
     * Get the "id" iSShipPriceNegotiation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ISShipPriceNegotiationDTO> findOne(Long id);

    /**
     * Delete the "id" iSShipPriceNegotiation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
