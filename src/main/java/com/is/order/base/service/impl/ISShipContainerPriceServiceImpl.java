package com.is.order.base.service.impl;

import com.is.order.base.service.ISShipContainerPriceService;
import com.is.order.base.domain.ISShipContainerPrice;
import com.is.order.base.repository.ISShipContainerPriceRepository;
import com.is.order.base.service.dto.ISShipContainerPriceDTO;
import com.is.order.base.service.mapper.ISShipContainerPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ISShipContainerPrice.
 */
@Service
@Transactional
public class ISShipContainerPriceServiceImpl implements ISShipContainerPriceService {

    private final Logger log = LoggerFactory.getLogger(ISShipContainerPriceServiceImpl.class);

    private final ISShipContainerPriceRepository iSShipContainerPriceRepository;

    private final ISShipContainerPriceMapper iSShipContainerPriceMapper;

    public ISShipContainerPriceServiceImpl(ISShipContainerPriceRepository iSShipContainerPriceRepository, ISShipContainerPriceMapper iSShipContainerPriceMapper) {
        this.iSShipContainerPriceRepository = iSShipContainerPriceRepository;
        this.iSShipContainerPriceMapper = iSShipContainerPriceMapper;
    }

    /**
     * Save a iSShipContainerPrice.
     *
     * @param iSShipContainerPriceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ISShipContainerPriceDTO save(ISShipContainerPriceDTO iSShipContainerPriceDTO) {
        log.debug("Request to save ISShipContainerPrice : {}", iSShipContainerPriceDTO);
        ISShipContainerPrice iSShipContainerPrice = iSShipContainerPriceMapper.toEntity(iSShipContainerPriceDTO);
        iSShipContainerPrice = iSShipContainerPriceRepository.save(iSShipContainerPrice);
        return iSShipContainerPriceMapper.toDto(iSShipContainerPrice);
    }

    /**
     * Get all the iSShipContainerPrices.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ISShipContainerPriceDTO> findAll() {
        log.debug("Request to get all ISShipContainerPrices");
        return iSShipContainerPriceRepository.findAll().stream()
            .map(iSShipContainerPriceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one iSShipContainerPrice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ISShipContainerPriceDTO> findOne(Long id) {
        log.debug("Request to get ISShipContainerPrice : {}", id);
        return iSShipContainerPriceRepository.findById(id)
            .map(iSShipContainerPriceMapper::toDto);
    }

    /**
     * Delete the iSShipContainerPrice by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISShipContainerPrice : {}", id);
        iSShipContainerPriceRepository.deleteById(id);
    }
}
