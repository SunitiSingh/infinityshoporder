package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceShipPriceNgDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceShipPriceNg and its DTO CommerceShipPriceNgDTO.
 */
@Mapper(componentModel = "spring", uses = {CommerceShipContainerPriceMapper.class})
public interface CommerceShipPriceNgMapper extends EntityMapper<CommerceShipPriceNgDTO, CommerceShipPriceNg> {

    @Mapping(source = "commerceShipContainerPrice.id", target = "commerceShipContainerPriceId")
    CommerceShipPriceNgDTO toDto(CommerceShipPriceNg commerceShipPriceNg);

    @Mapping(source = "commerceShipContainerPriceId", target = "commerceShipContainerPrice")
    CommerceShipPriceNg toEntity(CommerceShipPriceNgDTO commerceShipPriceNgDTO);

    default CommerceShipPriceNg fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceShipPriceNg commerceShipPriceNg = new CommerceShipPriceNg();
        commerceShipPriceNg.setId(id);
        return commerceShipPriceNg;
    }
}
