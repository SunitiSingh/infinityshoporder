package com.is.order.base.service.impl;

import com.is.order.base.service.ISItemService;
import com.is.order.base.domain.ISItem;
import com.is.order.base.repository.ISItemRepository;
import com.is.order.base.service.dto.ISItemDTO;
import com.is.order.base.service.mapper.ISItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ISItem.
 */
@Service
@Transactional
public class ISItemServiceImpl implements ISItemService {

    private final Logger log = LoggerFactory.getLogger(ISItemServiceImpl.class);

    private final ISItemRepository iSItemRepository;

    private final ISItemMapper iSItemMapper;

    public ISItemServiceImpl(ISItemRepository iSItemRepository, ISItemMapper iSItemMapper) {
        this.iSItemRepository = iSItemRepository;
        this.iSItemMapper = iSItemMapper;
    }

    /**
     * Save a iSItem.
     *
     * @param iSItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ISItemDTO save(ISItemDTO iSItemDTO) {
        log.debug("Request to save ISItem : {}", iSItemDTO);
        ISItem iSItem = iSItemMapper.toEntity(iSItemDTO);
        iSItem = iSItemRepository.save(iSItem);
        return iSItemMapper.toDto(iSItem);
    }

    /**
     * Get all the iSItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ISItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ISItems");
        return iSItemRepository.findAll(pageable)
            .map(iSItemMapper::toDto);
    }

    /**
     * Get all the ISItem with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ISItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return iSItemRepository.findAllWithEagerRelationships(pageable).map(iSItemMapper::toDto);
    }
    

    /**
     * Get one iSItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ISItemDTO> findOne(Long id) {
        log.debug("Request to get ISItem : {}", id);
        return iSItemRepository.findOneWithEagerRelationships(id)
            .map(iSItemMapper::toDto);
    }

    /**
     * Delete the iSItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISItem : {}", id);
        iSItemRepository.deleteById(id);
    }
}
