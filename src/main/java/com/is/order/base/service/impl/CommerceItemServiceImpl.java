package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceItemService;
import com.is.order.base.domain.CommerceItem;
import com.is.order.base.repository.CommerceItemRepository;
import com.is.order.base.service.dto.CommerceItemDTO;
import com.is.order.base.service.mapper.CommerceItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing CommerceItem.
 */
@Service
@Transactional
public class CommerceItemServiceImpl implements CommerceItemService {

    private final Logger log = LoggerFactory.getLogger(CommerceItemServiceImpl.class);

    private final CommerceItemRepository commerceItemRepository;

    private final CommerceItemMapper commerceItemMapper;

    public CommerceItemServiceImpl(CommerceItemRepository commerceItemRepository, CommerceItemMapper commerceItemMapper) {
        this.commerceItemRepository = commerceItemRepository;
        this.commerceItemMapper = commerceItemMapper;
    }

    /**
     * Save a commerceItem.
     *
     * @param commerceItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceItemDTO save(CommerceItemDTO commerceItemDTO) {
        log.debug("Request to save CommerceItem : {}", commerceItemDTO);
        CommerceItem commerceItem = commerceItemMapper.toEntity(commerceItemDTO);
        commerceItem = commerceItemRepository.save(commerceItem);
        return commerceItemMapper.toDto(commerceItem);
    }

    /**
     * Get all the commerceItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CommerceItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommerceItems");
        return commerceItemRepository.findAll(pageable)
            .map(commerceItemMapper::toDto);
    }

    /**
     * Get all the CommerceItem with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<CommerceItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return commerceItemRepository.findAllWithEagerRelationships(pageable).map(commerceItemMapper::toDto);
    }
    

    /**
     * Get one commerceItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceItemDTO> findOne(Long id) {
        log.debug("Request to get CommerceItem : {}", id);
        return commerceItemRepository.findOneWithEagerRelationships(id)
            .map(commerceItemMapper::toDto);
    }

    /**
     * Delete the commerceItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceItem : {}", id);
        commerceItemRepository.deleteById(id);
    }
}
