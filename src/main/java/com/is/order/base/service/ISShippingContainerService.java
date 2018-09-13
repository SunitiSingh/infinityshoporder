package com.is.order.base.service;

import com.is.order.base.service.dto.ISShippingContainerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ISShippingContainer.
 */
public interface ISShippingContainerService {

    /**
     * Save a iSShippingContainer.
     *
     * @param iSShippingContainerDTO the entity to save
     * @return the persisted entity
     */
    ISShippingContainerDTO save(ISShippingContainerDTO iSShippingContainerDTO);

    /**
     * Get all the iSShippingContainers.
     *
     * @return the list of entities
     */
    List<ISShippingContainerDTO> findAll();


    /**
     * Get the "id" iSShippingContainer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ISShippingContainerDTO> findOne(Long id);

    /**
     * Delete the "id" iSShippingContainer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
