package com.is.order.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A CommerceOrderPriceNg.
 */
@Entity
@Table(name = "commerce_order_price_ng")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommerceOrderPriceNg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "ngid")
    private String ngid;

    @ManyToOne
    @JsonIgnoreProperties("ngs")
    private CommerceOrderPrice commerceOrderPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CommerceOrderPriceNg price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNgid() {
        return ngid;
    }

    public CommerceOrderPriceNg ngid(String ngid) {
        this.ngid = ngid;
        return this;
    }

    public void setNgid(String ngid) {
        this.ngid = ngid;
    }

    public CommerceOrderPrice getCommerceOrderPrice() {
        return commerceOrderPrice;
    }

    public CommerceOrderPriceNg commerceOrderPrice(CommerceOrderPrice commerceOrderPrice) {
        this.commerceOrderPrice = commerceOrderPrice;
        return this;
    }

    public void setCommerceOrderPrice(CommerceOrderPrice commerceOrderPrice) {
        this.commerceOrderPrice = commerceOrderPrice;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommerceOrderPriceNg commerceOrderPriceNg = (CommerceOrderPriceNg) o;
        if (commerceOrderPriceNg.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceOrderPriceNg.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceOrderPriceNg{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", ngid='" + getNgid() + "'" +
            "}";
    }
}
