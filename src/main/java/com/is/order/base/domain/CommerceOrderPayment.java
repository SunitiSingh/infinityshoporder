package com.is.order.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.is.order.base.domain.enumeration.PayStatus;

import com.is.order.base.domain.enumeration.CommercePaymentType;

/**
 * A CommerceOrderPayment.
 */
@Entity
@Table(name = "commerce_order_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommerceOrderPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "paystatus")
    private PayStatus paystatus;

    @Column(name = "payment_amount", precision = 10, scale = 2)
    private BigDecimal paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private CommercePaymentType paymentType;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "billing_phone")
    private String billingPhone;

    @ManyToOne
    @JsonIgnoreProperties("payments")
    private CommerceOrder commerceOrder;

    @OneToOne
    @JoinColumn(unique = true)
    private CommercePaymentCard card;

    @OneToOne
    @JoinColumn(unique = true)
    private CommerceEPay epay;

    @OneToOne
    @JoinColumn(unique = true)
    private CommerceBillingAddress billingAddress;

    @ManyToMany(mappedBy = "payments")
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

    public PayStatus getPaystatus() {
        return paystatus;
    }

    public CommerceOrderPayment paystatus(PayStatus paystatus) {
        this.paystatus = paystatus;
        return this;
    }

    public void setPaystatus(PayStatus paystatus) {
        this.paystatus = paystatus;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public CommerceOrderPayment paymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
        return this;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public CommercePaymentType getPaymentType() {
        return paymentType;
    }

    public CommerceOrderPayment paymentType(CommercePaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(CommercePaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getFirstName() {
        return firstName;
    }

    public CommerceOrderPayment firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public CommerceOrderPayment lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBillingPhone() {
        return billingPhone;
    }

    public CommerceOrderPayment billingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
        return this;
    }

    public void setBillingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
    }

    public CommerceOrder getCommerceOrder() {
        return commerceOrder;
    }

    public CommerceOrderPayment commerceOrder(CommerceOrder commerceOrder) {
        this.commerceOrder = commerceOrder;
        return this;
    }

    public void setCommerceOrder(CommerceOrder commerceOrder) {
        this.commerceOrder = commerceOrder;
    }

    public CommercePaymentCard getCard() {
        return card;
    }

    public CommerceOrderPayment card(CommercePaymentCard commercePaymentCard) {
        this.card = commercePaymentCard;
        return this;
    }

    public void setCard(CommercePaymentCard commercePaymentCard) {
        this.card = commercePaymentCard;
    }

    public CommerceEPay getEpay() {
        return epay;
    }

    public CommerceOrderPayment epay(CommerceEPay commerceEPay) {
        this.epay = commerceEPay;
        return this;
    }

    public void setEpay(CommerceEPay commerceEPay) {
        this.epay = commerceEPay;
    }

    public CommerceBillingAddress getBillingAddress() {
        return billingAddress;
    }

    public CommerceOrderPayment billingAddress(CommerceBillingAddress commerceBillingAddress) {
        this.billingAddress = commerceBillingAddress;
        return this;
    }

    public void setBillingAddress(CommerceBillingAddress commerceBillingAddress) {
        this.billingAddress = commerceBillingAddress;
    }

    public Set<CommerceItem> getItems() {
        return items;
    }

    public CommerceOrderPayment items(Set<CommerceItem> commerceItems) {
        this.items = commerceItems;
        return this;
    }

    public CommerceOrderPayment addItem(CommerceItem commerceItem) {
        this.items.add(commerceItem);
        commerceItem.getPayments().add(this);
        return this;
    }

    public CommerceOrderPayment removeItem(CommerceItem commerceItem) {
        this.items.remove(commerceItem);
        commerceItem.getPayments().remove(this);
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
        CommerceOrderPayment commerceOrderPayment = (CommerceOrderPayment) o;
        if (commerceOrderPayment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceOrderPayment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceOrderPayment{" +
            "id=" + getId() +
            ", paystatus='" + getPaystatus() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", billingPhone='" + getBillingPhone() + "'" +
            "}";
    }
}
