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
 * A CommerceShipContainerPrice.
 */
@Entity
@Table(name = "commerce_ship_container_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommerceShipContainerPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "commerceShipContainerPrice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommerceShipPriceNg> ngs = new HashSet<>();

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

    public CommerceShipContainerPrice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<CommerceShipPriceNg> getNgs() {
        return ngs;
    }

    public CommerceShipContainerPrice ngs(Set<CommerceShipPriceNg> commerceShipPriceNgs) {
        this.ngs = commerceShipPriceNgs;
        return this;
    }

    public CommerceShipContainerPrice addNgs(CommerceShipPriceNg commerceShipPriceNg) {
        this.ngs.add(commerceShipPriceNg);
        commerceShipPriceNg.setCommerceShipContainerPrice(this);
        return this;
    }

    public CommerceShipContainerPrice removeNgs(CommerceShipPriceNg commerceShipPriceNg) {
        this.ngs.remove(commerceShipPriceNg);
        commerceShipPriceNg.setCommerceShipContainerPrice(null);
        return this;
    }

    public void setNgs(Set<CommerceShipPriceNg> commerceShipPriceNgs) {
        this.ngs = commerceShipPriceNgs;
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
        CommerceShipContainerPrice commerceShipContainerPrice = (CommerceShipContainerPrice) o;
        if (commerceShipContainerPrice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceShipContainerPrice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceShipContainerPrice{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            "}";
    }
}
