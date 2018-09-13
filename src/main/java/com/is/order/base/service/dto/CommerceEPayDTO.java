package com.is.order.base.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CommerceEPay entity.
 */
public class CommerceEPayDTO implements Serializable {

    private Long id;

    private String ePayType;

    private String ePayToken;

    private ZonedDateTime createDate;

    private ZonedDateTime updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getePayType() {
        return ePayType;
    }

    public void setePayType(String ePayType) {
        this.ePayType = ePayType;
    }

    public String getePayToken() {
        return ePayToken;
    }

    public void setePayToken(String ePayToken) {
        this.ePayToken = ePayToken;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommerceEPayDTO commerceEPayDTO = (CommerceEPayDTO) o;
        if (commerceEPayDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceEPayDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceEPayDTO{" +
            "id=" + getId() +
            ", ePayType='" + getePayType() + "'" +
            ", ePayToken='" + getePayToken() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
