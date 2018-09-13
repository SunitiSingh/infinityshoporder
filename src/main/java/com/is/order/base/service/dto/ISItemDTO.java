package com.is.order.base.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ISItem entity.
 */
public class ISItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String skuid;

    @NotNull
    private Integer quantity;

    @NotNull
    private ZonedDateTime creationDate;

    private ZonedDateTime updateDate;

    private Long iSOrderId;

    private Long priceId;

    private Set<ISShippingContainerDTO> shipcontainers = new HashSet<>();

    private Set<ISOrderPaymentDTO> payments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public Long getISOrderId() {
        return iSOrderId;
    }

    public void setISOrderId(Long iSOrderId) {
        this.iSOrderId = iSOrderId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long iSItemPriceId) {
        this.priceId = iSItemPriceId;
    }

    public Set<ISShippingContainerDTO> getShipcontainers() {
        return shipcontainers;
    }

    public void setShipcontainers(Set<ISShippingContainerDTO> iSShippingContainers) {
        this.shipcontainers = iSShippingContainers;
    }

    public Set<ISOrderPaymentDTO> getPayments() {
        return payments;
    }

    public void setPayments(Set<ISOrderPaymentDTO> iSOrderPayments) {
        this.payments = iSOrderPayments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ISItemDTO iSItemDTO = (ISItemDTO) o;
        if (iSItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISItemDTO{" +
            "id=" + getId() +
            ", skuid='" + getSkuid() + "'" +
            ", quantity=" + getQuantity() +
            ", creationDate='" + getCreationDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", iSOrder=" + getISOrderId() +
            ", price=" + getPriceId() +
            "}";
    }
}
