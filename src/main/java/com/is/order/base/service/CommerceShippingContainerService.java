package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceShippingContainerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CommerceShippingContainer.
 */
public interface CommerceShippingContainerService {

    /**
     * Save a commerceShippingContainer.
     *
     * @param commerceShippingContainerDTO the entity to save
     * @return the persisted entity
     */
    CommerceShippingContainerDTO save(CommerceShippingContainerDTO commerceShippingContainerDTO);

    /**
     * Get all the commerceShippingContainers.
     *
     * @return the list of entities
     */
    List<CommerceShippingContainerDTO> findAll();


    /**
     * Get the "id" commerceShippingContainer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceShippingContainerDTO> findOne(Long id);

    /**
     * Delete the "id" commerceShippingContainer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
