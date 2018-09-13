package com.is.order.base.service;

import com.is.order.base.service.dto.ISEPayDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ISEPay.
 */
public interface ISEPayService {

    /**
     * Save a iSEPay.
     *
     * @param iSEPayDTO the entity to save
     * @return the persisted entity
     */
    ISEPayDTO save(ISEPayDTO iSEPayDTO);

    /**
     * Get all the iSEPays.
     *
     * @return the list of entities
     */
    List<ISEPayDTO> findAll();


    /**
     * Get the "id" iSEPay.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ISEPayDTO> findOne(Long id);

    /**
     * Delete the "id" iSEPay.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
