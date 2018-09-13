package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceShipContainerPriceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CommerceShipContainerPrice.
 */
public interface CommerceShipContainerPriceService {

    /**
     * Save a commerceShipContainerPrice.
     *
     * @param commerceShipContainerPriceDTO the entity to save
     * @return the persisted entity
     */
    CommerceShipContainerPriceDTO save(CommerceShipContainerPriceDTO commerceShipContainerPriceDTO);

    /**
     * Get all the commerceShipContainerPrices.
     *
     * @return the list of entities
     */
    List<CommerceShipContainerPriceDTO> findAll();


    /**
     * Get the "id" commerceShipContainerPrice.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceShipContainerPriceDTO> findOne(Long id);

    /**
     * Delete the "id" commerceShipContainerPrice.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
