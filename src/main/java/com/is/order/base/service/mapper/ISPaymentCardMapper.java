package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.ISPaymentCardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ISPaymentCard and its DTO ISPaymentCardDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ISPaymentCardMapper extends EntityMapper<ISPaymentCardDTO, ISPaymentCard> {



    default ISPaymentCard fromId(Long id) {
        if (id == null) {
            return null;
        }
        ISPaymentCard iSPaymentCard = new ISPaymentCard();
        iSPaymentCard.setId(id);
        return iSPaymentCard;
    }
}
