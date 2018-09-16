package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceItemShipInfoService;
import com.is.order.base.domain.CommerceItemShipInfo;
import com.is.order.base.repository.CommerceItemShipInfoRepository;
import com.is.order.base.service.dto.CommerceItemShipInfoDTO;
import com.is.order.base.service.mapper.CommerceItemShipInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CommerceItemShipInfo.
 */
@Service
@Transactional
public class CommerceItemShipInfoServiceImpl implements CommerceItemShipInfoService {

    private final Logger log = LoggerFactory.getLogger(CommerceItemShipInfoServiceImpl.class);

    private final CommerceItemShipInfoRepository commerceItemShipInfoRepository;

    private final CommerceItemShipInfoMapper commerceItemShipInfoMapper;

    public CommerceItemShipInfoServiceImpl(CommerceItemShipInfoRepository commerceItemShipInfoRepository, CommerceItemShipInfoMapper commerceItemShipInfoMapper) {
        this.commerceItemShipInfoRepository = commerceItemShipInfoRepository;
        this.commerceItemShipInfoMapper = commerceItemShipInfoMapper;
    }

    /**
     * Save a commerceItemShipInfo.
     *
     * @param commerceItemShipInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceItemShipInfoDTO save(CommerceItemShipInfoDTO commerceItemShipInfoDTO) {
        log.debug("Request to save CommerceItemShipInfo : {}", commerceItemShipInfoDTO);
        CommerceItemShipInfo commerceItemShipInfo = commerceItemShipInfoMapper.toEntity(commerceItemShipInfoDTO);
        commerceItemShipInfo = commerceItemShipInfoRepository.save(commerceItemShipInfo);
        return commerceItemShipInfoMapper.toDto(commerceItemShipInfo);
    }

    /**
     * Get all the commerceItemShipInfos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommerceItemShipInfoDTO> findAll() {
        log.debug("Request to get all CommerceItemShipInfos");
        return commerceItemShipInfoRepository.findAll().stream()
            .map(commerceItemShipInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commerceItemShipInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceItemShipInfoDTO> findOne(Long id) {
        log.debug("Request to get CommerceItemShipInfo : {}", id);
        return commerceItemShipInfoRepository.findById(id)
            .map(commerceItemShipInfoMapper::toDto);
    }

    /**
     * Delete the commerceItemShipInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceItemShipInfo : {}", id);
        commerceItemShipInfoRepository.deleteById(id);
    }
}
