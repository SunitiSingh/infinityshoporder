package com.is.order.base.service.impl;

import com.is.order.base.service.ISShipPriceNegotiationService;
import com.is.order.base.domain.ISShipPriceNegotiation;
import com.is.order.base.repository.ISShipPriceNegotiationRepository;
import com.is.order.base.service.dto.ISShipPriceNegotiationDTO;
import com.is.order.base.service.mapper.ISShipPriceNegotiationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ISShipPriceNegotiation.
 */
@Service
@Transactional
public class ISShipPriceNegotiationServiceImpl implements ISShipPriceNegotiationService {

    private final Logger log = LoggerFactory.getLogger(ISShipPriceNegotiationServiceImpl.class);

    private final ISShipPriceNegotiationRepository iSShipPriceNegotiationRepository;

    private final ISShipPriceNegotiationMapper iSShipPriceNegotiationMapper;

    public ISShipPriceNegotiationServiceImpl(ISShipPriceNegotiationRepository iSShipPriceNegotiationRepository, ISShipPriceNegotiationMapper iSShipPriceNegotiationMapper) {
        this.iSShipPriceNegotiationRepository = iSShipPriceNegotiationRepository;
        this.iSShipPriceNegotiationMapper = iSShipPriceNegotiationMapper;
    }

    /**
     * Save a iSShipPriceNegotiation.
     *
     * @param iSShipPriceNegotiationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ISShipPriceNegotiationDTO save(ISShipPriceNegotiationDTO iSShipPriceNegotiationDTO) {
        log.debug("Request to save ISShipPriceNegotiation : {}", iSShipPriceNegotiationDTO);
        ISShipPriceNegotiation iSShipPriceNegotiation = iSShipPriceNegotiationMapper.toEntity(iSShipPriceNegotiationDTO);
        iSShipPriceNegotiation = iSShipPriceNegotiationRepository.save(iSShipPriceNegotiation);
        return iSShipPriceNegotiationMapper.toDto(iSShipPriceNegotiation);
    }

    /**
     * Get all the iSShipPriceNegotiations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ISShipPriceNegotiationDTO> findAll() {
        log.debug("Request to get all ISShipPriceNegotiations");
        return iSShipPriceNegotiationRepository.findAll().stream()
            .map(iSShipPriceNegotiationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one iSShipPriceNegotiation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ISShipPriceNegotiationDTO> findOne(Long id) {
        log.debug("Request to get ISShipPriceNegotiation : {}", id);
        return iSShipPriceNegotiationRepository.findById(id)
            .map(iSShipPriceNegotiationMapper::toDto);
    }

    /**
     * Delete the iSShipPriceNegotiation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISShipPriceNegotiation : {}", id);
        iSShipPriceNegotiationRepository.deleteById(id);
    }
}
