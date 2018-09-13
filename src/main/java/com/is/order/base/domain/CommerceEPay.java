package com.is.order.base.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CommerceEPay.
 */
@Entity
@Table(name = "commerce_e_pay")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommerceEPay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "e_pay_type")
    private String ePayType;

    @Column(name = "e_pay_token")
    private String ePayToken;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getePayType() {
        return ePayType;
    }

    public CommerceEPay ePayType(String ePayType) {
        this.ePayType = ePayType;
        return this;
    }

    public void setePayType(String ePayType) {
        this.ePayType = ePayType;
    }

    public String getePayToken() {
        return ePayToken;
    }

    public CommerceEPay ePayToken(String ePayToken) {
        this.ePayToken = ePayToken;
        return this;
    }

    public void setePayToken(String ePayToken) {
        this.ePayToken = ePayToken;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public CommerceEPay createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public CommerceEPay updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
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
        CommerceEPay commerceEPay = (CommerceEPay) o;
        if (commerceEPay.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceEPay.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceEPay{" +
            "id=" + getId() +
            ", ePayType='" + getePayType() + "'" +
            ", ePayToken='" + getePayToken() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
