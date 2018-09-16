package com.is.order.base.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.is.order.base.domain.enumeration.ItemShipStatus;

/**
 * A DTO for the CommerceItemShipInfo entity.
 */
public class CommerceItemShipInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private ItemShipStatus status;

    @NotNull
    private Integer quantity;

    private Long shipContainerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemShipStatus getStatus() {
        return status;
    }

    public void setStatus(ItemShipStatus status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getShipContainerId() {
        return shipContainerId;
    }

    public void setShipContainerId(Long commerceShippingContainerId) {
        this.shipContainerId = commerceShippingContainerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommerceItemShipInfoDTO commerceItemShipInfoDTO = (CommerceItemShipInfoDTO) o;
        if (commerceItemShipInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceItemShipInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceItemShipInfoDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", quantity=" + getQuantity() +
            ", shipContainer=" + getShipContainerId() +
            "}";
    }
}
