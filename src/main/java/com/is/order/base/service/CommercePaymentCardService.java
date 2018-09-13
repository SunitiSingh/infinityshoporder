package com.is.order.base.service;

import com.is.order.base.service.dto.CommercePaymentCardDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CommercePaymentCard.
 */
public interface CommercePaymentCardService {

    /**
     * Save a commercePaymentCard.
     *
     * @param commercePaymentCardDTO the entity to save
     * @return the persisted entity
     */
    CommercePaymentCardDTO save(CommercePaymentCardDTO commercePaymentCardDTO);

    /**
     * Get all the commercePaymentCards.
     *
     * @return the list of entities
     */
    List<CommercePaymentCardDTO> findAll();


    /**
     * Get the "id" commercePaymentCard.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommercePaymentCardDTO> findOne(Long id);

    /**
     * Delete the "id" commercePaymentCard.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
