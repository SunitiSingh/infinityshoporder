package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.ISShippingContainerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ISShippingContainer and its DTO ISShippingContainerDTO.
 */
@Mapper(componentModel = "spring", uses = {ISOrderMapper.class, ISShipContainerPriceMapper.class})
public interface ISShippingContainerMapper extends EntityMapper<ISShippingContainerDTO, ISShippingContainer> {

    @Mapping(source = "ISOrder.id", target = "ISOrderId")
    @Mapping(source = "price.id", target = "priceId")
    ISShippingContainerDTO toDto(ISShippingContainer iSShippingContainer);

    @Mapping(source = "ISOrderId", target = "ISOrder")
    @Mapping(source = "priceId", target = "price")
    @Mapping(target = "items", ignore = true)
    ISShippingContainer toEntity(ISShippingContainerDTO iSShippingContainerDTO);

    default ISShippingContainer fromId(Long id) {
        if (id == null) {
            return null;
        }
        ISShippingContainer iSShippingContainer = new ISShippingContainer();
        iSShippingContainer.setId(id);
        return iSShippingContainer;
    }
}
