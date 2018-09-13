package com.is.order.base.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.is.order.base.domain.enumeration.OrderStatus;

/**
 * A DTO for the CommerceOrder entity.
 */
public class CommerceOrderDTO implements Serializable {

    private Long id;

    @NotNull
    private OrderStatus status;

    @NotNull
    private String custid;

    @NotNull
    private ZonedDateTime creationDate;

    private ZonedDateTime placedDate;

    private ZonedDateTime updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(ZonedDateTime placedDate) {
        this.placedDate = placedDate;
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

        CommerceOrderDTO commerceOrderDTO = (CommerceOrderDTO) o;
        if (commerceOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerceOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommerceOrderDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", custid='" + getCustid() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", placedDate='" + getPlacedDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
