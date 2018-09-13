package com.is.order.base.service.impl;

import com.is.order.base.service.ISEPayService;
import com.is.order.base.domain.ISEPay;
import com.is.order.base.repository.ISEPayRepository;
import com.is.order.base.service.dto.ISEPayDTO;
import com.is.order.base.service.mapper.ISEPayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ISEPay.
 */
@Service
@Transactional
public class ISEPayServiceImpl implements ISEPayService {

    private final Logger log = LoggerFactory.getLogger(ISEPayServiceImpl.class);

    private final ISEPayRepository iSEPayRepository;

    private final ISEPayMapper iSEPayMapper;

    public ISEPayServiceImpl(ISEPayRepository iSEPayRepository, ISEPayMapper iSEPayMapper) {
        this.iSEPayRepository = iSEPayRepository;
        this.iSEPayMapper = iSEPayMapper;
    }

    /**
     * Save a iSEPay.
     *
     * @param iSEPayDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ISEPayDTO save(ISEPayDTO iSEPayDTO) {
        log.debug("Request to save ISEPay : {}", iSEPayDTO);
        ISEPay iSEPay = iSEPayMapper.toEntity(iSEPayDTO);
        iSEPay = iSEPayRepository.save(iSEPay);
        return iSEPayMapper.toDto(iSEPay);
    }

    /**
     * Get all the iSEPays.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ISEPayDTO> findAll() {
        log.debug("Request to get all ISEPays");
        return iSEPayRepository.findAll().stream()
            .map(iSEPayMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one iSEPay by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ISEPayDTO> findOne(Long id) {
        log.debug("Request to get ISEPay : {}", id);
        return iSEPayRepository.findById(id)
            .map(iSEPayMapper::toDto);
    }

    /**
     * Delete the iSEPay by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISEPay : {}", id);
        iSEPayRepository.deleteById(id);
    }
}
