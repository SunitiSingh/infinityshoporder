package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceEPayDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CommerceEPay.
 */
public interface CommerceEPayService {

    /**
     * Save a commerceEPay.
     *
     * @param commerceEPayDTO the entity to save
     * @return the persisted entity
     */
    CommerceEPayDTO save(CommerceEPayDTO commerceEPayDTO);

    /**
     * Get all the commerceEPays.
     *
     * @return the list of entities
     */
    List<CommerceEPayDTO> findAll();


    /**
     * Get the "id" commerceEPay.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceEPayDTO> findOne(Long id);

    /**
     * Delete the "id" commerceEPay.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
