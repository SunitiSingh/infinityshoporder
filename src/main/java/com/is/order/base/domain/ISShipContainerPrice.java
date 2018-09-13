package com.is.order.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ISShipContainerPrice.
 */
@Entity
@Table(name = "is_ship_container_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ISShipContainerPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "iSShipContainerPrice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ISShipPriceNegotiation> negotiations = new HashSet<>();

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

    public ISShipContainerPrice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<ISShipPriceNegotiation> getNegotiations() {
        return negotiations;
    }

    public ISShipContainerPrice negotiations(Set<ISShipPriceNegotiation> iSShipPriceNegotiations) {
        this.negotiations = iSShipPriceNegotiations;
        return this;
    }

    public ISShipContainerPrice addNegotiations(ISShipPriceNegotiation iSShipPriceNegotiation) {
        this.negotiations.add(iSShipPriceNegotiation);
        iSShipPriceNegotiation.setISShipContainerPrice(this);
        return this;
    }

    public ISShipContainerPrice removeNegotiations(ISShipPriceNegotiation iSShipPriceNegotiation) {
        this.negotiations.remove(iSShipPriceNegotiation);
        iSShipPriceNegotiation.setISShipContainerPrice(null);
        return this;
    }

    public void setNegotiations(Set<ISShipPriceNegotiation> iSShipPriceNegotiations) {
        this.negotiations = iSShipPriceNegotiations;
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
        ISShipContainerPrice iSShipContainerPrice = (ISShipContainerPrice) o;
        if (iSShipContainerPrice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSShipContainerPrice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISShipContainerPrice{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            "}";
    }
}
