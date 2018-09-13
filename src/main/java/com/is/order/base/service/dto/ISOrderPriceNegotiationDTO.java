package com.is.order.base.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the ISOrderPriceNegotiation entity.
 */
public class ISOrderPriceNegotiationDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal price;

    private String negotiationid;

    private Long iSOrderPriceId;

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

    public String getNegotiationid() {
        return negotiationid;
    }

    public void setNegotiationid(String negotiationid) {
        this.negotiationid = negotiationid;
    }

    public Long getISOrderPriceId() {
        return iSOrderPriceId;
    }

    public void setISOrderPriceId(Long iSOrderPriceId) {
        this.iSOrderPriceId = iSOrderPriceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ISOrderPriceNegotiationDTO iSOrderPriceNegotiationDTO = (ISOrderPriceNegotiationDTO) o;
        if (iSOrderPriceNegotiationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSOrderPriceNegotiationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISOrderPriceNegotiationDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", negotiationid='" + getNegotiationid() + "'" +
            ", iSOrderPrice=" + getISOrderPriceId() +
            "}";
    }
}
