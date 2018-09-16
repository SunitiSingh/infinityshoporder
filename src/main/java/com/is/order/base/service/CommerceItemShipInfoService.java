package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceItemShipInfoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CommerceItemShipInfo.
 */
public interface CommerceItemShipInfoService {

    /**
     * Save a commerceItemShipInfo.
     *
     * @param commerceItemShipInfoDTO the entity to save
     * @return the persisted entity
     */
    CommerceItemShipInfoDTO save(CommerceItemShipInfoDTO commerceItemShipInfoDTO);

    /**
     * Get all the commerceItemShipInfos.
     *
     * @return the list of entities
     */
    List<CommerceItemShipInfoDTO> findAll();


    /**
     * Get the "id" commerceItemShipInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceItemShipInfoDTO> findOne(Long id);

    /**
     * Delete the "id" commerceItemShipInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
