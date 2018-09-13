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
 * A ISShipPriceNegotiation.
 */
@Entity
@Table(name = "is_ship_price_negotiation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ISShipPriceNegotiation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JsonIgnoreProperties("negotiations")
    private ISShipContainerPrice iSShipContainerPrice;

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

    public ISShipPriceNegotiation price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ISShipContainerPrice getISShipContainerPrice() {
        return iSShipContainerPrice;
    }

    public ISShipPriceNegotiation iSShipContainerPrice(ISShipContainerPrice iSShipContainerPrice) {
        this.iSShipContainerPrice = iSShipContainerPrice;
        return this;
    }

    public void setISShipContainerPrice(ISShipContainerPrice iSShipContainerPrice) {
        this.iSShipContainerPrice = iSShipContainerPrice;
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
        ISShipPriceNegotiation iSShipPriceNegotiation = (ISShipPriceNegotiation) o;
        if (iSShipPriceNegotiation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSShipPriceNegotiation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISShipPriceNegotiation{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            "}";
    }
}
