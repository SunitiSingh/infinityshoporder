package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceOrder and its DTO CommerceOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommerceOrderMapper extends EntityMapper<CommerceOrderDTO, CommerceOrder> {


    @Mapping(target = "prices", ignore = true)
    @Mapping(target = "shipcontainers", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "payments", ignore = true)
    CommerceOrder toEntity(CommerceOrderDTO commerceOrderDTO);

    default CommerceOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceOrder commerceOrder = new CommerceOrder();
        commerceOrder.setId(id);
        return commerceOrder;
    }
}
