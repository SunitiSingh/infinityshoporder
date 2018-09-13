package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceBillingAddressService;
import com.is.order.base.domain.CommerceBillingAddress;
import com.is.order.base.repository.CommerceBillingAddressRepository;
import com.is.order.base.service.dto.CommerceBillingAddressDTO;
import com.is.order.base.service.mapper.CommerceBillingAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CommerceBillingAddress.
 */
@Service
@Transactional
public class CommerceBillingAddressServiceImpl implements CommerceBillingAddressService {

    private final Logger log = LoggerFactory.getLogger(CommerceBillingAddressServiceImpl.class);

    private final CommerceBillingAddressRepository commerceBillingAddressRepository;

    private final CommerceBillingAddressMapper commerceBillingAddressMapper;

    public CommerceBillingAddressServiceImpl(CommerceBillingAddressRepository commerceBillingAddressRepository, CommerceBillingAddressMapper commerceBillingAddressMapper) {
        this.commerceBillingAddressRepository = commerceBillingAddressRepository;
        this.commerceBillingAddressMapper = commerceBillingAddressMapper;
    }

    /**
     * Save a commerceBillingAddress.
     *
     * @param commerceBillingAddressDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceBillingAddressDTO save(CommerceBillingAddressDTO commerceBillingAddressDTO) {
        log.debug("Request to save CommerceBillingAddress : {}", commerceBillingAddressDTO);
        CommerceBillingAddress commerceBillingAddress = commerceBillingAddressMapper.toEntity(commerceBillingAddressDTO);
        commerceBillingAddress = commerceBillingAddressRepository.save(commerceBillingAddress);
        return commerceBillingAddressMapper.toDto(commerceBillingAddress);
    }

    /**
     * Get all the commerceBillingAddresses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommerceBillingAddressDTO> findAll() {
        log.debug("Request to get all CommerceBillingAddresses");
        return commerceBillingAddressRepository.findAll().stream()
            .map(commerceBillingAddressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commerceBillingAddress by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceBillingAddressDTO> findOne(Long id) {
        log.debug("Request to get CommerceBillingAddress : {}", id);
        return commerceBillingAddressRepository.findById(id)
            .map(commerceBillingAddressMapper::toDto);
    }

    /**
     * Delete the commerceBillingAddress by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceBillingAddress : {}", id);
        commerceBillingAddressRepository.deleteById(id);
    }
}
