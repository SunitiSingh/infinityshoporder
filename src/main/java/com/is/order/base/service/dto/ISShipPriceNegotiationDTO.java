package com.is.order.base.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the ISShipPriceNegotiation entity.
 */
public class ISShipPriceNegotiationDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal price;

    private Long iSShipContainerPriceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getISShipContainerPriceId() {
        return iSShipContainerPriceId;
    }

    public void setISShipContainerPriceId(Long iSShipContainerPriceId) {
        this.iSShipContainerPriceId = iSShipContainerPriceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ISShipPriceNegotiationDTO iSShipPriceNegotiationDTO = (ISShipPriceNegotiationDTO) o;
        if (iSShipPriceNegotiationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSShipPriceNegotiationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISShipPriceNegotiationDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", iSShipContainerPrice=" + getISShipContainerPriceId() +
            "}";
    }
}
