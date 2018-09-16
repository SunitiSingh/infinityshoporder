package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceItemPayInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceItemPayInfo and its DTO CommerceItemPayInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {CommerceOrderPaymentMapper.class})
public interface CommerceItemPayInfoMapper extends EntityMapper<CommerceItemPayInfoDTO, CommerceItemPayInfo> {

    @Mapping(source = "orderPayment.id", target = "orderPaymentId")
    CommerceItemPayInfoDTO toDto(CommerceItemPayInfo commerceItemPayInfo);

    @Mapping(source = "orderPaymentId", target = "orderPayment")
    CommerceItemPayInfo toEntity(CommerceItemPayInfoDTO commerceItemPayInfoDTO);

    default CommerceItemPayInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceItemPayInfo commerceItemPayInfo = new CommerceItemPayInfo();
        commerceItemPayInfo.setId(id);
        return commerceItemPayInfo;
    }
}
