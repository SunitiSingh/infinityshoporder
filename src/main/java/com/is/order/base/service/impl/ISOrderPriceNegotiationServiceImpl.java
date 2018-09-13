package com.is.order.base.service.impl;

import com.is.order.base.service.ISOrderPriceNegotiationService;
import com.is.order.base.domain.ISOrderPriceNegotiation;
import com.is.order.base.repository.ISOrderPriceNegotiationRepository;
import com.is.order.base.service.dto.ISOrderPriceNegotiationDTO;
import com.is.order.base.service.mapper.ISOrderPriceNegotiationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ISOrderPriceNegotiation.
 */
@Service
@Transactional
public class ISOrderPriceNegotiationServiceImpl implements ISOrderPriceNegotiationService {

    private final Logger log = LoggerFactory.getLogger(ISOrderPriceNegotiationServiceImpl.class);

    private final ISOrderPriceNegotiationRepository iSOrderPriceNegotiationRepository;

    private final ISOrderPriceNegotiationMapper iSOrderPriceNegotiationMapper;

    public ISOrderPriceNegotiationServiceImpl(ISOrderPriceNegotiationRepository iSOrderPriceNegotiationRepository, ISOrderPriceNegotiationMapper iSOrderPriceNegotiationMapper) {
        this.iSOrderPriceNegotiationRepository = iSOrderPriceNegotiationRepository;
        this.iSOrderPriceNegotiationMapper = iSOrderPriceNegotiationMapper;
    }

    /**
     * Save a iSOrderPriceNegotiation.
     *
     * @param iSOrderPriceNegotiationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ISOrderPriceNegotiationDTO save(ISOrderPriceNegotiationDTO iSOrderPriceNegotiationDTO) {
        log.debug("Request to save ISOrderPriceNegotiation : {}", iSOrderPriceNegotiationDTO);
        ISOrderPriceNegotiation iSOrderPriceNegotiation = iSOrderPriceNegotiationMapper.toEntity(iSOrderPriceNegotiationDTO);
        iSOrderPriceNegotiation = iSOrderPriceNegotiationRepository.save(iSOrderPriceNegotiation);
        return iSOrderPriceNegotiationMapper.toDto(iSOrderPriceNegotiation);
    }

    /**
     * Get all the iSOrderPriceNegotiations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ISOrderPriceNegotiationDTO> findAll() {
        log.debug("Request to get all ISOrderPriceNegotiations");
        return iSOrderPriceNegotiationRepository.findAll().stream()
            .map(iSOrderPriceNegotiationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one iSOrderPriceNegotiation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ISOrderPriceNegotiationDTO> findOne(Long id) {
        log.debug("Request to get ISOrderPriceNegotiation : {}", id);
        return iSOrderPriceNegotiationRepository.findById(id)
            .map(iSOrderPriceNegotiationMapper::toDto);
    }

    /**
     * Delete the iSOrderPriceNegotiation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISOrderPriceNegotiation : {}", id);
        iSOrderPriceNegotiationRepository.deleteById(id);
    }
}
