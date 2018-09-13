package com.is.order.base.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the ISItemPriceNegotiation entity.
 */
public class ISItemPriceNegotiationDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal price;

    private String negotiationid;

    private Long iSItemPriceId;

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

    public Long getISItemPriceId() {
        return iSItemPriceId;
    }

    public void setISItemPriceId(Long iSItemPriceId) {
        this.iSItemPriceId = iSItemPriceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ISItemPriceNegotiationDTO iSItemPriceNegotiationDTO = (ISItemPriceNegotiationDTO) o;
        if (iSItemPriceNegotiationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSItemPriceNegotiationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISItemPriceNegotiationDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", negotiationid='" + getNegotiationid() + "'" +
            ", iSItemPrice=" + getISItemPriceId() +
            "}";
    }
}
