package com.is.order.base.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the CommerceOrderPriceNg entity.
 */
public class CommerceOrderPriceNgDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal price;

    private String ngid;

    private Long commerceOrderPriceId;

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

    public String getNgid() {
        return ngid;
    }

    public void setNgid(String ngid) {
        this.ngid = ngid;
    }

    public Long getCommerceOrderPriceId() {
        return commerceOrderPriceId;
    }

    public void setCommerceOrderPriceId(Long commerceOrderPriceId) {
        this.commerceOrderPriceId = commerceOrderPriceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommerceOrderPriceNgDTO commerceOrderPriceNgDTO = (CommerceOrderPriceNgDTO) o;
        if (commerceOrderPriceNgDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceOrderPriceNgDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceOrderPriceNgDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", ngid='" + getNgid() + "'" +
            ", commerceOrderPrice=" + getCommerceOrderPriceId() +
            "}";
    }
}
