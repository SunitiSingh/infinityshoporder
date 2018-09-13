package com.is.order.base.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the CommerceItemPriceNg entity.
 */
public class CommerceItemPriceNgDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal price;

    private String ngid;

    private Long commerceItemPriceId;

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

    public Long getCommerceItemPriceId() {
        return commerceItemPriceId;
    }

    public void setCommerceItemPriceId(Long commerceItemPriceId) {
        this.commerceItemPriceId = commerceItemPriceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommerceItemPriceNgDTO commerceItemPriceNgDTO = (CommerceItemPriceNgDTO) o;
        if (commerceItemPriceNgDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceItemPriceNgDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceItemPriceNgDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", ngid='" + getNgid() + "'" +
            ", commerceItemPrice=" + getCommerceItemPriceId() +
            "}";
    }
}
