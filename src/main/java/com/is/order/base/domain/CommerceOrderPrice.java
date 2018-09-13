package com.is.order.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A CommerceOrderPrice.
 */
@Entity
@Table(name = "commerce_order_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommerceOrderPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JsonIgnoreProperties("prices")
    private CommerceOrder commerceOrder;

    @OneToMany(mappedBy = "commerceOrderPrice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommerceOrderPriceNg> ngs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CommerceOrderPrice name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CommerceOrderPrice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CommerceOrder getCommerceOrder() {
        return commerceOrder;
    }

    public CommerceOrderPrice commerceOrder(CommerceOrder commerceOrder) {
        this.commerceOrder = commerceOrder;
        return this;
    }

    public void setCommerceOrder(CommerceOrder commerceOrder) {
        this.commerceOrder = commerceOrder;
    }

    public Set<CommerceOrderPriceNg> getNgs() {
        return ngs;
    }

    public CommerceOrderPrice ngs(Set<CommerceOrderPriceNg> commerceOrderPriceNgs) {
        this.ngs = commerceOrderPriceNgs;
        return this;
    }

    public CommerceOrderPrice addNgs(CommerceOrderPriceNg commerceOrderPriceNg) {
        this.ngs.add(commerceOrderPriceNg);
        commerceOrderPriceNg.setCommerceOrderPrice(this);
        return this;
    }

    public CommerceOrderPrice removeNgs(CommerceOrderPriceNg commerceOrderPriceNg) {
        this.ngs.remove(commerceOrderPriceNg);
        commerceOrderPriceNg.setCommerceOrderPrice(null);
        return this;
    }

    public void setNgs(Set<CommerceOrderPriceNg> commerceOrderPriceNgs) {
        this.ngs = commerceOrderPriceNgs;
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
        CommerceOrderPrice commerceOrderPrice = (CommerceOrderPrice) o;
        if (commerceOrderPrice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceOrderPrice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceOrderPrice{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
