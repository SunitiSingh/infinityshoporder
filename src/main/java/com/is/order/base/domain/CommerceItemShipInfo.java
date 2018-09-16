package com.is.order.base.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.is.order.base.domain.enumeration.ItemShipStatus;

/**
 * A CommerceItemShipInfo.
 */
@Entity
@Table(name = "commerce_item_ship_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommerceItemShipInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ItemShipStatus status;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @OneToOne
    @JoinColumn(unique = true)
    private CommerceShippingContainer shipContainer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemShipStatus getStatus() {
        return status;
    }

    public CommerceItemShipInfo status(ItemShipStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ItemShipStatus status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CommerceItemShipInfo quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public CommerceShippingContainer getShipContainer() {
        return shipContainer;
    }

    public CommerceItemShipInfo shipContainer(CommerceShippingContainer commerceShippingContainer) {
        this.shipContainer = commerceShippingContainer;
        return this;
    }

    public void setShipContainer(CommerceShippingContainer commerceShippingContainer) {
        this.shipContainer = commerceShippingContainer;
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
        CommerceItemShipInfo commerceItemShipInfo = (CommerceItemShipInfo) o;
        if (commerceItemShipInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceItemShipInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceItemShipInfo{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
