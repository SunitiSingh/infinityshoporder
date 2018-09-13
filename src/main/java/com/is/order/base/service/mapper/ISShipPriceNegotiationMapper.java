package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.ISShipPriceNegotiationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ISShipPriceNegotiation and its DTO ISShipPriceNegotiationDTO.
 */
@Mapper(componentModel = "spring", uses = {ISShipContainerPriceMapper.class})
public interface ISShipPriceNegotiationMapper extends EntityMapper<ISShipPriceNegotiationDTO, ISShipPriceNegotiation> {

    @Mapping(source = "ISShipContainerPrice.id", target = "ISShipContainerPriceId")
    ISShipPriceNegotiationDTO toDto(ISShipPriceNegotiation iSShipPriceNegotiation);

    @Mapping(source = "ISShipContainerPriceId", target = "ISShipContainerPrice")
    ISShipPriceNegotiation toEntity(ISShipPriceNegotiationDTO iSShipPriceNegotiationDTO);

    default ISShipPriceNegotiation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ISShipPriceNegotiation iSShipPriceNegotiation = new ISShipPriceNegotiation();
        iSShipPriceNegotiation.setId(id);
        return iSShipPriceNegotiation;
    }
}
