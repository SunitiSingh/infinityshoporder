package com.is.order.base.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.is.order.base.domain.enumeration.PayStatus;
import com.is.order.base.domain.enumeration.CommercePaymentType;

/**
 * A DTO for the CommerceOrderPayment entity.
 */
public class CommerceOrderPaymentDTO implements Serializable {

    private Long id;

    private PayStatus paystatus;

    private BigDecimal paymentAmount;

    private CommercePaymentType paymentType;

    private String firstName;

    private String lastName;

    private String billingPhone;

    private Long commerceOrderId;

    private Long cardId;

    private Long epayId;

    private Long billingAddressId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PayStatus getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(PayStatus paystatus) {
        this.paystatus = paystatus;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public CommercePaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(CommercePaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBillingPhone() {
        return billingPhone;
    }

    public void setBillingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
    }

    public Long getCommerceOrderId() {
        return commerceOrderId;
    }

    public void setCommerceOrderId(Long commerceOrderId) {
        this.commerceOrderId = commerceOrderId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long commercePaymentCardId) {
        this.cardId = commercePaymentCardId;
    }

    public Long getEpayId() {
        return epayId;
    }

    public void setEpayId(Long commerceEPayId) {
        this.epayId = commerceEPayId;
    }

    public Long getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(Long commerceBillingAddressId) {
        this.billingAddressId = commerceBillingAddressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommerceOrderPaymentDTO commerceOrderPaymentDTO = (CommerceOrderPaymentDTO) o;
        if (commerceOrderPaymentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceOrderPaymentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceOrderPaymentDTO{" +
            "id=" + getId() +
            ", paystatus='" + getPaystatus() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", billingPhone='" + getBillingPhone() + "'" +
            ", commerceOrder=" + getCommerceOrderId() +
            ", card=" + getCardId() +
            ", epay=" + getEpayId() +
            ", billingAddress=" + getBillingAddressId() +
            "}";
    }
}
