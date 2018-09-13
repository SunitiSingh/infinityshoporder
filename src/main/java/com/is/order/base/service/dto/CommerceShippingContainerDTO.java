package com.is.order.base.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.is.order.base.domain.enumeration.CommerceShipStatus;

/**
 * A DTO for the CommerceShippingContainer entity.
 */
public class CommerceShippingContainerDTO implements Serializable {

    private Long id;

    @NotNull
    private CommerceShipStatus shipstatus;

    private String carrier;

    @NotNull
    private ZonedDateTime creationDate;

    private ZonedDateTime updateDate;

    private Long commerceOrderId;

    private Long priceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommerceShipStatus getShipstatus() {
        return shipstatus;
    }

    public void setShipstatus(CommerceShipStatus shipstatus) {
        this.shipstatus = shipstatus;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
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

    public void setPriceId(Long commerceShipContainerPriceId) {
        this.priceId = commerceShipContainerPriceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommerceShippingContainerDTO commerceShippingContainerDTO = (CommerceShippingContainerDTO) o;
        if (commerceShippingContainerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceShippingContainerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceShippingContainerDTO{" +
            "id=" + getId() +
            ", shipstatus='" + getShipstatus() + "'" +
            ", carrier='" + getCarrier() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", commerceOrder=" + getCommerceOrderId() +
            ", price=" + getPriceId() +
            "}";
    }
}
