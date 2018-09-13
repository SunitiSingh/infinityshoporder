package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceShipContainerPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceShipContainerPrice and its DTO CommerceShipContainerPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommerceShipContainerPriceMapper extends EntityMapper<CommerceShipContainerPriceDTO, CommerceShipContainerPrice> {


    @Mapping(target = "ngs", ignore = true)
    CommerceShipContainerPrice toEntity(CommerceShipContainerPriceDTO commerceShipContainerPriceDTO);

    default CommerceShipContainerPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceShipContainerPrice commerceShipContainerPrice = new CommerceShipContainerPrice();
        commerceShipContainerPrice.setId(id);
        return commerceShipContainerPrice;
    }
}
