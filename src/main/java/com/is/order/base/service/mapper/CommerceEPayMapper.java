package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceEPayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceEPay and its DTO CommerceEPayDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommerceEPayMapper extends EntityMapper<CommerceEPayDTO, CommerceEPay> {



    default CommerceEPay fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceEPay commerceEPay = new CommerceEPay();
        commerceEPay.setId(id);
        return commerceEPay;
    }
}
