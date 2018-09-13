package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.ISBillingAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ISBillingAddress and its DTO ISBillingAddressDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ISBillingAddressMapper extends EntityMapper<ISBillingAddressDTO, ISBillingAddress> {



    default ISBillingAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        ISBillingAddress iSBillingAddress = new ISBillingAddress();
        iSBillingAddress.setId(id);
        return iSBillingAddress;
    }
}
