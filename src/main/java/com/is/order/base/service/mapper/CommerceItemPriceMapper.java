package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceItemPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceItemPrice and its DTO CommerceItemPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommerceItemPriceMapper extends EntityMapper<CommerceItemPriceDTO, CommerceItemPrice> {


    @Mapping(target = "ngs", ignore = true)
    CommerceItemPrice toEntity(CommerceItemPriceDTO commerceItemPriceDTO);

    default CommerceItemPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceItemPrice commerceItemPrice = new CommerceItemPrice();
        commerceItemPrice.setId(id);
        return commerceItemPrice;
    }
}
