package com.is.order.base.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the CommerceShipContainerPrice entity.
 */
public class CommerceShipContainerPriceDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal price;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommerceShipContainerPriceDTO commerceShipContainerPriceDTO = (CommerceShipContainerPriceDTO) o;
        if (commerceShipContainerPriceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceShipContainerPriceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceShipContainerPriceDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            "}";
    }
}
