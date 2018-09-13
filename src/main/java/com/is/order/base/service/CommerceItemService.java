package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CommerceItem.
 */
public interface CommerceItemService {

    /**
     * Save a commerceItem.
     *
     * @param commerceItemDTO the entity to save
     * @return the persisted entity
     */
    CommerceItemDTO save(CommerceItemDTO commerceItemDTO);

    /**
     * Get all the commerceItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CommerceItemDTO> findAll(Pageable pageable);

    /**
     * Get all the CommerceItem with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<CommerceItemDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" commerceItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceItemDTO> findOne(Long id);

    /**
     * Delete the "id" commerceItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
