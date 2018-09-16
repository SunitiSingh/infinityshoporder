package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceItem and its DTO CommerceItemDTO.
 */
@Mapper(componentModel = "spring", uses = {CommerceOrderMapper.class, CommerceItemPriceMapper.class, CommerceItemShipInfoMapper.class, CommerceItemPayInfoMapper.class, CommerceShippingContainerMapper.class, CommerceOrderPaymentMapper.class})
public interface CommerceItemMapper extends EntityMapper<CommerceItemDTO, CommerceItem> {

    @Mapping(source = "commerceOrder.id", target = "commerceOrderId")
    @Mapping(source = "price.id", target = "priceId")
    @Mapping(source = "shipInfo.id", target = "shipInfoId")
    @Mapping(source = "payInfo.id", target = "payInfoId")
    CommerceItemDTO toDto(CommerceItem commerceItem);

    @Mapping(source = "commerceOrderId", target = "commerceOrder")
    @Mapping(source = "priceId", target = "price")
    @Mapping(source = "shipInfoId", target = "shipInfo")
    @Mapping(source = "payInfoId", target = "payInfo")
    CommerceItem toEntity(CommerceItemDTO commerceItemDTO);

    default CommerceItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceItem commerceItem = new CommerceItem();
        commerceItem.setId(id);
        return commerceItem;
    }
}
