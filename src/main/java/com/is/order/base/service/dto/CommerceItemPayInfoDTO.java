package com.is.order.base.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.is.order.base.domain.enumeration.ItemPayStatus;

/**
 * A DTO for the CommerceItemPayInfo entity.
 */
public class CommerceItemPayInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private ItemPayStatus status;

    @NotNull
    private Integer quantity;

    private Long orderPaymentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemPayStatus getStatus() {
        return status;
    }

    public void setStatus(ItemPayStatus status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getOrderPaymentId() {
        return orderPaymentId;
    }

    public void setOrderPaymentId(Long commerceOrderPaymentId) {
        this.orderPaymentId = commerceOrderPaymentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommerceItemPayInfoDTO commerceItemPayInfoDTO = (CommerceItemPayInfoDTO) o;
        if (commerceItemPayInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceItemPayInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceItemPayInfoDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", quantity=" + getQuantity() +
            ", orderPayment=" + getOrderPaymentId() +
            "}";
    }
}
