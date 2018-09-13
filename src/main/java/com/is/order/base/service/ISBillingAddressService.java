package com.is.order.base.service;

import com.is.order.base.service.dto.ISBillingAddressDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ISBillingAddress.
 */
public interface ISBillingAddressService {

    /**
     * Save a iSBillingAddress.
     *
     * @param iSBillingAddressDTO the entity to save
     * @return the persisted entity
     */
    ISBillingAddressDTO save(ISBillingAddressDTO iSBillingAddressDTO);

    /**
     * Get all the iSBillingAddresses.
     *
     * @return the list of entities
     */
    List<ISBillingAddressDTO> findAll();


    /**
     * Get the "id" iSBillingAddress.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ISBillingAddressDTO> findOne(Long id);

    /**
     * Delete the "id" iSBillingAddress.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
