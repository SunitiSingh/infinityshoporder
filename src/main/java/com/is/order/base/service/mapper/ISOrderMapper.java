package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.ISOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ISOrder and its DTO ISOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ISOrderMapper extends EntityMapper<ISOrderDTO, ISOrder> {


    @Mapping(target = "prices", ignore = true)
    @Mapping(target = "shipcontainers", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "payments", ignore = true)
    ISOrder toEntity(ISOrderDTO iSOrderDTO);

    default ISOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        ISOrder iSOrder = new ISOrder();
        iSOrder.setId(id);
        return iSOrder;
    }
}
