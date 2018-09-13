package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceOrderPriceNgService;
import com.is.order.base.domain.CommerceOrderPriceNg;
import com.is.order.base.repository.CommerceOrderPriceNgRepository;
import com.is.order.base.service.dto.CommerceOrderPriceNgDTO;
import com.is.order.base.service.mapper.CommerceOrderPriceNgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CommerceOrderPriceNg.
 */
@Service
@Transactional
public class CommerceOrderPriceNgServiceImpl implements CommerceOrderPriceNgService {

    private final Logger log = LoggerFactory.getLogger(CommerceOrderPriceNgServiceImpl.class);

    private final CommerceOrderPriceNgRepository commerceOrderPriceNgRepository;

    private final CommerceOrderPriceNgMapper commerceOrderPriceNgMapper;

    public CommerceOrderPriceNgServiceImpl(CommerceOrderPriceNgRepository commerceOrderPriceNgRepository, CommerceOrderPriceNgMapper commerceOrderPriceNgMapper) {
        this.commerceOrderPriceNgRepository = commerceOrderPriceNgRepository;
        this.commerceOrderPriceNgMapper = commerceOrderPriceNgMapper;
    }

    /**
     * Save a commerceOrderPriceNg.
     *
     * @param commerceOrderPriceNgDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceOrderPriceNgDTO save(CommerceOrderPriceNgDTO commerceOrderPriceNgDTO) {
        log.debug("Request to save CommerceOrderPriceNg : {}", commerceOrderPriceNgDTO);
        CommerceOrderPriceNg commerceOrderPriceNg = commerceOrderPriceNgMapper.toEntity(commerceOrderPriceNgDTO);
        commerceOrderPriceNg = commerceOrderPriceNgRepository.save(commerceOrderPriceNg);
        return commerceOrderPriceNgMapper.toDto(commerceOrderPriceNg);
    }

    /**
     * Get all the commerceOrderPriceNgs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommerceOrderPriceNgDTO> findAll() {
        log.debug("Request to get all CommerceOrderPriceNgs");
        return commerceOrderPriceNgRepository.findAll().stream()
            .map(commerceOrderPriceNgMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commerceOrderPriceNg by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceOrderPriceNgDTO> findOne(Long id) {
        log.debug("Request to get CommerceOrderPriceNg : {}", id);
        return commerceOrderPriceNgRepository.findById(id)
            .map(commerceOrderPriceNgMapper::toDto);
    }

    /**
     * Delete the commerceOrderPriceNg by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceOrderPriceNg : {}", id);
        commerceOrderPriceNgRepository.deleteById(id);
    }
}
