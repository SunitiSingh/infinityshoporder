package com.is.order.base.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.is.order.base.domain.enumeration.ItemPayStatus;

/**
 * A CommerceItemPayInfo.
 */
@Entity
@Table(name = "commerce_item_pay_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommerceItemPayInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ItemPayStatus status;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @OneToOne
    @JoinColumn(unique = true)
    private CommerceOrderPayment orderPayment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemPayStatus getStatus() {
        return status;
    }

    public CommerceItemPayInfo status(ItemPayStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ItemPayStatus status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CommerceItemPayInfo quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public CommerceOrderPayment getOrderPayment() {
        return orderPayment;
    }

    public CommerceItemPayInfo orderPayment(CommerceOrderPayment commerceOrderPayment) {
        this.orderPayment = commerceOrderPayment;
        return this;
    }

    public void setOrderPayment(CommerceOrderPayment commerceOrderPayment) {
        this.orderPayment = commerceOrderPayment;
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
        CommerceItemPayInfo commerceItemPayInfo = (CommerceItemPayInfo) o;
        if (commerceItemPayInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceItemPayInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceItemPayInfo{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
