package com.is.order.base.service;

import com.is.order.base.service.dto.ISPaymentCardDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ISPaymentCard.
 */
public interface ISPaymentCardService {

    /**
     * Save a iSPaymentCard.
     *
     * @param iSPaymentCardDTO the entity to save
     * @return the persisted entity
     */
    ISPaymentCardDTO save(ISPaymentCardDTO iSPaymentCardDTO);

    /**
     * Get all the iSPaymentCards.
     *
     * @return the list of entities
     */
    List<ISPaymentCardDTO> findAll();


    /**
     * Get the "id" iSPaymentCard.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ISPaymentCardDTO> findOne(Long id);

    /**
     * Delete the "id" iSPaymentCard.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
