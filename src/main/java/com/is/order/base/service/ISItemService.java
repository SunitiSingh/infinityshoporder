package com.is.order.base.service;

import com.is.order.base.service.dto.ISItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ISItem.
 */
public interface ISItemService {

    /**
     * Save a iSItem.
     *
     * @param iSItemDTO the entity to save
     * @return the persisted entity
     */
    ISItemDTO save(ISItemDTO iSItemDTO);

    /**
     * Get all the iSItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ISItemDTO> findAll(Pageable pageable);

    /**
     * Get all the ISItem with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ISItemDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" iSItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ISItemDTO> findOne(Long id);

    /**
     * Delete the "id" iSItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
