package com.is.order.base.service.impl;

import com.is.order.base.service.ISBillingAddressService;
import com.is.order.base.domain.ISBillingAddress;
import com.is.order.base.repository.ISBillingAddressRepository;
import com.is.order.base.service.dto.ISBillingAddressDTO;
import com.is.order.base.service.mapper.ISBillingAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ISBillingAddress.
 */
@Service
@Transactional
public class ISBillingAddressServiceImpl implements ISBillingAddressService {

    private final Logger log = LoggerFactory.getLogger(ISBillingAddressServiceImpl.class);

    private final ISBillingAddressRepository iSBillingAddressRepository;

    private final ISBillingAddressMapper iSBillingAddressMapper;

    public ISBillingAddressServiceImpl(ISBillingAddressRepository iSBillingAddressRepository, ISBillingAddressMapper iSBillingAddressMapper) {
        this.iSBillingAddressRepository = iSBillingAddressRepository;
        this.iSBillingAddressMapper = iSBillingAddressMapper;
    }

    /**
     * Save a iSBillingAddress.
     *
     * @param iSBillingAddressDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ISBillingAddressDTO save(ISBillingAddressDTO iSBillingAddressDTO) {
        log.debug("Request to save ISBillingAddress : {}", iSBillingAddressDTO);
        ISBillingAddress iSBillingAddress = iSBillingAddressMapper.toEntity(iSBillingAddressDTO);
        iSBillingAddress = iSBillingAddressRepository.save(iSBillingAddress);
        return iSBillingAddressMapper.toDto(iSBillingAddress);
    }

    /**
     * Get all the iSBillingAddresses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ISBillingAddressDTO> findAll() {
        log.debug("Request to get all ISBillingAddresses");
        return iSBillingAddressRepository.findAll().stream()
            .map(iSBillingAddressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one iSBillingAddress by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ISBillingAddressDTO> findOne(Long id) {
        log.debug("Request to get ISBillingAddress : {}", id);
        return iSBillingAddressRepository.findById(id)
            .map(iSBillingAddressMapper::toDto);
    }

    /**
     * Delete the iSBillingAddress by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISBillingAddress : {}", id);
        iSBillingAddressRepository.deleteById(id);
    }
}
