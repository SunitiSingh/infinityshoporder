package com.is.order.base.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.is.order.base.domain.enumeration.ISShipStatus;

/**
 * A DTO for the ISShippingContainer entity.
 */
public class ISShippingContainerDTO implements Serializable {

    private Long id;

    @NotNull
    private ISShipStatus shipstatus;

    private String carrier;

    @NotNull
    private ZonedDateTime creationDate;

    private ZonedDateTime updateDate;

    private Long iSOrderId;

    private Long priceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ISShipStatus getShipstatus() {
        return shipstatus;
    }

    public void setShipstatus(ISShipStatus shipstatus) {
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

    public Long getISOrderId() {
        return iSOrderId;
    }

    public void setISOrderId(Long iSOrderId) {
        this.iSOrderId = iSOrderId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long iSShipContainerPriceId) {
        this.priceId = iSShipContainerPriceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ISShippingContainerDTO iSShippingContainerDTO = (ISShippingContainerDTO) o;
        if (iSShippingContainerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSShippingContainerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISShippingContainerDTO{" +
            "id=" + getId() +
            ", shipstatus='" + getShipstatus() + "'" +
            ", carrier='" + getCarrier() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", iSOrder=" + getISOrderId() +
            ", price=" + getPriceId() +
            "}";
    }
}
