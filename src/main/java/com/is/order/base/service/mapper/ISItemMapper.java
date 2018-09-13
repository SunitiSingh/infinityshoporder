package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.ISItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ISItem and its DTO ISItemDTO.
 */
@Mapper(componentModel = "spring", uses = {ISOrderMapper.class, ISItemPriceMapper.class, ISShippingContainerMapper.class, ISOrderPaymentMapper.class})
public interface ISItemMapper extends EntityMapper<ISItemDTO, ISItem> {

    @Mapping(source = "ISOrder.id", target = "ISOrderId")
    @Mapping(source = "price.id", target = "priceId")
    ISItemDTO toDto(ISItem iSItem);

    @Mapping(source = "ISOrderId", target = "ISOrder")
    @Mapping(source = "priceId", target = "price")
    ISItem toEntity(ISItemDTO iSItemDTO);

    default ISItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        ISItem iSItem = new ISItem();
        iSItem.setId(id);
        return iSItem;
    }
}
