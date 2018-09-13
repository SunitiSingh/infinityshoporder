package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.ISShipContainerPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ISShipContainerPrice and its DTO ISShipContainerPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ISShipContainerPriceMapper extends EntityMapper<ISShipContainerPriceDTO, ISShipContainerPrice> {


    @Mapping(target = "negotiations", ignore = true)
    ISShipContainerPrice toEntity(ISShipContainerPriceDTO iSShipContainerPriceDTO);

    default ISShipContainerPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        ISShipContainerPrice iSShipContainerPrice = new ISShipContainerPrice();
        iSShipContainerPrice.setId(id);
        return iSShipContainerPrice;
    }
}
