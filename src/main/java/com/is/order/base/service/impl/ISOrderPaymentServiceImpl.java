package com.is.order.base.service.impl;

import com.is.order.base.service.ISOrderPaymentService;
import com.is.order.base.domain.ISOrderPayment;
import com.is.order.base.repository.ISOrderPaymentRepository;
import com.is.order.base.service.dto.ISOrderPaymentDTO;
import com.is.order.base.service.mapper.ISOrderPaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ISOrderPayment.
 */
@Service
@Transactional
public class ISOrderPaymentServiceImpl implements ISOrderPaymentService {

    private final Logger log = LoggerFactory.getLogger(ISOrderPaymentServiceImpl.class);

    private final ISOrderPaymentRepository iSOrderPaymentRepository;

    private final ISOrderPaymentMapper iSOrderPaymentMapper;

    public ISOrderPaymentServiceImpl(ISOrderPaymentRepository iSOrderPaymentRepository, ISOrderPaymentMapper iSOrderPaymentMapper) {
        this.iSOrderPaymentRepository = iSOrderPaymentRepository;
        this.iSOrderPaymentMapper = iSOrderPaymentMapper;
    }

    /**
     * Save a iSOrderPayment.
     *
     * @param iSOrderPaymentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ISOrderPaymentDTO save(ISOrderPaymentDTO iSOrderPaymentDTO) {
        log.debug("Request to save ISOrderPayment : {}", iSOrderPaymentDTO);
        ISOrderPayment iSOrderPayment = iSOrderPaymentMapper.toEntity(iSOrderPaymentDTO);
        iSOrderPayment = iSOrderPaymentRepository.save(iSOrderPayment);
        return iSOrderPaymentMapper.toDto(iSOrderPayment);
    }

    /**
     * Get all the iSOrderPayments.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ISOrderPaymentDTO> findAll() {
        log.debug("Request to get all ISOrderPayments");
        return iSOrderPaymentRepository.findAll().stream()
            .map(iSOrderPaymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one iSOrderPayment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ISOrderPaymentDTO> findOne(Long id) {
        log.debug("Request to get ISOrderPayment : {}", id);
        return iSOrderPaymentRepository.findById(id)
            .map(iSOrderPaymentMapper::toDto);
    }

    /**
     * Delete the iSOrderPayment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISOrderPayment : {}", id);
        iSOrderPaymentRepository.deleteById(id);
    }
}
