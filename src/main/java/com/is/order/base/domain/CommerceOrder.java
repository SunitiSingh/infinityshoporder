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
 * The CommerceOrder entity.
 */
@ApiModel(description = "The CommerceOrder entity.")
@Entity
@Table(name = "commerce_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommerceOrder implements Serializable {

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

    @OneToMany(mappedBy = "commerceOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommerceOrderPrice> prices = new HashSet<>();

    @OneToMany(mappedBy = "commerceOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommerceShippingContainer> shipcontainers = new HashSet<>();

    @OneToMany(mappedBy = "commerceOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommerceItem> items = new HashSet<>();

    @OneToMany(mappedBy = "commerceOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommerceOrderPayment> payments = new HashSet<>();

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

    public CommerceOrder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getCustid() {
        return custid;
    }

    public CommerceOrder custid(String custid) {
        this.custid = custid;
        return this;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public CommerceOrder creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getPlacedDate() {
        return placedDate;
    }

    public CommerceOrder placedDate(ZonedDateTime placedDate) {
        this.placedDate = placedDate;
        return this;
    }

    public void setPlacedDate(ZonedDateTime placedDate) {
        this.placedDate = placedDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public CommerceOrder updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public Set<CommerceOrderPrice> getPrices() {
        return prices;
    }

    public CommerceOrder prices(Set<CommerceOrderPrice> commerceOrderPrices) {
        this.prices = commerceOrderPrices;
        return this;
    }

    public CommerceOrder addPrices(CommerceOrderPrice commerceOrderPrice) {
        this.prices.add(commerceOrderPrice);
        commerceOrderPrice.setCommerceOrder(this);
        return this;
    }

    public CommerceOrder removePrices(CommerceOrderPrice commerceOrderPrice) {
        this.prices.remove(commerceOrderPrice);
        commerceOrderPrice.setCommerceOrder(null);
        return this;
    }

    public void setPrices(Set<CommerceOrderPrice> commerceOrderPrices) {
        this.prices = commerceOrderPrices;
    }

    public Set<CommerceShippingContainer> getShipcontainers() {
        return shipcontainers;
    }

    public CommerceOrder shipcontainers(Set<CommerceShippingContainer> commerceShippingContainers) {
        this.shipcontainers = commerceShippingContainers;
        return this;
    }

    public CommerceOrder addShipcontainers(CommerceShippingContainer commerceShippingContainer) {
        this.shipcontainers.add(commerceShippingContainer);
        commerceShippingContainer.setCommerceOrder(this);
        return this;
    }

    public CommerceOrder removeShipcontainers(CommerceShippingContainer commerceShippingContainer) {
        this.shipcontainers.remove(commerceShippingContainer);
        commerceShippingContainer.setCommerceOrder(null);
        return this;
    }

    public void setShipcontainers(Set<CommerceShippingContainer> commerceShippingContainers) {
        this.shipcontainers = commerceShippingContainers;
    }

    public Set<CommerceItem> getItems() {
        return items;
    }

    public CommerceOrder items(Set<CommerceItem> commerceItems) {
        this.items = commerceItems;
        return this;
    }

    public CommerceOrder addItem(CommerceItem commerceItem) {
        this.items.add(commerceItem);
        commerceItem.setCommerceOrder(this);
        return this;
    }

    public CommerceOrder removeItem(CommerceItem commerceItem) {
        this.items.remove(commerceItem);
        commerceItem.setCommerceOrder(null);
        return this;
    }

    public void setItems(Set<CommerceItem> commerceItems) {
        this.items = commerceItems;
    }

    public Set<CommerceOrderPayment> getPayments() {
        return payments;
    }

    public CommerceOrder payments(Set<CommerceOrderPayment> commerceOrderPayments) {
        this.payments = commerceOrderPayments;
        return this;
    }

    public CommerceOrder addPayments(CommerceOrderPayment commerceOrderPayment) {
        this.payments.add(commerceOrderPayment);
        commerceOrderPayment.setCommerceOrder(this);
        return this;
    }

    public CommerceOrder removePayments(CommerceOrderPayment commerceOrderPayment) {
        this.payments.remove(commerceOrderPayment);
        commerceOrderPayment.setCommerceOrder(null);
        return this;
    }

    public void setPayments(Set<CommerceOrderPayment> commerceOrderPayments) {
        this.payments = commerceOrderPayments;
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
        CommerceOrder commerceOrder = (CommerceOrder) o;
        if (commerceOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceOrder{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", custid='" + getCustid() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", placedDate='" + getPlacedDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
