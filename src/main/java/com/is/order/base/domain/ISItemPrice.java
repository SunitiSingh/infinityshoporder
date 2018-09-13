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
 * A ISItemPrice.
 */
@Entity
@Table(name = "is_item_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ISItemPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "iSItemPrice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ISItemPriceNegotiation> negotiations = new HashSet<>();

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

    public ISItemPrice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<ISItemPriceNegotiation> getNegotiations() {
        return negotiations;
    }

    public ISItemPrice negotiations(Set<ISItemPriceNegotiation> iSItemPriceNegotiations) {
        this.negotiations = iSItemPriceNegotiations;
        return this;
    }

    public ISItemPrice addNegotiations(ISItemPriceNegotiation iSItemPriceNegotiation) {
        this.negotiations.add(iSItemPriceNegotiation);
        iSItemPriceNegotiation.setISItemPrice(this);
        return this;
    }

    public ISItemPrice removeNegotiations(ISItemPriceNegotiation iSItemPriceNegotiation) {
        this.negotiations.remove(iSItemPriceNegotiation);
        iSItemPriceNegotiation.setISItemPrice(null);
        return this;
    }

    public void setNegotiations(Set<ISItemPriceNegotiation> iSItemPriceNegotiations) {
        this.negotiations = iSItemPriceNegotiations;
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
        ISItemPrice iSItemPrice = (ISItemPrice) o;
        if (iSItemPrice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSItemPrice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISItemPrice{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            "}";
    }
}
