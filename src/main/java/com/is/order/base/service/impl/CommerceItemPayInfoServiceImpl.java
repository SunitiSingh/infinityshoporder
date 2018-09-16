package com.is.order.base.service.impl;

import com.is.order.base.service.CommerceItemPayInfoService;
import com.is.order.base.domain.CommerceItemPayInfo;
import com.is.order.base.repository.CommerceItemPayInfoRepository;
import com.is.order.base.service.dto.CommerceItemPayInfoDTO;
import com.is.order.base.service.mapper.CommerceItemPayInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CommerceItemPayInfo.
 */
@Service
@Transactional
public class CommerceItemPayInfoServiceImpl implements CommerceItemPayInfoService {

    private final Logger log = LoggerFactory.getLogger(CommerceItemPayInfoServiceImpl.class);

    private final CommerceItemPayInfoRepository commerceItemPayInfoRepository;

    private final CommerceItemPayInfoMapper commerceItemPayInfoMapper;

    public CommerceItemPayInfoServiceImpl(CommerceItemPayInfoRepository commerceItemPayInfoRepository, CommerceItemPayInfoMapper commerceItemPayInfoMapper) {
        this.commerceItemPayInfoRepository = commerceItemPayInfoRepository;
        this.commerceItemPayInfoMapper = commerceItemPayInfoMapper;
    }

    /**
     * Save a commerceItemPayInfo.
     *
     * @param commerceItemPayInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceItemPayInfoDTO save(CommerceItemPayInfoDTO commerceItemPayInfoDTO) {
        log.debug("Request to save CommerceItemPayInfo : {}", commerceItemPayInfoDTO);
        CommerceItemPayInfo commerceItemPayInfo = commerceItemPayInfoMapper.toEntity(commerceItemPayInfoDTO);
        commerceItemPayInfo = commerceItemPayInfoRepository.save(commerceItemPayInfo);
        return commerceItemPayInfoMapper.toDto(commerceItemPayInfo);
    }

    /**
     * Get all the commerceItemPayInfos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommerceItemPayInfoDTO> findAll() {
        log.debug("Request to get all CommerceItemPayInfos");
        return commerceItemPayInfoRepository.findAll().stream()
            .map(commerceItemPayInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commerceItemPayInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceItemPayInfoDTO> findOne(Long id) {
        log.debug("Request to get CommerceItemPayInfo : {}", id);
        return commerceItemPayInfoRepository.findById(id)
            .map(commerceItemPayInfoMapper::toDto);
    }

    /**
     * Delete the commerceItemPayInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceItemPayInfo : {}", id);
        commerceItemPayInfoRepository.deleteById(id);
    }
}
