package com.is.order.base.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the CommerceShipPriceNg entity.
 */
public class CommerceShipPriceNgDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal price;

    private Long commerceShipContainerPriceId;

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

    public Long getCommerceShipContainerPriceId() {
        return commerceShipContainerPriceId;
    }

    public void setCommerceShipContainerPriceId(Long commerceShipContainerPriceId) {
        this.commerceShipContainerPriceId = commerceShipContainerPriceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommerceShipPriceNgDTO commerceShipPriceNgDTO = (CommerceShipPriceNgDTO) o;
        if (commerceShipPriceNgDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceShipPriceNgDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceShipPriceNgDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", commerceShipContainerPrice=" + getCommerceShipContainerPriceId() +
            "}";
    }
}
