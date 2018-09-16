package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceOrderService;
import com.is.order.base.domain.CommerceOrder;
import com.is.order.base.repository.CommerceOrderRepository;
import com.is.order.base.service.dto.CommerceOrderDTO;
import com.is.order.base.service.mapper.CommerceOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing CommerceOrder.
 */
@Service
@Transactional
public class CommerceOrderServiceImpl implements CommerceOrderService {

    private final Logger log = LoggerFactory.getLogger(CommerceOrderServiceImpl.class);

    private final CommerceOrderRepository commerceOrderRepository;

    private final CommerceOrderMapper commerceOrderMapper;

    public CommerceOrderServiceImpl(CommerceOrderRepository commerceOrderRepository, CommerceOrderMapper commerceOrderMapper) {
        this.commerceOrderRepository = commerceOrderRepository;
        this.commerceOrderMapper = commerceOrderMapper;
    }

    /**
     * Save a commerceOrder.
     *
     * @param commerceOrderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceOrderDTO save(CommerceOrderDTO commerceOrderDTO) {
        log.debug("Request to save CommerceOrder : {}", commerceOrderDTO);
        
        //Create default shipping payment and other basic entities
        
        CommerceOrder commerceOrder = commerceOrderMapper.toEntity(commerceOrderDTO);
        commerceOrder = commerceOrderRepository.save(commerceOrder);
        return commerceOrderMapper.toDto(commerceOrder);
    }

    /**
     * Get all the commerceOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CommerceOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommerceOrders");
        return commerceOrderRepository.findAll(pageable)
            .map(commerceOrderMapper::toDto);
    }


    /**
     * Get one commerceOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceOrderDTO> findOne(Long id) {
        log.debug("Request to get CommerceOrder : {}", id);
        return commerceOrderRepository.findById(id)
            .map(commerceOrderMapper::toDto);
    }

    /**
     * Delete the commerceOrder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceOrder : {}", id);
        commerceOrderRepository.deleteById(id);
    }
}
