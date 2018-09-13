package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceOrderPaymentService;
import com.is.order.base.domain.CommerceOrderPayment;
import com.is.order.base.repository.CommerceOrderPaymentRepository;
import com.is.order.base.service.dto.CommerceOrderPaymentDTO;
import com.is.order.base.service.mapper.CommerceOrderPaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CommerceOrderPayment.
 */
@Service
@Transactional
public class CommerceOrderPaymentServiceImpl implements CommerceOrderPaymentService {

    private final Logger log = LoggerFactory.getLogger(CommerceOrderPaymentServiceImpl.class);

    private final CommerceOrderPaymentRepository commerceOrderPaymentRepository;

    private final CommerceOrderPaymentMapper commerceOrderPaymentMapper;

    public CommerceOrderPaymentServiceImpl(CommerceOrderPaymentRepository commerceOrderPaymentRepository, CommerceOrderPaymentMapper commerceOrderPaymentMapper) {
        this.commerceOrderPaymentRepository = commerceOrderPaymentRepository;
        this.commerceOrderPaymentMapper = commerceOrderPaymentMapper;
    }

    /**
     * Save a commerceOrderPayment.
     *
     * @param commerceOrderPaymentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceOrderPaymentDTO save(CommerceOrderPaymentDTO commerceOrderPaymentDTO) {
        log.debug("Request to save CommerceOrderPayment : {}", commerceOrderPaymentDTO);
        CommerceOrderPayment commerceOrderPayment = commerceOrderPaymentMapper.toEntity(commerceOrderPaymentDTO);
        commerceOrderPayment = commerceOrderPaymentRepository.save(commerceOrderPayment);
        return commerceOrderPaymentMapper.toDto(commerceOrderPayment);
    }

    /**
     * Get all the commerceOrderPayments.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommerceOrderPaymentDTO> findAll() {
        log.debug("Request to get all CommerceOrderPayments");
        return commerceOrderPaymentRepository.findAll().stream()
            .map(commerceOrderPaymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commerceOrderPayment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceOrderPaymentDTO> findOne(Long id) {
        log.debug("Request to get CommerceOrderPayment : {}", id);
        return commerceOrderPaymentRepository.findById(id)
            .map(commerceOrderPaymentMapper::toDto);
    }

    /**
     * Delete the commerceOrderPayment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceOrderPayment : {}", id);
        commerceOrderPaymentRepository.deleteById(id);
    }
}
