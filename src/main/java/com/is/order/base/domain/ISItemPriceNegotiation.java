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
 * A ISItemPriceNegotiation.
 */
@Entity
@Table(name = "is_item_price_negotiation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ISItemPriceNegotiation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "negotiationid")
    private String negotiationid;

    @ManyToOne
    @JsonIgnoreProperties("negotiations")
    private ISItemPrice iSItemPrice;

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

    public ISItemPriceNegotiation price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNegotiationid() {
        return negotiationid;
    }

    public ISItemPriceNegotiation negotiationid(String negotiationid) {
        this.negotiationid = negotiationid;
        return this;
    }

    public void setNegotiationid(String negotiationid) {
        this.negotiationid = negotiationid;
    }

    public ISItemPrice getISItemPrice() {
        return iSItemPrice;
    }

    public ISItemPriceNegotiation iSItemPrice(ISItemPrice iSItemPrice) {
        this.iSItemPrice = iSItemPrice;
        return this;
    }

    public void setISItemPrice(ISItemPrice iSItemPrice) {
        this.iSItemPrice = iSItemPrice;
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
        ISItemPriceNegotiation iSItemPriceNegotiation = (ISItemPriceNegotiation) o;
        if (iSItemPriceNegotiation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSItemPriceNegotiation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISItemPriceNegotiation{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", negotiationid='" + getNegotiationid() + "'" +
            "}";
    }
}
