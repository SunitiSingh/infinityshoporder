package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceOrderPaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceOrderPayment and its DTO CommerceOrderPaymentDTO.
 */
@Mapper(componentModel = "spring", uses = {CommerceOrderMapper.class, CommercePaymentCardMapper.class, CommerceEPayMapper.class, CommerceBillingAddressMapper.class})
public interface CommerceOrderPaymentMapper extends EntityMapper<CommerceOrderPaymentDTO, CommerceOrderPayment> {

    @Mapping(source = "commerceOrder.id", target = "commerceOrderId")
    @Mapping(source = "card.id", target = "cardId")
    @Mapping(source = "epay.id", target = "epayId")
    @Mapping(source = "billingAddress.id", target = "billingAddressId")
    CommerceOrderPaymentDTO toDto(CommerceOrderPayment commerceOrderPayment);

    @Mapping(source = "commerceOrderId", target = "commerceOrder")
    @Mapping(source = "cardId", target = "card")
    @Mapping(source = "epayId", target = "epay")
    @Mapping(source = "billingAddressId", target = "billingAddress")
    @Mapping(target = "items", ignore = true)
    CommerceOrderPayment toEntity(CommerceOrderPaymentDTO commerceOrderPaymentDTO);

    default CommerceOrderPayment fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceOrderPayment commerceOrderPayment = new CommerceOrderPayment();
        commerceOrderPayment.setId(id);
        return commerceOrderPayment;
    }
}
