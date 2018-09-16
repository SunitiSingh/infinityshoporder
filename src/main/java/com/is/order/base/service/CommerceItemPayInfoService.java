package com.is.order.base.service;

import com.is.order.base.service.dto.CommerceItemPayInfoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CommerceItemPayInfo.
 */
public interface CommerceItemPayInfoService {

    /**
     * Save a commerceItemPayInfo.
     *
     * @param commerceItemPayInfoDTO the entity to save
     * @return the persisted entity
     */
    CommerceItemPayInfoDTO save(CommerceItemPayInfoDTO commerceItemPayInfoDTO);

    /**
     * Get all the commerceItemPayInfos.
     *
     * @return the list of entities
     */
    List<CommerceItemPayInfoDTO> findAll();


    /**
     * Get the "id" commerceItemPayInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CommerceItemPayInfoDTO> findOne(Long id);

    /**
     * Delete the "id" commerceItemPayInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
