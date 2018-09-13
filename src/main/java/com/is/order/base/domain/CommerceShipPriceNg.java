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
 * A CommerceShipPriceNg.
 */
@Entity
@Table(name = "commerce_ship_price_ng")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommerceShipPriceNg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JsonIgnoreProperties("ngs")
    private CommerceShipContainerPrice commerceShipContainerPrice;

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

    public CommerceShipPriceNg price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CommerceShipContainerPrice getCommerceShipContainerPrice() {
        return commerceShipContainerPrice;
    }

    public CommerceShipPriceNg commerceShipContainerPrice(CommerceShipContainerPrice commerceShipContainerPrice) {
        this.commerceShipContainerPrice = commerceShipContainerPrice;
        return this;
    }

    public void setCommerceShipContainerPrice(CommerceShipContainerPrice commerceShipContainerPrice) {
        this.commerceShipContainerPrice = commerceShipContainerPrice;
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
        CommerceShipPriceNg commerceShipPriceNg = (CommerceShipPriceNg) o;
        if (commerceShipPriceNg.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceShipPriceNg.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceShipPriceNg{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            "}";
    }
}
