package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceOrderPriceNgDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceOrderPriceNg and its DTO CommerceOrderPriceNgDTO.
 */
@Mapper(componentModel = "spring", uses = {CommerceOrderPriceMapper.class})
public interface CommerceOrderPriceNgMapper extends EntityMapper<CommerceOrderPriceNgDTO, CommerceOrderPriceNg> {

    @Mapping(source = "commerceOrderPrice.id", target = "commerceOrderPriceId")
    CommerceOrderPriceNgDTO toDto(CommerceOrderPriceNg commerceOrderPriceNg);

    @Mapping(source = "commerceOrderPriceId", target = "commerceOrderPrice")
    CommerceOrderPriceNg toEntity(CommerceOrderPriceNgDTO commerceOrderPriceNgDTO);

    default CommerceOrderPriceNg fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceOrderPriceNg commerceOrderPriceNg = new CommerceOrderPriceNg();
        commerceOrderPriceNg.setId(id);
        return commerceOrderPriceNg;
    }
}
