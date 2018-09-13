package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceShipContainerPriceService;
import com.is.order.base.domain.CommerceShipContainerPrice;
import com.is.order.base.repository.CommerceShipContainerPriceRepository;
import com.is.order.base.service.dto.CommerceShipContainerPriceDTO;
import com.is.order.base.service.mapper.CommerceShipContainerPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CommerceShipContainerPrice.
 */
@Service
@Transactional
public class CommerceShipContainerPriceServiceImpl implements CommerceShipContainerPriceService {

    private final Logger log = LoggerFactory.getLogger(CommerceShipContainerPriceServiceImpl.class);

    private final CommerceShipContainerPriceRepository commerceShipContainerPriceRepository;

    private final CommerceShipContainerPriceMapper commerceShipContainerPriceMapper;

    public CommerceShipContainerPriceServiceImpl(CommerceShipContainerPriceRepository commerceShipContainerPriceRepository, CommerceShipContainerPriceMapper commerceShipContainerPriceMapper) {
        this.commerceShipContainerPriceRepository = commerceShipContainerPriceRepository;
        this.commerceShipContainerPriceMapper = commerceShipContainerPriceMapper;
    }

    /**
     * Save a commerceShipContainerPrice.
     *
     * @param commerceShipContainerPriceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceShipContainerPriceDTO save(CommerceShipContainerPriceDTO commerceShipContainerPriceDTO) {
        log.debug("Request to save CommerceShipContainerPrice : {}", commerceShipContainerPriceDTO);
        CommerceShipContainerPrice commerceShipContainerPrice = commerceShipContainerPriceMapper.toEntity(commerceShipContainerPriceDTO);
        commerceShipContainerPrice = commerceShipContainerPriceRepository.save(commerceShipContainerPrice);
        return commerceShipContainerPriceMapper.toDto(commerceShipContainerPrice);
    }

    /**
     * Get all the commerceShipContainerPrices.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommerceShipContainerPriceDTO> findAll() {
        log.debug("Request to get all CommerceShipContainerPrices");
        return commerceShipContainerPriceRepository.findAll().stream()
            .map(commerceShipContainerPriceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commerceShipContainerPrice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceShipContainerPriceDTO> findOne(Long id) {
        log.debug("Request to get CommerceShipContainerPrice : {}", id);
        return commerceShipContainerPriceRepository.findById(id)
            .map(commerceShipContainerPriceMapper::toDto);
    }

    /**
     * Delete the commerceShipContainerPrice by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceShipContainerPrice : {}", id);
        commerceShipContainerPriceRepository.deleteById(id);
    }
}
