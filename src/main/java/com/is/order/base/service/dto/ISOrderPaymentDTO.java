package com.is.order.base.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.is.order.base.domain.enumeration.PayStatus;
import com.is.order.base.domain.enumeration.ISPaymentType;

/**
 * A DTO for the ISOrderPayment entity.
 */
public class ISOrderPaymentDTO implements Serializable {

    private Long id;

    private PayStatus paystatus;

    private BigDecimal paymentAmount;

    private ISPaymentType paymentType;

    private String firstName;

    private String lastName;

    private String billingPhone;

    private Long iSOrderId;

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

    public ISPaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(ISPaymentType paymentType) {
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

    public Long getISOrderId() {
        return iSOrderId;
    }

    public void setISOrderId(Long iSOrderId) {
        this.iSOrderId = iSOrderId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long iSPaymentCardId) {
        this.cardId = iSPaymentCardId;
    }

    public Long getEpayId() {
        return epayId;
    }

    public void setEpayId(Long iSEPayId) {
        this.epayId = iSEPayId;
    }

    public Long getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(Long iSBillingAddressId) {
        this.billingAddressId = iSBillingAddressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ISOrderPaymentDTO iSOrderPaymentDTO = (ISOrderPaymentDTO) o;
        if (iSOrderPaymentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iSOrderPaymentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ISOrderPaymentDTO{" +
            "id=" + getId() +
            ", paystatus='" + getPaystatus() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", billingPhone='" + getBillingPhone() + "'" +
            ", iSOrder=" + getISOrderId() +
            ", card=" + getCardId() +
            ", epay=" + getEpayId() +
            ", billingAddress=" + getBillingAddressId() +
            "}";
    }
}
