package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.ISOrderPriceNegotiationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ISOrderPriceNegotiation and its DTO ISOrderPriceNegotiationDTO.
 */
@Mapper(componentModel = "spring", uses = {ISOrderPriceMapper.class})
public interface ISOrderPriceNegotiationMapper extends EntityMapper<ISOrderPriceNegotiationDTO, ISOrderPriceNegotiation> {

    @Mapping(source = "ISOrderPrice.id", target = "ISOrderPriceId")
    ISOrderPriceNegotiationDTO toDto(ISOrderPriceNegotiation iSOrderPriceNegotiation);

    @Mapping(source = "ISOrderPriceId", target = "ISOrderPrice")
    ISOrderPriceNegotiation toEntity(ISOrderPriceNegotiationDTO iSOrderPriceNegotiationDTO);

    default ISOrderPriceNegotiation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ISOrderPriceNegotiation iSOrderPriceNegotiation = new ISOrderPriceNegotiation();
        iSOrderPriceNegotiation.setId(id);
        return iSOrderPriceNegotiation;
    }
}
