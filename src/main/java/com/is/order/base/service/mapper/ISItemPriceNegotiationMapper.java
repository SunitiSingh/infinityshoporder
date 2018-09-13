package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.ISItemPriceNegotiationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ISItemPriceNegotiation and its DTO ISItemPriceNegotiationDTO.
 */
@Mapper(componentModel = "spring", uses = {ISItemPriceMapper.class})
public interface ISItemPriceNegotiationMapper extends EntityMapper<ISItemPriceNegotiationDTO, ISItemPriceNegotiation> {

    @Mapping(source = "ISItemPrice.id", target = "ISItemPriceId")
    ISItemPriceNegotiationDTO toDto(ISItemPriceNegotiation iSItemPriceNegotiation);

    @Mapping(source = "ISItemPriceId", target = "ISItemPrice")
    ISItemPriceNegotiation toEntity(ISItemPriceNegotiationDTO iSItemPriceNegotiationDTO);

    default ISItemPriceNegotiation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ISItemPriceNegotiation iSItemPriceNegotiation = new ISItemPriceNegotiation();
        iSItemPriceNegotiation.setId(id);
        return iSItemPriceNegotiation;
    }
}
