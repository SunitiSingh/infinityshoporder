package com.is.order.base.service.mapper;

import com.is.order.base.domain.*;
import com.is.order.base.service.dto.CommerceItemShipInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommerceItemShipInfo and its DTO CommerceItemShipInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {CommerceShippingContainerMapper.class})
public interface CommerceItemShipInfoMapper extends EntityMapper<CommerceItemShipInfoDTO, CommerceItemShipInfo> {

    @Mapping(source = "shipContainer.id", target = "shipContainerId")
    CommerceItemShipInfoDTO toDto(CommerceItemShipInfo commerceItemShipInfo);

    @Mapping(source = "shipContainerId", target = "shipContainer")
    CommerceItemShipInfo toEntity(CommerceItemShipInfoDTO commerceItemShipInfoDTO);

    default CommerceItemShipInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommerceItemShipInfo commerceItemShipInfo = new CommerceItemShipInfo();
        commerceItemShipInfo.setId(id);
        return commerceItemShipInfo;
    }
}
