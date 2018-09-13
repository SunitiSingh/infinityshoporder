package com.is.order.base.service.impl;

import com.is.order.base.service.ISPaymentCardService;
import com.is.order.base.domain.ISPaymentCard;
import com.is.order.base.repository.ISPaymentCardRepository;
import com.is.order.base.service.dto.ISPaymentCardDTO;
import com.is.order.base.service.mapper.ISPaymentCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ISPaymentCard.
 */
@Service
@Transactional
public class ISPaymentCardServiceImpl implements ISPaymentCardService {

    private final Logger log = LoggerFactory.getLogger(ISPaymentCardServiceImpl.class);

    private final ISPaymentCardRepository iSPaymentCardRepository;

    private final ISPaymentCardMapper iSPaymentCardMapper;

    public ISPaymentCardServiceImpl(ISPaymentCardRepository iSPaymentCardRepository, ISPaymentCardMapper iSPaymentCardMapper) {
        this.iSPaymentCardRepository = iSPaymentCardRepository;
        this.iSPaymentCardMapper = iSPaymentCardMapper;
    }

    /**
     * Save a iSPaymentCard.
     *
     * @param iSPaymentCardDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ISPaymentCardDTO save(ISPaymentCardDTO iSPaymentCardDTO) {
        log.debug("Request to save ISPaymentCard : {}", iSPaymentCardDTO);
        ISPaymentCard iSPaymentCard = iSPaymentCardMapper.toEntity(iSPaymentCardDTO);
        iSPaymentCard = iSPaymentCardRepository.save(iSPaymentCard);
        return iSPaymentCardMapper.toDto(iSPaymentCard);
    }

    /**
     * Get all the iSPaymentCards.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ISPaymentCardDTO> findAll() {
        log.debug("Request to get all ISPaymentCards");
        return iSPaymentCardRepository.findAll().stream()
            .map(iSPaymentCardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one iSPaymentCard by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ISPaymentCardDTO> findOne(Long id) {
        log.debug("Request to get ISPaymentCard : {}", id);
        return iSPaymentCardRepository.findById(id)
            .map(iSPaymentCardMapper::toDto);
    }

    /**
     * Delete the iSPaymentCard by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISPaymentCard : {}", id);
        iSPaymentCardRepository.deleteById(id);
    }
}
