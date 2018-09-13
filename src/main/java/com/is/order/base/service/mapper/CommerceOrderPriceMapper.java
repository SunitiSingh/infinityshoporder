package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceOrderPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceOrderPrice and its DTO CommerceOrderPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {CommerceOrderMapper.class})
public interface CommerceOrderPriceMapper extends EntityMapper<CommerceOrderPriceDTO, CommerceOrderPrice> {

    @Mapping(source = "commerceOrder.id", target = "commerceOrderId")
    CommerceOrderPriceDTO toDto(CommerceOrderPrice commerceOrderPrice);

    @Mapping(source = "commerceOrderId", target = "commerceOrder")
    @Mapping(target = "ngs", ignore = true)
    CommerceOrderPrice toEntity(CommerceOrderPriceDTO commerceOrderPriceDTO);

    default CommerceOrderPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceOrderPrice commerceOrderPrice = new CommerceOrderPrice();
        commerceOrderPrice.setId(id);
        return commerceOrderPrice;
    }
}
