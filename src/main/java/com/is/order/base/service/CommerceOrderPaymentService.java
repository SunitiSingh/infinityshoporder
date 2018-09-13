package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceOrderPaymentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CommerceOrderPayment.
 */
public interface CommerceOrderPaymentService {

    /**
     * Save a commerceOrderPayment.
     *
     * @param commerceOrderPaymentDTO the entity to save
     * @return the persisted entity
     */
    CommerceOrderPaymentDTO save(CommerceOrderPaymentDTO commerceOrderPaymentDTO);

    /**
     * Get all the commerceOrderPayments.
     *
     * @return the list of entities
     */
    List<CommerceOrderPaymentDTO> findAll();


    /**
     * Get the "id" commerceOrderPayment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceOrderPaymentDTO> findOne(Long id);

    /**
     * Delete the "id" commerceOrderPayment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
