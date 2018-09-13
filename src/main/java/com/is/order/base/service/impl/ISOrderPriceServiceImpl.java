package com.is.order.base.service.impl;

import com.is.order.base.service.ISOrderPriceService;
import com.is.order.base.domain.ISOrderPrice;
import com.is.order.base.repository.ISOrderPriceRepository;
import com.is.order.base.service.dto.ISOrderPriceDTO;
import com.is.order.base.service.mapper.ISOrderPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ISOrderPrice.
 */
@Service
@Transactional
public class ISOrderPriceServiceImpl implements ISOrderPriceService {

    private final Logger log = LoggerFactory.getLogger(ISOrderPriceServiceImpl.class);

    private final ISOrderPriceRepository iSOrderPriceRepository;

    private final ISOrderPriceMapper iSOrderPriceMapper;

    public ISOrderPriceServiceImpl(ISOrderPriceRepository iSOrderPriceRepository, ISOrderPriceMapper iSOrderPriceMapper) {
        this.iSOrderPriceRepository = iSOrderPriceRepository;
        this.iSOrderPriceMapper = iSOrderPriceMapper;
    }

    /**
     * Save a iSOrderPrice.
     *
     * @param iSOrderPriceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ISOrderPriceDTO save(ISOrderPriceDTO iSOrderPriceDTO) {
        log.debug("Request to save ISOrderPrice : {}", iSOrderPriceDTO);
        ISOrderPrice iSOrderPrice = iSOrderPriceMapper.toEntity(iSOrderPriceDTO);
        iSOrderPrice = iSOrderPriceRepository.save(iSOrderPrice);
        return iSOrderPriceMapper.toDto(iSOrderPrice);
    }

    /**
     * Get all the iSOrderPrices.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ISOrderPriceDTO> findAll() {
        log.debug("Request to get all ISOrderPrices");
        return iSOrderPriceRepository.findAll().stream()
            .map(iSOrderPriceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one iSOrderPrice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ISOrderPriceDTO> findOne(Long id) {
        log.debug("Request to get ISOrderPrice : {}", id);
        return iSOrderPriceRepository.findById(id)
            .map(iSOrderPriceMapper::toDto);
    }

    /**
     * Delete the iSOrderPrice by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISOrderPrice : {}", id);
        iSOrderPriceRepository.deleteById(id);
    }
}
