package com.is.order.base.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the CommerceOrderPrice entity.
 */
public class CommerceOrderPriceDTO implements Serializable {

    private Long id;

    private String name;

    @NotNull
    private BigDecimal price;

    private Long commerceOrderId;

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

    public Long getCommerceOrderId() {
        return commerceOrderId;
    }

    public void setCommerceOrderId(Long commerceOrderId) {
        this.commerceOrderId = commerceOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommerceOrderPriceDTO commerceOrderPriceDTO = (CommerceOrderPriceDTO) o;
        if (commerceOrderPriceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceOrderPriceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceOrderPriceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", commerceOrder=" + getCommerceOrderId() +
            "}";
    }
}
