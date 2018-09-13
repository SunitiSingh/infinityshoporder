package com.is.order.base.service.impl;

import com.is.order.base.service.ISShippingContainerService;
import com.is.order.base.domain.ISShippingContainer;
import com.is.order.base.repository.ISShippingContainerRepository;
import com.is.order.base.service.dto.ISShippingContainerDTO;
import com.is.order.base.service.mapper.ISShippingContainerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ISShippingContainer.
 */
@Service
@Transactional
public class ISShippingContainerServiceImpl implements ISShippingContainerService {

    private final Logger log = LoggerFactory.getLogger(ISShippingContainerServiceImpl.class);

    private final ISShippingContainerRepository iSShippingContainerRepository;

    private final ISShippingContainerMapper iSShippingContainerMapper;

    public ISShippingContainerServiceImpl(ISShippingContainerRepository iSShippingContainerRepository, ISShippingContainerMapper iSShippingContainerMapper) {
        this.iSShippingContainerRepository = iSShippingContainerRepository;
        this.iSShippingContainerMapper = iSShippingContainerMapper;
    }

    /**
     * Save a iSShippingContainer.
     *
     * @param iSShippingContainerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ISShippingContainerDTO save(ISShippingContainerDTO iSShippingContainerDTO) {
        log.debug("Request to save ISShippingContainer : {}", iSShippingContainerDTO);
        ISShippingContainer iSShippingContainer = iSShippingContainerMapper.toEntity(iSShippingContainerDTO);
        iSShippingContainer = iSShippingContainerRepository.save(iSShippingContainer);
        return iSShippingContainerMapper.toDto(iSShippingContainer);
    }

    /**
     * Get all the iSShippingContainers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ISShippingContainerDTO> findAll() {
        log.debug("Request to get all ISShippingContainers");
        return iSShippingContainerRepository.findAll().stream()
            .map(iSShippingContainerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one iSShippingContainer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ISShippingContainerDTO> findOne(Long id) {
        log.debug("Request to get ISShippingContainer : {}", id);
        return iSShippingContainerRepository.findById(id)
            .map(iSShippingContainerMapper::toDto);
    }

    /**
     * Delete the iSShippingContainer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISShippingContainer : {}", id);
        iSShippingContainerRepository.deleteById(id);
    }
}
