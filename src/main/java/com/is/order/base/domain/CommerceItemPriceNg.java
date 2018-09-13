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
 * A CommerceItemPriceNg.
 */
@Entity
@Table(name = "commerce_item_price_ng")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommerceItemPriceNg implements Serializable {

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
    private CommerceItemPrice commerceItemPrice;

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

    public CommerceItemPriceNg price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNgid() {
        return ngid;
    }

    public CommerceItemPriceNg ngid(String ngid) {
        this.ngid = ngid;
        return this;
    }

    public void setNgid(String ngid) {
        this.ngid = ngid;
    }

    public CommerceItemPrice getCommerceItemPrice() {
        return commerceItemPrice;
    }

    public CommerceItemPriceNg commerceItemPrice(CommerceItemPrice commerceItemPrice) {
        this.commerceItemPrice = commerceItemPrice;
        return this;
    }

    public void setCommerceItemPrice(CommerceItemPrice commerceItemPrice) {
        this.commerceItemPrice = commerceItemPrice;
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
        CommerceItemPriceNg commerceItemPriceNg = (CommerceItemPriceNg) o;
        if (commerceItemPriceNg.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceItemPriceNg.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceItemPriceNg{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", ngid='" + getNgid() + "'" +
            "}";
    }
}
