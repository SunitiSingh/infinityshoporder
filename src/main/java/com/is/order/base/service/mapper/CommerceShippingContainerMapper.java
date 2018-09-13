package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceShippingContainerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceShippingContainer and its DTO CommerceShippingContainerDTO.
 */
@Mapper(componentModel = "spring", uses = {CommerceOrderMapper.class, CommerceShipContainerPriceMapper.class})
public interface CommerceShippingContainerMapper extends EntityMapper<CommerceShippingContainerDTO, CommerceShippingContainer> {

    @Mapping(source = "commerceOrder.id", target = "commerceOrderId")
    @Mapping(source = "price.id", target = "priceId")
    CommerceShippingContainerDTO toDto(CommerceShippingContainer commerceShippingContainer);

    @Mapping(source = "commerceOrderId", target = "commerceOrder")
    @Mapping(source = "priceId", target = "price")
    @Mapping(target = "items", ignore = true)
    CommerceShippingContainer toEntity(CommerceShippingContainerDTO commerceShippingContainerDTO);

    default CommerceShippingContainer fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceShippingContainer commerceShippingContainer = new CommerceShippingContainer();
        commerceShippingContainer.setId(id);
        return commerceShippingContainer;
    }
}
