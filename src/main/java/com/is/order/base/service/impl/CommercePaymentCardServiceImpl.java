package com.is.order.base.service.impl;

import com.is.order.base.service.CommercePaymentCardService;
import com.is.order.base.domain.CommercePaymentCard;
import com.is.order.base.repository.CommercePaymentCardRepository;
import com.is.order.base.service.dto.CommercePaymentCardDTO;
import com.is.order.base.service.mapper.CommercePaymentCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CommercePaymentCard.
 */
@Service
@Transactional
public class CommercePaymentCardServiceImpl implements CommercePaymentCardService {

    private final Logger log = LoggerFactory.getLogger(CommercePaymentCardServiceImpl.class);

    private final CommercePaymentCardRepository commercePaymentCardRepository;

    private final CommercePaymentCardMapper commercePaymentCardMapper;

    public CommercePaymentCardServiceImpl(CommercePaymentCardRepository commercePaymentCardRepository, CommercePaymentCardMapper commercePaymentCardMapper) {
        this.commercePaymentCardRepository = commercePaymentCardRepository;
        this.commercePaymentCardMapper = commercePaymentCardMapper;
    }

    /**
     * Save a commercePaymentCard.
     *
     * @param commercePaymentCardDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommercePaymentCardDTO save(CommercePaymentCardDTO commercePaymentCardDTO) {
        log.debug("Request to save CommercePaymentCard : {}", commercePaymentCardDTO);
        CommercePaymentCard commercePaymentCard = commercePaymentCardMapper.toEntity(commercePaymentCardDTO);
        commercePaymentCard = commercePaymentCardRepository.save(commercePaymentCard);
        return commercePaymentCardMapper.toDto(commercePaymentCard);
    }

    /**
     * Get all the commercePaymentCards.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommercePaymentCardDTO> findAll() {
        log.debug("Request to get all CommercePaymentCards");
        return commercePaymentCardRepository.findAll().stream()
            .map(commercePaymentCardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commercePaymentCard by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommercePaymentCardDTO> findOne(Long id) {
        log.debug("Request to get CommercePaymentCard : {}", id);
        return commercePaymentCardRepository.findById(id)
            .map(commercePaymentCardMapper::toDto);
    }

    /**
     * Delete the commercePaymentCard by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommercePaymentCard : {}", id);
        commercePaymentCardRepository.deleteById(id);
    }
}
