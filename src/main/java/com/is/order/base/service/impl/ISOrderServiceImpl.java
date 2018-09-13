package com.is.order.base.service.impl;

import com.is.order.base.service.ISOrderService;
import com.is.order.base.domain.ISOrder;
import com.is.order.base.repository.ISOrderRepository;
import com.is.order.base.service.dto.ISOrderDTO;
import com.is.order.base.service.mapper.ISOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ISOrder.
 */
@Service
@Transactional
public class ISOrderServiceImpl implements ISOrderService {

    private final Logger log = LoggerFactory.getLogger(ISOrderServiceImpl.class);

    private final ISOrderRepository iSOrderRepository;

    private final ISOrderMapper iSOrderMapper;

    public ISOrderServiceImpl(ISOrderRepository iSOrderRepository, ISOrderMapper iSOrderMapper) {
        this.iSOrderRepository = iSOrderRepository;
        this.iSOrderMapper = iSOrderMapper;
    }

    /**
     * Save a iSOrder.
     *
     * @param iSOrderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ISOrderDTO save(ISOrderDTO iSOrderDTO) {
        log.debug("Request to save ISOrder : {}", iSOrderDTO);
        ISOrder iSOrder = iSOrderMapper.toEntity(iSOrderDTO);
        iSOrder = iSOrderRepository.save(iSOrder);
        return iSOrderMapper.toDto(iSOrder);
    }

    /**
     * Get all the iSOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ISOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ISOrders");
        return iSOrderRepository.findAll(pageable)
            .map(iSOrderMapper::toDto);
    }


    /**
     * Get one iSOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ISOrderDTO> findOne(Long id) {
        log.debug("Request to get ISOrder : {}", id);
        return iSOrderRepository.findById(id)
            .map(iSOrderMapper::toDto);
    }

    /**
     * Delete the iSOrder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISOrder : {}", id);
        iSOrderRepository.deleteById(id);
    }
}
