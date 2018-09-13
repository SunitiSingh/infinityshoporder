package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.ISOrderPaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ISOrderPayment and its DTO ISOrderPaymentDTO.
 */
@Mapper(componentModel = "spring", uses = {ISOrderMapper.class, ISPaymentCardMapper.class, ISEPayMapper.class, ISBillingAddressMapper.class})
public interface ISOrderPaymentMapper extends EntityMapper<ISOrderPaymentDTO, ISOrderPayment> {

    @Mapping(source = "ISOrder.id", target = "ISOrderId")
    @Mapping(source = "card.id", target = "cardId")
    @Mapping(source = "epay.id", target = "epayId")
    @Mapping(source = "billingAddress.id", target = "billingAddressId")
    ISOrderPaymentDTO toDto(ISOrderPayment iSOrderPayment);

    @Mapping(source = "ISOrderId", target = "ISOrder")
    @Mapping(source = "cardId", target = "card")
    @Mapping(source = "epayId", target = "epay")
    @Mapping(source = "billingAddressId", target = "billingAddress")
    @Mapping(target = "items", ignore = true)
    ISOrderPayment toEntity(ISOrderPaymentDTO iSOrderPaymentDTO);

    default ISOrderPayment fromId(Long id) {
        if (id == null) {
            return null;
        }
        ISOrderPayment iSOrderPayment = new ISOrderPayment();
        iSOrderPayment.setId(id);
        return iSOrderPayment;
    }
}
