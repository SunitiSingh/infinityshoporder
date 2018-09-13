package com.is.order.base.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the ISOrderPrice entity.
 */
public class ISOrderPriceDTO implements Serializable {

    private Long id;

    private String name;

    @NotNull
    private BigDecimal price;

    private Long iSOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getISOrderId() {
        return iSOrderId;
    }

    public void setISOrderId(Long iSOrderId) {
        this.iSOrderId = iSOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ISOrderPriceDTO iSOrderPriceDTO = (ISOrderPriceDTO) o;
        if (iSOrderPriceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSOrderPriceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISOrderPriceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", iSOrder=" + getISOrderId() +
            "}";
    }
}
