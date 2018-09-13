package com.is.order.base.service;

import com.is.order.base.service.dto.ISShipContainerPriceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ISShipContainerPrice.
 */
public interface ISShipContainerPriceService {

    /**
     * Save a iSShipContainerPrice.
     *
     * @param iSShipContainerPriceDTO the entity to save
     * @return the persisted entity
     */
    ISShipContainerPriceDTO save(ISShipContainerPriceDTO iSShipContainerPriceDTO);

    /**
     * Get all the iSShipContainerPrices.
     *
     * @return the list of entities
     */
    List<ISShipContainerPriceDTO> findAll();


    /**
     * Get the "id" iSShipContainerPrice.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ISShipContainerPriceDTO> findOne(Long id);

    /**
     * Delete the "id" iSShipContainerPrice.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
