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
 * A ISOrderPrice.
 */
@Entity
@Table(name = "is_order_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ISOrderPrice implements Serializable {

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
    private ISOrder iSOrder;

    @OneToMany(mappedBy = "iSOrderPrice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ISOrderPriceNegotiation> negotiations = new HashSet<>();

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

    public ISOrderPrice name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ISOrderPrice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ISOrder getISOrder() {
        return iSOrder;
    }

    public ISOrderPrice iSOrder(ISOrder iSOrder) {
        this.iSOrder = iSOrder;
        return this;
    }

    public void setISOrder(ISOrder iSOrder) {
        this.iSOrder = iSOrder;
    }

    public Set<ISOrderPriceNegotiation> getNegotiations() {
        return negotiations;
    }

    public ISOrderPrice negotiations(Set<ISOrderPriceNegotiation> iSOrderPriceNegotiations) {
        this.negotiations = iSOrderPriceNegotiations;
        return this;
    }

    public ISOrderPrice addNegotiations(ISOrderPriceNegotiation iSOrderPriceNegotiation) {
        this.negotiations.add(iSOrderPriceNegotiation);
        iSOrderPriceNegotiation.setISOrderPrice(this);
        return this;
    }

    public ISOrderPrice removeNegotiations(ISOrderPriceNegotiation iSOrderPriceNegotiation) {
        this.negotiations.remove(iSOrderPriceNegotiation);
        iSOrderPriceNegotiation.setISOrderPrice(null);
        return this;
    }

    public void setNegotiations(Set<ISOrderPriceNegotiation> iSOrderPriceNegotiations) {
        this.negotiations = iSOrderPriceNegotiations;
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
        ISOrderPrice iSOrderPrice = (ISOrderPrice) o;
        if (iSOrderPrice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSOrderPrice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISOrderPrice{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
