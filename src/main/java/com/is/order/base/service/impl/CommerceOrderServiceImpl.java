package com.is.order.base.service.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.is.order.base.domain.CommerceOrder;
import com.is.order.base.domain.CommerceOrderPayment;
import com.is.order.base.domain.CommerceShippingContainer;
import com.is.order.base.domain.enumeration.CommerceShipStatus;
import com.is.order.base.domain.enumeration.PayStatus;
import com.is.order.base.repository.CommerceOrderPaymentRepository;
import com.is.order.base.repository.CommerceOrderRepository;
import com.is.order.base.repository.CommerceShippingContainerRepository;
import com.is.order.base.service.CommerceOrderService;
import com.is.order.base.service.dto.CommerceOrderDTO;
import com.is.order.base.service.dto.CommerceOrderPaymentDTO;
import com.is.order.base.service.dto.CommerceShippingContainerDTO;
import com.is.order.base.service.mapper.CommerceOrderMapper;
import com.is.order.base.service.mapper.CommerceOrderPaymentMapper;
import com.is.order.base.service.mapper.CommerceShippingContainerMapper;
/**
 * Service Implementation for managing CommerceOrder.
 */
@Service
@Transactional
public class CommerceOrderServiceImpl implements CommerceOrderService {

    private final Logger log = LoggerFactory.getLogger(CommerceOrderServiceImpl.class);

    private final CommerceOrderRepository commerceOrderRepository;

    private final CommerceOrderMapper commerceOrderMapper;
    
    private final CommerceShippingContainerMapper commerceShippingContainerMapper;
    
    private final CommerceOrderPaymentMapper commerceOrderPaymentMapper;

    public CommerceOrderServiceImpl(CommerceOrderRepository commerceOrderRepository, CommerceOrderMapper commerceOrderMapper,
    					CommerceShippingContainerMapper commerceShippingContainerMapper,
    					CommerceOrderPaymentMapper commerceOrderPaymentMapper) {
        this.commerceOrderRepository = commerceOrderRepository;
        this.commerceOrderMapper = commerceOrderMapper;
        this.commerceShippingContainerMapper = commerceShippingContainerMapper;
        this.commerceOrderPaymentMapper = commerceOrderPaymentMapper;
    }

    /**
     * Save a commerceOrder.
     *
     * @param commerceOrderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommerceOrderDTO save(CommerceOrderDTO commerceOrderDTO) {
        log.debug("Request to save CommerceOrder : {}", commerceOrderDTO);
        
        log.error("Commerce Order ID " + commerceOrderDTO.getId());
        
        if(commerceOrderDTO.getId() ==  null) {
        
	        //Create default shipping payment and other basic entities
	        CommerceShippingContainerDTO commerceShippingContainerDTO = new CommerceShippingContainerDTO();
	        CommerceOrderPaymentDTO commerceOrderPaymentDTO = new CommerceOrderPaymentDTO();
	        
	        commerceShippingContainerDTO.setShipstatus(CommerceShipStatus.INIT);
	        commerceShippingContainerDTO.setCreationDate(ZonedDateTime.now(ZoneId.systemDefault()));
	        commerceOrderPaymentDTO.setPaystatus(PayStatus.INITIAL);
	        
	        CommerceShippingContainer commerceShippingContainer = commerceShippingContainerMapper.toEntity(commerceShippingContainerDTO);
	        //commerceShippingContainer = commerceShippingContainerRepository.save(commerceShippingContainer);
	        CommerceOrderPayment commerceOrderPayment = commerceOrderPaymentMapper.toEntity(commerceOrderPaymentDTO);
	        //commerceOrderPayment = commerceOrderPaymentRepository.save(commerceOrderPayment);
	        
	        
	        CommerceOrder commerceOrder = commerceOrderMapper.toEntity(commerceOrderDTO);
	        
	        commerceShippingContainer.setCommerceOrder(commerceOrder);
	        commerceOrderPayment.setCommerceOrder(commerceOrder);
	        
	        List<CommerceShippingContainer> commerceShippingContainers = new ArrayList<CommerceShippingContainer>();
	        commerceShippingContainers.add(commerceShippingContainer);
	                
	        commerceOrder.setShipcontainers(commerceShippingContainers);
	        
	        List<CommerceOrderPayment> commerceOrderPayments = new ArrayList<CommerceOrderPayment>();
	        commerceOrderPayments.add(commerceOrderPayment);
	        
	        commerceOrder.setPayments(commerceOrderPayments);
	        commerceOrder.setCreationDate(ZonedDateTime.now(ZoneId.systemDefault()));
	        
	        commerceOrder = commerceOrderRepository.save(commerceOrder);
	        return commerceOrderMapper.toDto(commerceOrder);
        } else {
        	CommerceOrder commerceOrder = commerceOrderMapper.toEntity(commerceOrderDTO);
        	commerceOrder.setUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        	commerceOrder = commerceOrderRepository.save(commerceOrder);
        	return commerceOrderMapper.toDto(commerceOrder);
        }
    }

    /**
     * Get all the commerceOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CommerceOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommerceOrders");
        return commerceOrderRepository.findAll(pageable)
            .map(commerceOrderMapper::toDto);
    }


    /**
     * Get one commerceOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommerceOrderDTO> findOne(Long id) {
        log.debug("Request to get CommerceOrder : {}", id);
        return commerceOrderRepository.findById(id)
            .map(commerceOrderMapper::toDto);
    }

    /**
     * Delete the commerceOrder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommerceOrder : {}", id);
        commerceOrderRepository.deleteById(id);
    }
}
