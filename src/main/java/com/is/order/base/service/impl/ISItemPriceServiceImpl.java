package com.is.order.base.service.impl;

import com.is.order.base.service.ISItemPriceService;
import com.is.order.base.domain.ISItemPrice;
import com.is.order.base.repository.ISItemPriceRepository;
import com.is.order.base.service.dto.ISItemPriceDTO;
import com.is.order.base.service.mapper.ISItemPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ISItemPrice.
 */
@Service
@Transactional
public class ISItemPriceServiceImpl implements ISItemPriceService {

    private final Logger log = LoggerFactory.getLogger(ISItemPriceServiceImpl.class);

    private final ISItemPriceRepository iSItemPriceRepository;

    private final ISItemPriceMapper iSItemPriceMapper;

    public ISItemPriceServiceImpl(ISItemPriceRepository iSItemPriceRepository, ISItemPriceMapper iSItemPriceMapper) {
        this.iSItemPriceRepository = iSItemPriceRepository;
        this.iSItemPriceMapper = iSItemPriceMapper;
    }

    /**
     * Save a iSItemPrice.
     *
     * @param iSItemPriceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ISItemPriceDTO save(ISItemPriceDTO iSItemPriceDTO) {
        log.debug("Request to save ISItemPrice : {}", iSItemPriceDTO);
        ISItemPrice iSItemPrice = iSItemPriceMapper.toEntity(iSItemPriceDTO);
        iSItemPrice = iSItemPriceRepository.save(iSItemPrice);
        return iSItemPriceMapper.toDto(iSItemPrice);
    }

    /**
     * Get all the iSItemPrices.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ISItemPriceDTO> findAll() {
        log.debug("Request to get all ISItemPrices");
        return iSItemPriceRepository.findAll().stream()
            .map(iSItemPriceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one iSItemPrice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ISItemPriceDTO> findOne(Long id) {
        log.debug("Request to get ISItemPrice : {}", id);
        return iSItemPriceRepository.findById(id)
            .map(iSItemPriceMapper::toDto);
    }

    /**
     * Delete the iSItemPrice by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISItemPrice : {}", id);
        iSItemPriceRepository.deleteById(id);
    }
}
