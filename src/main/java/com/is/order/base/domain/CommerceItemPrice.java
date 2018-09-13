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
 * A CommerceItemPrice.
 */
@Entity
@Table(name = "commerce_item_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommerceItemPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "commerceItemPrice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommerceItemPriceNg> ngs = new HashSet<>();

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

    public CommerceItemPrice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<CommerceItemPriceNg> getNgs() {
        return ngs;
    }

    public CommerceItemPrice ngs(Set<CommerceItemPriceNg> commerceItemPriceNgs) {
        this.ngs = commerceItemPriceNgs;
        return this;
    }

    public CommerceItemPrice addNgs(CommerceItemPriceNg commerceItemPriceNg) {
        this.ngs.add(commerceItemPriceNg);
        commerceItemPriceNg.setCommerceItemPrice(this);
        return this;
    }

    public CommerceItemPrice removeNgs(CommerceItemPriceNg commerceItemPriceNg) {
        this.ngs.remove(commerceItemPriceNg);
        commerceItemPriceNg.setCommerceItemPrice(null);
        return this;
    }

    public void setNgs(Set<CommerceItemPriceNg> commerceItemPriceNgs) {
        this.ngs = commerceItemPriceNgs;
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
        CommerceItemPrice commerceItemPrice = (CommerceItemPrice) o;
        if (commerceItemPrice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceItemPrice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceItemPrice{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            "}";
    }
}
