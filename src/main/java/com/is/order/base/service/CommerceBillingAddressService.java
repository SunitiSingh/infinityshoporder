package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceBillingAddressDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CommerceBillingAddress.
 */
public interface CommerceBillingAddressService {

    /**
     * Save a commerceBillingAddress.
     *
     * @param commerceBillingAddressDTO the entity to save
     * @return the persisted entity
     */
    CommerceBillingAddressDTO save(CommerceBillingAddressDTO commerceBillingAddressDTO);

    /**
     * Get all the commerceBillingAddresses.
     *
     * @return the list of entities
     */
    List<CommerceBillingAddressDTO> findAll();


    /**
     * Get the "id" commerceBillingAddress.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceBillingAddressDTO> findOne(Long id);

    /**
     * Delete the "id" commerceBillingAddress.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
