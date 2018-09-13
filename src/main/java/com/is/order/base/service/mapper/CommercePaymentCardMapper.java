package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommercePaymentCardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommercePaymentCard and its DTO CommercePaymentCardDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommercePaymentCardMapper extends EntityMapper<CommercePaymentCardDTO, CommercePaymentCard> {



    default CommercePaymentCard fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercePaymentCard commercePaymentCard = new CommercePaymentCard();
        commercePaymentCard.setId(id);
        return commercePaymentCard;
    }
}
