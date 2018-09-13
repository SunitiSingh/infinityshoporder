package com.is.order.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import com.is.order.base.domain.enumeration.CommerceShipStatus;

/**
 * A CommerceShippingContainer.
 */
@Entity
@Table(name = "commerce_shipping_container")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommerceShippingContainer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "shipstatus", nullable = false)
    private CommerceShipStatus shipstatus;

    @Column(name = "carrier")
    private String carrier;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @ManyToOne
    @JsonIgnoreProperties("shipcontainers")
    private CommerceOrder commerceOrder;

    @OneToOne
    @JoinColumn(unique = true)
    private CommerceShipContainerPrice price;

    @ManyToMany(mappedBy = "shipcontainers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommerceItem> items = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommerceShipStatus getShipstatus() {
        return shipstatus;
    }

    public CommerceShippingContainer shipstatus(CommerceShipStatus shipstatus) {
        this.shipstatus = shipstatus;
        return this;
    }

    public void setShipstatus(CommerceShipStatus shipstatus) {
        this.shipstatus = shipstatus;
    }

    public String getCarrier() {
        return carrier;
    }

    public CommerceShippingContainer carrier(String carrier) {
        this.carrier = carrier;
        return this;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public CommerceShippingContainer creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public CommerceShippingContainer updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public CommerceOrder getCommerceOrder() {
        return commerceOrder;
    }

    public CommerceShippingContainer commerceOrder(CommerceOrder commerceOrder) {
        this.commerceOrder = commerceOrder;
        return this;
    }

    public void setCommerceOrder(CommerceOrder commerceOrder) {
        this.commerceOrder = commerceOrder;
    }

    public CommerceShipContainerPrice getPrice() {
        return price;
    }

    public CommerceShippingContainer price(CommerceShipContainerPrice commerceShipContainerPrice) {
        this.price = commerceShipContainerPrice;
        return this;
    }

    public void setPrice(CommerceShipContainerPrice commerceShipContainerPrice) {
        this.price = commerceShipContainerPrice;
    }

    public Set<CommerceItem> getItems() {
        return items;
    }

    public CommerceShippingContainer items(Set<CommerceItem> commerceItems) {
        this.items = commerceItems;
        return this;
    }

    public CommerceShippingContainer addItem(CommerceItem commerceItem) {
        this.items.add(commerceItem);
        commerceItem.getShipcontainers().add(this);
        return this;
    }

    public CommerceShippingContainer removeItem(CommerceItem commerceItem) {
        this.items.remove(commerceItem);
        commerceItem.getShipcontainers().remove(this);
        return this;
    }

    public void setItems(Set<CommerceItem> commerceItems) {
        this.items = commerceItems;
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
        CommerceShippingContainer commerceShippingContainer = (CommerceShippingContainer) o;
        if (commerceShippingContainer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceShippingContainer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceShippingContainer{" +
            "id=" + getId() +
            ", shipstatus='" + getShipstatus() + "'" +
            ", carrier='" + getCarrier() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
