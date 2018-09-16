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
 * A CommerceItem.
 */
@Entity
@Table(name = "commerce_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommerceItem implements Serializable {

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
    private CommerceOrder commerceOrder;

    @OneToOne
    @JoinColumn(unique = true)
    private CommerceItemPrice price;

    @OneToOne
    @JoinColumn(unique = true)
    private CommerceItemShipInfo shipInfo;

    @OneToOne
    @JoinColumn(unique = true)
    private CommerceItemPayInfo payInfo;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "commerce_item_shipcontainer",
               joinColumns = @JoinColumn(name = "commerce_items_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "shipcontainers_id", referencedColumnName = "id"))
    private Set<CommerceShippingContainer> shipcontainers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "commerce_item_payment",
               joinColumns = @JoinColumn(name = "commerce_items_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "payments_id", referencedColumnName = "id"))
    private Set<CommerceOrderPayment> payments = new HashSet<>();

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

    public CommerceItem skuid(String skuid) {
        this.skuid = skuid;
        return this;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CommerceItem quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public CommerceItem creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public CommerceItem updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public CommerceOrder getCommerceOrder() {
        return commerceOrder;
    }

    public CommerceItem commerceOrder(CommerceOrder commerceOrder) {
        this.commerceOrder = commerceOrder;
        return this;
    }

    public void setCommerceOrder(CommerceOrder commerceOrder) {
        this.commerceOrder = commerceOrder;
    }

    public CommerceItemPrice getPrice() {
        return price;
    }

    public CommerceItem price(CommerceItemPrice commerceItemPrice) {
        this.price = commerceItemPrice;
        return this;
    }

    public void setPrice(CommerceItemPrice commerceItemPrice) {
        this.price = commerceItemPrice;
    }

    public CommerceItemShipInfo getShipInfo() {
        return shipInfo;
    }

    public CommerceItem shipInfo(CommerceItemShipInfo commerceItemShipInfo) {
        this.shipInfo = commerceItemShipInfo;
        return this;
    }

    public void setShipInfo(CommerceItemShipInfo commerceItemShipInfo) {
        this.shipInfo = commerceItemShipInfo;
    }

    public CommerceItemPayInfo getPayInfo() {
        return payInfo;
    }

    public CommerceItem payInfo(CommerceItemPayInfo commerceItemPayInfo) {
        this.payInfo = commerceItemPayInfo;
        return this;
    }

    public void setPayInfo(CommerceItemPayInfo commerceItemPayInfo) {
        this.payInfo = commerceItemPayInfo;
    }

    public Set<CommerceShippingContainer> getShipcontainers() {
        return shipcontainers;
    }

    public CommerceItem shipcontainers(Set<CommerceShippingContainer> commerceShippingContainers) {
        this.shipcontainers = commerceShippingContainers;
        return this;
    }

    public CommerceItem addShipcontainer(CommerceShippingContainer commerceShippingContainer) {
        this.shipcontainers.add(commerceShippingContainer);
        commerceShippingContainer.getItems().add(this);
        return this;
    }

    public CommerceItem removeShipcontainer(CommerceShippingContainer commerceShippingContainer) {
        this.shipcontainers.remove(commerceShippingContainer);
        commerceShippingContainer.getItems().remove(this);
        return this;
    }

    public void setShipcontainers(Set<CommerceShippingContainer> commerceShippingContainers) {
        this.shipcontainers = commerceShippingContainers;
    }

    public Set<CommerceOrderPayment> getPayments() {
        return payments;
    }

    public CommerceItem payments(Set<CommerceOrderPayment> commerceOrderPayments) {
        this.payments = commerceOrderPayments;
        return this;
    }

    public CommerceItem addPayment(CommerceOrderPayment commerceOrderPayment) {
        this.payments.add(commerceOrderPayment);
        commerceOrderPayment.getItems().add(this);
        return this;
    }

    public CommerceItem removePayment(CommerceOrderPayment commerceOrderPayment) {
        this.payments.remove(commerceOrderPayment);
        commerceOrderPayment.getItems().remove(this);
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
        CommerceItem commerceItem = (CommerceItem) o;
        if (commerceItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceItem{" +
            "id=" + getId() +
            ", skuid='" + getSkuid() + "'" +
            ", quantity=" + getQuantity() +
            ", creationDate='" + getCreationDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
