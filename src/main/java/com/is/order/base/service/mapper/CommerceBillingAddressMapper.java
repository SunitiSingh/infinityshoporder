package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceBillingAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceBillingAddress and its DTO CommerceBillingAddressDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommerceBillingAddressMapper extends EntityMapper<CommerceBillingAddressDTO, CommerceBillingAddress> {



    default CommerceBillingAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceBillingAddress commerceBillingAddress = new CommerceBillingAddress();
        commerceBillingAddress.setId(id);
        return commerceBillingAddress;
    }
}
