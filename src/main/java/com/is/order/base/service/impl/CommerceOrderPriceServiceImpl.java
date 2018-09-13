package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceOrderPriceService;
import com.is.order.base.domain.CommerceOrderPrice;
import com.is.order.base.repository.CommerceOrderPriceRepository;
import com.is.order.base.service.dto.CommerceOrderPriceDTO;
import com.is.order.base.service.mapper.CommerceOrderPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CommerceOrderPrice.
 */
@Service
@Transactional
public class CommerceOrderPriceServiceImpl implements CommerceOrderPriceService {

    private final Logger log = LoggerFactory.getLogger(CommerceOrderPriceServiceImpl.class);

    private final CommerceOrderPriceRepository commerceOrderPriceRepository;

    private final CommerceOrderPriceMapper commerceOrderPriceMapper;

    public CommerceOrderPriceServiceImpl(CommerceOrderPriceRepository commerceOrderPriceRepository, CommerceOrderPriceMapper commerceOrderPriceMapper) {
        this.commerceOrderPriceRepository = commerceOrderPriceRepository;
        this.commerceOrderPriceMapper = commerceOrderPriceMapper;
    }

    /**
     * Save a commerceOrderPrice.
     *
     * @param commerceOrderPriceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceOrderPriceDTO save(CommerceOrderPriceDTO commerceOrderPriceDTO) {
        log.debug("Request to save CommerceOrderPrice : {}", commerceOrderPriceDTO);
        CommerceOrderPrice commerceOrderPrice = commerceOrderPriceMapper.toEntity(commerceOrderPriceDTO);
        commerceOrderPrice = commerceOrderPriceRepository.save(commerceOrderPrice);
        return commerceOrderPriceMapper.toDto(commerceOrderPrice);
    }

    /**
     * Get all the commerceOrderPrices.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommerceOrderPriceDTO> findAll() {
        log.debug("Request to get all CommerceOrderPrices");
        return commerceOrderPriceRepository.findAll().stream()
            .map(commerceOrderPriceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commerceOrderPrice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceOrderPriceDTO> findOne(Long id) {
        log.debug("Request to get CommerceOrderPrice : {}", id);
        return commerceOrderPriceRepository.findById(id)
            .map(commerceOrderPriceMapper::toDto);
    }

    /**
     * Delete the commerceOrderPrice by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceOrderPrice : {}", id);
        commerceOrderPriceRepository.deleteById(id);
    }
}
