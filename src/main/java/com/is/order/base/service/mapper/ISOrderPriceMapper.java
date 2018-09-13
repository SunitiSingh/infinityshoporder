package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.ISOrderPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ISOrderPrice and its DTO ISOrderPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {ISOrderMapper.class})
public interface ISOrderPriceMapper extends EntityMapper<ISOrderPriceDTO, ISOrderPrice> {

    @Mapping(source = "ISOrder.id", target = "ISOrderId")
    ISOrderPriceDTO toDto(ISOrderPrice iSOrderPrice);

    @Mapping(source = "ISOrderId", target = "ISOrder")
    @Mapping(target = "negotiations", ignore = true)
    ISOrderPrice toEntity(ISOrderPriceDTO iSOrderPriceDTO);

    default ISOrderPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        ISOrderPrice iSOrderPrice = new ISOrderPrice();
        iSOrderPrice.setId(id);
        return iSOrderPrice;
    }
}
