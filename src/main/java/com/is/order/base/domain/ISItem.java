package com.is.order.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ISItem.
 */
@Entity
@Table(name = "is_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ISItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "skuid", nullable = false)
    private String skuid;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @ManyToOne
    @JsonIgnoreProperties("items")
    private ISOrder iSOrder;

    @OneToOne
    @JoinColumn(unique = true)
    private ISItemPrice price;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "isitem_shipcontainer",
               joinColumns = @JoinColumn(name = "isitems_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "shipcontainers_id", referencedColumnName = "id"))
    private Set<ISShippingContainer> shipcontainers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "isitem_payment",
               joinColumns = @JoinColumn(name = "isitems_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "payments_id", referencedColumnName = "id"))
    private Set<ISOrderPayment> payments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuid() {
        return skuid;
    }

    public ISItem skuid(String skuid) {
        this.skuid = skuid;
        return this;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ISItem quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public ISItem creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public ISItem updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public ISOrder getISOrder() {
        return iSOrder;
    }

    public ISItem iSOrder(ISOrder iSOrder) {
        this.iSOrder = iSOrder;
        return this;
    }

    public void setISOrder(ISOrder iSOrder) {
        this.iSOrder = iSOrder;
    }

    public ISItemPrice getPrice() {
        return price;
    }

    public ISItem price(ISItemPrice iSItemPrice) {
        this.price = iSItemPrice;
        return this;
    }

    public void setPrice(ISItemPrice iSItemPrice) {
        this.price = iSItemPrice;
    }

    public Set<ISShippingContainer> getShipcontainers() {
        return shipcontainers;
    }

    public ISItem shipcontainers(Set<ISShippingContainer> iSShippingContainers) {
        this.shipcontainers = iSShippingContainers;
        return this;
    }

    public ISItem addShipcontainer(ISShippingContainer iSShippingContainer) {
        this.shipcontainers.add(iSShippingContainer);
        iSShippingContainer.getItems().add(this);
        return this;
    }

    public ISItem removeShipcontainer(ISShippingContainer iSShippingContainer) {
        this.shipcontainers.remove(iSShippingContainer);
        iSShippingContainer.getItems().remove(this);
        return this;
    }

    public void setShipcontainers(Set<ISShippingContainer> iSShippingContainers) {
        this.shipcontainers = iSShippingContainers;
    }

    public Set<ISOrderPayment> getPayments() {
        return payments;
    }

    public ISItem payments(Set<ISOrderPayment> iSOrderPayments) {
        this.payments = iSOrderPayments;
        return this;
    }

    public ISItem addPayment(ISOrderPayment iSOrderPayment) {
        this.payments.add(iSOrderPayment);
        iSOrderPayment.getItems().add(this);
        return this;
    }

    public ISItem removePayment(ISOrderPayment iSOrderPayment) {
        this.payments.remove(iSOrderPayment);
        iSOrderPayment.getItems().remove(this);
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
        ISItem iSItem = (ISItem) o;
        if (iSItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISItem{" +
            "id=" + getId() +
            ", skuid='" + getSkuid() + "'" +
            ", quantity=" + getQuantity() +
            ", creationDate='" + getCreationDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
