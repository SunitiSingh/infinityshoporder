package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceShippingContainerService;
import com.is.order.base.domain.CommerceShippingContainer;
import com.is.order.base.repository.CommerceShippingContainerRepository;
import com.is.order.base.service.dto.CommerceShippingContainerDTO;
import com.is.order.base.service.mapper.CommerceShippingContainerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CommerceShippingContainer.
 */
@Service
@Transactional
public class CommerceShippingContainerServiceImpl implements CommerceShippingContainerService {

    private final Logger log = LoggerFactory.getLogger(CommerceShippingContainerServiceImpl.class);

    private final CommerceShippingContainerRepository commerceShippingContainerRepository;

    private final CommerceShippingContainerMapper commerceShippingContainerMapper;

    public CommerceShippingContainerServiceImpl(CommerceShippingContainerRepository commerceShippingContainerRepository, CommerceShippingContainerMapper commerceShippingContainerMapper) {
        this.commerceShippingContainerRepository = commerceShippingContainerRepository;
        this.commerceShippingContainerMapper = commerceShippingContainerMapper;
    }

    /**
     * Save a commerceShippingContainer.
     *
     * @param commerceShippingContainerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceShippingContainerDTO save(CommerceShippingContainerDTO commerceShippingContainerDTO) {
        log.debug("Request to save CommerceShippingContainer : {}", commerceShippingContainerDTO);
        CommerceShippingContainer commerceShippingContainer = commerceShippingContainerMapper.toEntity(commerceShippingContainerDTO);
        commerceShippingContainer = commerceShippingContainerRepository.save(commerceShippingContainer);
        return commerceShippingContainerMapper.toDto(commerceShippingContainer);
    }

    /**
     * Get all the commerceShippingContainers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommerceShippingContainerDTO> findAll() {
        log.debug("Request to get all CommerceShippingContainers");
        return commerceShippingContainerRepository.findAll().stream()
            .map(commerceShippingContainerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commerceShippingContainer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceShippingContainerDTO> findOne(Long id) {
        log.debug("Request to get CommerceShippingContainer : {}", id);
        return commerceShippingContainerRepository.findById(id)
            .map(commerceShippingContainerMapper::toDto);
    }

    /**
     * Delete the commerceShippingContainer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceShippingContainer : {}", id);
        commerceShippingContainerRepository.deleteById(id);
    }
}
