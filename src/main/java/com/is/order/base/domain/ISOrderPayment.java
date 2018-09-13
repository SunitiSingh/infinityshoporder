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

import com.is.order.base.domain.enumeration.ISPaymentType;

/**
 * A ISOrderPayment.
 */
@Entity
@Table(name = "is_order_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ISOrderPayment implements Serializable {

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
    private ISPaymentType paymentType;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "billing_phone")
    private String billingPhone;

    @ManyToOne
    @JsonIgnoreProperties("payments")
    private ISOrder iSOrder;

    @OneToOne
    @JoinColumn(unique = true)
    private ISPaymentCard card;

    @OneToOne
    @JoinColumn(unique = true)
    private ISEPay epay;

    @OneToOne
    @JoinColumn(unique = true)
    private ISBillingAddress billingAddress;

    @ManyToMany(mappedBy = "payments")
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

    public PayStatus getPaystatus() {
        return paystatus;
    }

    public ISOrderPayment paystatus(PayStatus paystatus) {
        this.paystatus = paystatus;
        return this;
    }

    public void setPaystatus(PayStatus paystatus) {
        this.paystatus = paystatus;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public ISOrderPayment paymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
        return this;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public ISPaymentType getPaymentType() {
        return paymentType;
    }

    public ISOrderPayment paymentType(ISPaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(ISPaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getFirstName() {
        return firstName;
    }

    public ISOrderPayment firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ISOrderPayment lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBillingPhone() {
        return billingPhone;
    }

    public ISOrderPayment billingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
        return this;
    }

    public void setBillingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
    }

    public ISOrder getISOrder() {
        return iSOrder;
    }

    public ISOrderPayment iSOrder(ISOrder iSOrder) {
        this.iSOrder = iSOrder;
        return this;
    }

    public void setISOrder(ISOrder iSOrder) {
        this.iSOrder = iSOrder;
    }

    public ISPaymentCard getCard() {
        return card;
    }

    public ISOrderPayment card(ISPaymentCard iSPaymentCard) {
        this.card = iSPaymentCard;
        return this;
    }

    public void setCard(ISPaymentCard iSPaymentCard) {
        this.card = iSPaymentCard;
    }

    public ISEPay getEpay() {
        return epay;
    }

    public ISOrderPayment epay(ISEPay iSEPay) {
        this.epay = iSEPay;
        return this;
    }

    public void setEpay(ISEPay iSEPay) {
        this.epay = iSEPay;
    }

    public ISBillingAddress getBillingAddress() {
        return billingAddress;
    }

    public ISOrderPayment billingAddress(ISBillingAddress iSBillingAddress) {
        this.billingAddress = iSBillingAddress;
        return this;
    }

    public void setBillingAddress(ISBillingAddress iSBillingAddress) {
        this.billingAddress = iSBillingAddress;
    }

    public Set<ISItem> getItems() {
        return items;
    }

    public ISOrderPayment items(Set<ISItem> iSItems) {
        this.items = iSItems;
        return this;
    }

    public ISOrderPayment addItem(ISItem iSItem) {
        this.items.add(iSItem);
        iSItem.getPayments().add(this);
        return this;
    }

    public ISOrderPayment removeItem(ISItem iSItem) {
        this.items.remove(iSItem);
        iSItem.getPayments().remove(this);
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
        ISOrderPayment iSOrderPayment = (ISOrderPayment) o;
        if (iSOrderPayment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSOrderPayment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISOrderPayment{" +
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
