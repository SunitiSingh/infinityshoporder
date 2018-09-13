package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceItemPriceNgDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceItemPriceNg and its DTO CommerceItemPriceNgDTO.
 */
@Mapper(componentModel = "spring", uses = {CommerceItemPriceMapper.class})
public interface CommerceItemPriceNgMapper extends EntityMapper<CommerceItemPriceNgDTO, CommerceItemPriceNg> {

    @Mapping(source = "commerceItemPrice.id", target = "commerceItemPriceId")
    CommerceItemPriceNgDTO toDto(CommerceItemPriceNg commerceItemPriceNg);

    @Mapping(source = "commerceItemPriceId", target = "commerceItemPrice")
    CommerceItemPriceNg toEntity(CommerceItemPriceNgDTO commerceItemPriceNgDTO);

    default CommerceItemPriceNg fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceItemPriceNg commerceItemPriceNg = new CommerceItemPriceNg();
        commerceItemPriceNg.setId(id);
        return commerceItemPriceNg;
    }
}
