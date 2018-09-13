package com.is.order.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.is.order.base.domain.enumeration.OrderStatus;

/**
 * The ISOrder entity.
 */
@ApiModel(description = "The ISOrder entity.")
@Entity
@Table(name = "is_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ISOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @NotNull
    @Column(name = "custid", nullable = false)
    private String custid;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "placed_date")
    private ZonedDateTime placedDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @OneToMany(mappedBy = "iSOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ISOrderPrice> prices = new HashSet<>();

    @OneToMany(mappedBy = "iSOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ISShippingContainer> shipcontainers = new HashSet<>();

    @OneToMany(mappedBy = "iSOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ISItem> items = new HashSet<>();

    @OneToMany(mappedBy = "iSOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ISOrderPayment> payments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ISOrder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getCustid() {
        return custid;
    }

    public ISOrder custid(String custid) {
        this.custid = custid;
        return this;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public ISOrder creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getPlacedDate() {
        return placedDate;
    }

    public ISOrder placedDate(ZonedDateTime placedDate) {
        this.placedDate = placedDate;
        return this;
    }

    public void setPlacedDate(ZonedDateTime placedDate) {
        this.placedDate = placedDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public ISOrder updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public Set<ISOrderPrice> getPrices() {
        return prices;
    }

    public ISOrder prices(Set<ISOrderPrice> iSOrderPrices) {
        this.prices = iSOrderPrices;
        return this;
    }

    public ISOrder addPrices(ISOrderPrice iSOrderPrice) {
        this.prices.add(iSOrderPrice);
        iSOrderPrice.setISOrder(this);
        return this;
    }

    public ISOrder removePrices(ISOrderPrice iSOrderPrice) {
        this.prices.remove(iSOrderPrice);
        iSOrderPrice.setISOrder(null);
        return this;
    }

    public void setPrices(Set<ISOrderPrice> iSOrderPrices) {
        this.prices = iSOrderPrices;
    }

    public Set<ISShippingContainer> getShipcontainers() {
        return shipcontainers;
    }

    public ISOrder shipcontainers(Set<ISShippingContainer> iSShippingContainers) {
        this.shipcontainers = iSShippingContainers;
        return this;
    }

    public ISOrder addShipcontainers(ISShippingContainer iSShippingContainer) {
        this.shipcontainers.add(iSShippingContainer);
        iSShippingContainer.setISOrder(this);
        return this;
    }

    public ISOrder removeShipcontainers(ISShippingContainer iSShippingContainer) {
        this.shipcontainers.remove(iSShippingContainer);
        iSShippingContainer.setISOrder(null);
        return this;
    }

    public void setShipcontainers(Set<ISShippingContainer> iSShippingContainers) {
        this.shipcontainers = iSShippingContainers;
    }

    public Set<ISItem> getItems() {
        return items;
    }

    public ISOrder items(Set<ISItem> iSItems) {
        this.items = iSItems;
        return this;
    }

    public ISOrder addItem(ISItem iSItem) {
        this.items.add(iSItem);
        iSItem.setISOrder(this);
        return this;
    }

    public ISOrder removeItem(ISItem iSItem) {
        this.items.remove(iSItem);
        iSItem.setISOrder(null);
        return this;
    }

    public void setItems(Set<ISItem> iSItems) {
        this.items = iSItems;
    }

    public Set<ISOrderPayment> getPayments() {
        return payments;
    }

    public ISOrder payments(Set<ISOrderPayment> iSOrderPayments) {
        this.payments = iSOrderPayments;
        return this;
    }

    public ISOrder addPayments(ISOrderPayment iSOrderPayment) {
        this.payments.add(iSOrderPayment);
        iSOrderPayment.setISOrder(this);
        return this;
    }

    public ISOrder removePayments(ISOrderPayment iSOrderPayment) {
        this.payments.remove(iSOrderPayment);
        iSOrderPayment.setISOrder(null);
        return this;
    }

    public void setPayments(Set<ISOrderPayment> iSOrderPayments) {
        this.payments = iSOrderPayments;
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
        ISOrder iSOrder = (ISOrder) o;
        if (iSOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISOrder{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", custid='" + getCustid() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", placedDate='" + getPlacedDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
