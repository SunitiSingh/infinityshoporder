package com.is.order.base.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CommerceItem entity.
 */
public class CommerceItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String skuid;

    @NotNull
    private Integer quantity;

    @NotNull
    private ZonedDateTime creationDate;

    private ZonedDateTime updateDate;

    private Long commerceOrderId;

    private Long priceId;

    private Set<CommerceShippingContainerDTO> shipcontainers = new HashSet<>();

    private Set<CommerceOrderPaymentDTO> payments = new HashSet<>();

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

    public Long getCommerceOrderId() {
        return commerceOrderId;
    }

    public void setCommerceOrderId(Long commerceOrderId) {
        this.commerceOrderId = commerceOrderId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long commerceItemPriceId) {
        this.priceId = commerceItemPriceId;
    }

    public Set<CommerceShippingContainerDTO> getShipcontainers() {
        return shipcontainers;
    }

    public void setShipcontainers(Set<CommerceShippingContainerDTO> commerceShippingContainers) {
        this.shipcontainers = commerceShippingContainers;
    }

    public Set<CommerceOrderPaymentDTO> getPayments() {
        return payments;
    }

    public void setPayments(Set<CommerceOrderPaymentDTO> commerceOrderPayments) {
        this.payments = commerceOrderPayments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommerceItemDTO commerceItemDTO = (CommerceItemDTO) o;
        if (commerceItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceItemDTO{" +
            "id=" + getId() +
            ", skuid='" + getSkuid() + "'" +
            ", quantity=" + getQuantity() +
            ", creationDate='" + getCreationDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", commerceOrder=" + getCommerceOrderId() +
            ", price=" + getPriceId() +
            "}";
    }
}
