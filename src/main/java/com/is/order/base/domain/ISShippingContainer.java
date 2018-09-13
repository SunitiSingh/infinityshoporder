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

import com.is.order.base.domain.enumeration.ISShipStatus;

/**
 * A ISShippingContainer.
 */
@Entity
@Table(name = "is_shipping_container")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ISShippingContainer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "shipstatus", nullable = false)
    private ISShipStatus shipstatus;

    @Column(name = "carrier")
    private String carrier;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @ManyToOne
    @JsonIgnoreProperties("shipcontainers")
    private ISOrder iSOrder;

    @OneToOne
    @JoinColumn(unique = true)
    private ISShipContainerPrice price;

    @ManyToMany(mappedBy = "shipcontainers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ISItem> items = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ISShipStatus getShipstatus() {
        return shipstatus;
    }

    public ISShippingContainer shipstatus(ISShipStatus shipstatus) {
        this.shipstatus = shipstatus;
        return this;
    }

    public void setShipstatus(ISShipStatus shipstatus) {
        this.shipstatus = shipstatus;
    }

    public String getCarrier() {
        return carrier;
    }

    public ISShippingContainer carrier(String carrier) {
        this.carrier = carrier;
        return this;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public ISShippingContainer creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public ISShippingContainer updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public ISOrder getISOrder() {
        return iSOrder;
    }

    public ISShippingContainer iSOrder(ISOrder iSOrder) {
        this.iSOrder = iSOrder;
        return this;
    }

    public void setISOrder(ISOrder iSOrder) {
        this.iSOrder = iSOrder;
    }

    public ISShipContainerPrice getPrice() {
        return price;
    }

    public ISShippingContainer price(ISShipContainerPrice iSShipContainerPrice) {
        this.price = iSShipContainerPrice;
        return this;
    }

    public void setPrice(ISShipContainerPrice iSShipContainerPrice) {
        this.price = iSShipContainerPrice;
    }

    public Set<ISItem> getItems() {
        return items;
    }

    public ISShippingContainer items(Set<ISItem> iSItems) {
        this.items = iSItems;
        return this;
    }

    public ISShippingContainer addItem(ISItem iSItem) {
        this.items.add(iSItem);
        iSItem.getShipcontainers().add(this);
        return this;
    }

    public ISShippingContainer removeItem(ISItem iSItem) {
        this.items.remove(iSItem);
        iSItem.getShipcontainers().remove(this);
        return this;
    }

    public void setItems(Set<ISItem> iSItems) {
        this.items = iSItems;
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
        ISShippingContainer iSShippingContainer = (ISShippingContainer) o;
        if (iSShippingContainer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSShippingContainer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISShippingContainer{" +
            "id=" + getId() +
            ", shipstatus='" + getShipstatus() + "'" +
            ", carrier='" + getCarrier() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
