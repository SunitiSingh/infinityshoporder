package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceItemPriceNgService;
import com.is.order.base.domain.CommerceItemPriceNg;
import com.is.order.base.repository.CommerceItemPriceNgRepository;
import com.is.order.base.service.dto.CommerceItemPriceNgDTO;
import com.is.order.base.service.mapper.CommerceItemPriceNgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CommerceItemPriceNg.
 */
@Service
@Transactional
public class CommerceItemPriceNgServiceImpl implements CommerceItemPriceNgService {

    private final Logger log = LoggerFactory.getLogger(CommerceItemPriceNgServiceImpl.class);

    private final CommerceItemPriceNgRepository commerceItemPriceNgRepository;

    private final CommerceItemPriceNgMapper commerceItemPriceNgMapper;

    public CommerceItemPriceNgServiceImpl(CommerceItemPriceNgRepository commerceItemPriceNgRepository, CommerceItemPriceNgMapper commerceItemPriceNgMapper) {
        this.commerceItemPriceNgRepository = commerceItemPriceNgRepository;
        this.commerceItemPriceNgMapper = commerceItemPriceNgMapper;
    }

    /**
     * Save a commerceItemPriceNg.
     *
     * @param commerceItemPriceNgDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceItemPriceNgDTO save(CommerceItemPriceNgDTO commerceItemPriceNgDTO) {
        log.debug("Request to save CommerceItemPriceNg : {}", commerceItemPriceNgDTO);
        CommerceItemPriceNg commerceItemPriceNg = commerceItemPriceNgMapper.toEntity(commerceItemPriceNgDTO);
        commerceItemPriceNg = commerceItemPriceNgRepository.save(commerceItemPriceNg);
        return commerceItemPriceNgMapper.toDto(commerceItemPriceNg);
    }

    /**
     * Get all the commerceItemPriceNgs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommerceItemPriceNgDTO> findAll() {
        log.debug("Request to get all CommerceItemPriceNgs");
        return commerceItemPriceNgRepository.findAll().stream()
            .map(commerceItemPriceNgMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commerceItemPriceNg by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceItemPriceNgDTO> findOne(Long id) {
        log.debug("Request to get CommerceItemPriceNg : {}", id);
        return commerceItemPriceNgRepository.findById(id)
            .map(commerceItemPriceNgMapper::toDto);
    }

    /**
     * Delete the commerceItemPriceNg by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceItemPriceNg : {}", id);
        commerceItemPriceNgRepository.deleteById(id);
    }
}
