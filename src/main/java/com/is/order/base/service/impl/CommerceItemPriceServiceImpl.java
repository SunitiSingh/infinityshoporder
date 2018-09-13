package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceItemPriceService;
import com.is.order.base.domain.CommerceItemPrice;
import com.is.order.base.repository.CommerceItemPriceRepository;
import com.is.order.base.service.dto.CommerceItemPriceDTO;
import com.is.order.base.service.mapper.CommerceItemPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CommerceItemPrice.
 */
@Service
@Transactional
public class CommerceItemPriceServiceImpl implements CommerceItemPriceService {

    private final Logger log = LoggerFactory.getLogger(CommerceItemPriceServiceImpl.class);

    private final CommerceItemPriceRepository commerceItemPriceRepository;

    private final CommerceItemPriceMapper commerceItemPriceMapper;

    public CommerceItemPriceServiceImpl(CommerceItemPriceRepository commerceItemPriceRepository, CommerceItemPriceMapper commerceItemPriceMapper) {
        this.commerceItemPriceRepository = commerceItemPriceRepository;
        this.commerceItemPriceMapper = commerceItemPriceMapper;
    }

    /**
     * Save a commerceItemPrice.
     *
     * @param commerceItemPriceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceItemPriceDTO save(CommerceItemPriceDTO commerceItemPriceDTO) {
        log.debug("Request to save CommerceItemPrice : {}", commerceItemPriceDTO);
        CommerceItemPrice commerceItemPrice = commerceItemPriceMapper.toEntity(commerceItemPriceDTO);
        commerceItemPrice = commerceItemPriceRepository.save(commerceItemPrice);
        return commerceItemPriceMapper.toDto(commerceItemPrice);
    }

    /**
     * Get all the commerceItemPrices.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommerceItemPriceDTO> findAll() {
        log.debug("Request to get all CommerceItemPrices");
        return commerceItemPriceRepository.findAll().stream()
            .map(commerceItemPriceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commerceItemPrice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceItemPriceDTO> findOne(Long id) {
        log.debug("Request to get CommerceItemPrice : {}", id);
        return commerceItemPriceRepository.findById(id)
            .map(commerceItemPriceMapper::toDto);
    }

    /**
     * Delete the commerceItemPrice by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceItemPrice : {}", id);
        commerceItemPriceRepository.deleteById(id);
    }
}
