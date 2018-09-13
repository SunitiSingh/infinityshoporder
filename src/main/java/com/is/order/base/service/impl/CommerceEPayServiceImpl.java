package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceEPayService;
import com.is.order.base.domain.CommerceEPay;
import com.is.order.base.repository.CommerceEPayRepository;
import com.is.order.base.service.dto.CommerceEPayDTO;
import com.is.order.base.service.mapper.CommerceEPayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CommerceEPay.
 */
@Service
@Transactional
public class CommerceEPayServiceImpl implements CommerceEPayService {

    private final Logger log = LoggerFactory.getLogger(CommerceEPayServiceImpl.class);

    private final CommerceEPayRepository commerceEPayRepository;

    private final CommerceEPayMapper commerceEPayMapper;

    public CommerceEPayServiceImpl(CommerceEPayRepository commerceEPayRepository, CommerceEPayMapper commerceEPayMapper) {
        this.commerceEPayRepository = commerceEPayRepository;
        this.commerceEPayMapper = commerceEPayMapper;
    }

    /**
     * Save a commerceEPay.
     *
     * @param commerceEPayDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceEPayDTO save(CommerceEPayDTO commerceEPayDTO) {
        log.debug("Request to save CommerceEPay : {}", commerceEPayDTO);
        CommerceEPay commerceEPay = commerceEPayMapper.toEntity(commerceEPayDTO);
        commerceEPay = commerceEPayRepository.save(commerceEPay);
        return commerceEPayMapper.toDto(commerceEPay);
    }

    /**
     * Get all the commerceEPays.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommerceEPayDTO> findAll() {
        log.debug("Request to get all CommerceEPays");
        return commerceEPayRepository.findAll().stream()
            .map(commerceEPayMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commerceEPay by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceEPayDTO> findOne(Long id) {
        log.debug("Request to get CommerceEPay : {}", id);
        return commerceEPayRepository.findById(id)
            .map(commerceEPayMapper::toDto);
    }

    /**
     * Delete the commerceEPay by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceEPay : {}", id);
        commerceEPayRepository.deleteById(id);
    }
}
