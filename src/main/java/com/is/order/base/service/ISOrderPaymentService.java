package com.is.order.base.service;

import com.is.order.base.service.dto.ISOrderPaymentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ISOrderPayment.
 */
public interface ISOrderPaymentService {

    /**
     * Save a iSOrderPayment.
     *
     * @param iSOrderPaymentDTO the entity to save
     * @return the persisted entity
     */
    ISOrderPaymentDTO save(ISOrderPaymentDTO iSOrderPaymentDTO);

    /**
     * Get all the iSOrderPayments.
     *
     * @return the list of entities
     */
    List<ISOrderPaymentDTO> findAll();


    /**
     * Get the "id" iSOrderPayment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ISOrderPaymentDTO> findOne(Long id);

    /**
     * Delete the "id" iSOrderPayment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
