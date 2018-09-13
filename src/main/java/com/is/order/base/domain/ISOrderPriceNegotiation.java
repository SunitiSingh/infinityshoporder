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
 * A ISOrderPriceNegotiation.
 */
@Entity
@Table(name = "is_order_price_negotiation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ISOrderPriceNegotiation implements Serializable {

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
    private ISOrderPrice iSOrderPrice;

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

    public ISOrderPriceNegotiation price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNegotiationid() {
        return negotiationid;
    }

    public ISOrderPriceNegotiation negotiationid(String negotiationid) {
        this.negotiationid = negotiationid;
        return this;
    }

    public void setNegotiationid(String negotiationid) {
        this.negotiationid = negotiationid;
    }

    public ISOrderPrice getISOrderPrice() {
        return iSOrderPrice;
    }

    public ISOrderPriceNegotiation iSOrderPrice(ISOrderPrice iSOrderPrice) {
        this.iSOrderPrice = iSOrderPrice;
        return this;
    }

    public void setISOrderPrice(ISOrderPrice iSOrderPrice) {
        this.iSOrderPrice = iSOrderPrice;
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
        ISOrderPriceNegotiation iSOrderPriceNegotiation = (ISOrderPriceNegotiation) o;
        if (iSOrderPriceNegotiation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSOrderPriceNegotiation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISOrderPriceNegotiation{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", negotiationid='" + getNegotiationid() + "'" +
            "}";
    }
}
