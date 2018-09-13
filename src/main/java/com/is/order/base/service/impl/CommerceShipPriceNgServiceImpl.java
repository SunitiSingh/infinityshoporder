package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceShipPriceNgService;
import com.is.order.base.domain.CommerceShipPriceNg;
import com.is.order.base.repository.CommerceShipPriceNgRepository;
import com.is.order.base.service.dto.CommerceShipPriceNgDTO;
import com.is.order.base.service.mapper.CommerceShipPriceNgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CommerceShipPriceNg.
 */
@Service
@Transactional
public class CommerceShipPriceNgServiceImpl implements CommerceShipPriceNgService {

    private final Logger log = LoggerFactory.getLogger(CommerceShipPriceNgServiceImpl.class);

    private final CommerceShipPriceNgRepository commerceShipPriceNgRepository;

    private final CommerceShipPriceNgMapper commerceShipPriceNgMapper;

    public CommerceShipPriceNgServiceImpl(CommerceShipPriceNgRepository commerceShipPriceNgRepository, CommerceShipPriceNgMapper commerceShipPriceNgMapper) {
        this.commerceShipPriceNgRepository = commerceShipPriceNgRepository;
        this.commerceShipPriceNgMapper = commerceShipPriceNgMapper;
    }

    /**
     * Save a commerceShipPriceNg.
     *
     * @param commerceShipPriceNgDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceShipPriceNgDTO save(CommerceShipPriceNgDTO commerceShipPriceNgDTO) {
        log.debug("Request to save CommerceShipPriceNg : {}", commerceShipPriceNgDTO);
        CommerceShipPriceNg commerceShipPriceNg = commerceShipPriceNgMapper.toEntity(commerceShipPriceNgDTO);
        commerceShipPriceNg = commerceShipPriceNgRepository.save(commerceShipPriceNg);
        return commerceShipPriceNgMapper.toDto(commerceShipPriceNg);
    }

    /**
     * Get all the commerceShipPriceNgs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommerceShipPriceNgDTO> findAll() {
        log.debug("Request to get all CommerceShipPriceNgs");
        return commerceShipPriceNgRepository.findAll().stream()
            .map(commerceShipPriceNgMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commerceShipPriceNg by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceShipPriceNgDTO> findOne(Long id) {
        log.debug("Request to get CommerceShipPriceNg : {}", id);
        return commerceShipPriceNgRepository.findById(id)
            .map(commerceShipPriceNgMapper::toDto);
    }

    /**
     * Delete the commerceShipPriceNg by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceShipPriceNg : {}", id);
        commerceShipPriceNgRepository.deleteById(id);
    }
}
