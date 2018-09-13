package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.ISEPayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ISEPay and its DTO ISEPayDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ISEPayMapper extends EntityMapper<ISEPayDTO, ISEPay> {



    default ISEPay fromId(Long id) {
        if (id == null) {
            return null;
        }
        ISEPay iSEPay = new ISEPay();
        iSEPay.setId(id);
        return iSEPay;
    }
}
