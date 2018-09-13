package com.is.order.base.service.impl;

import com.is.order.base.service.ISItemPriceNegotiationService;
import com.is.order.base.domain.ISItemPriceNegotiation;
import com.is.order.base.repository.ISItemPriceNegotiationRepository;
import com.is.order.base.service.dto.ISItemPriceNegotiationDTO;
import com.is.order.base.service.mapper.ISItemPriceNegotiationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ISItemPriceNegotiation.
 */
@Service
@Transactional
public class ISItemPriceNegotiationServiceImpl implements ISItemPriceNegotiationService {

    private final Logger log = LoggerFactory.getLogger(ISItemPriceNegotiationServiceImpl.class);

    private final ISItemPriceNegotiationRepository iSItemPriceNegotiationRepository;

    private final ISItemPriceNegotiationMapper iSItemPriceNegotiationMapper;

    public ISItemPriceNegotiationServiceImpl(ISItemPriceNegotiationRepository iSItemPriceNegotiationRepository, ISItemPriceNegotiationMapper iSItemPriceNegotiationMapper) {
        this.iSItemPriceNegotiationRepository = iSItemPriceNegotiationRepository;
        this.iSItemPriceNegotiationMapper = iSItemPriceNegotiationMapper;
    }

    /**
     * Save a iSItemPriceNegotiation.
     *
     * @param iSItemPriceNegotiationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ISItemPriceNegotiationDTO save(ISItemPriceNegotiationDTO iSItemPriceNegotiationDTO) {
        log.debug("Request to save ISItemPriceNegotiation : {}", iSItemPriceNegotiationDTO);
        ISItemPriceNegotiation iSItemPriceNegotiation = iSItemPriceNegotiationMapper.toEntity(iSItemPriceNegotiationDTO);
        iSItemPriceNegotiation = iSItemPriceNegotiationRepository.save(iSItemPriceNegotiation);
        return iSItemPriceNegotiationMapper.toDto(iSItemPriceNegotiation);
    }

    /**
     * Get all the iSItemPriceNegotiations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ISItemPriceNegotiationDTO> findAll() {
        log.debug("Request to get all ISItemPriceNegotiations");
        return iSItemPriceNegotiationRepository.findAll().stream()
            .map(iSItemPriceNegotiationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one iSItemPriceNegotiation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ISItemPriceNegotiationDTO> findOne(Long id) {
        log.debug("Request to get ISItemPriceNegotiation : {}", id);
        return iSItemPriceNegotiationRepository.findById(id)
            .map(iSItemPriceNegotiationMapper::toDto);
    }

    /**
     * Delete the iSItemPriceNegotiation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISItemPriceNegotiation : {}", id);
        iSItemPriceNegotiationRepository.deleteById(id);
    }
}
