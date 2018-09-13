package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.ISItemPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ISItemPrice and its DTO ISItemPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ISItemPriceMapper extends EntityMapper<ISItemPriceDTO, ISItemPrice> {


    @Mapping(target = "negotiations", ignore = true)
    ISItemPrice toEntity(ISItemPriceDTO iSItemPriceDTO);

    default ISItemPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        ISItemPrice iSItemPrice = new ISItemPrice();
        iSItemPrice.setId(id);
        return iSItemPrice;
    }
}
