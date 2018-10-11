package com.application.seatallotment.domain;

import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Document(collection = "seatalloted")
public class Seatalloted implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("SeatNumber")
    private String seatNumber;

    @Field("location")
    private String location;

    @Field("floor")
    private String floor;

    @Field("vacancy")
    private String vacancy;

    @Field("requestForApproval")
    private String requestForApproval;

    @Field("pendingForApproval")
    private String pendingForApproval;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Seatalloted seatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getLocation() {
        return location;
    }

    public Seatalloted location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFloor() {
        return floor;
    }

    public Seatalloted floor(String floor) {
        this.floor = floor;
        return this;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getVacancy() {
        return vacancy;
    }

    public Seatalloted vacancy(String vacancy) {
        this.vacancy = vacancy;
        return this;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public String getRequestForApproval() {
        return requestForApproval;
    }

    public Seatalloted requestForApproval(String requestForApproval) {
        this.requestForApproval = requestForApproval;
        return this;
    }

    public void setRequestForApproval(String requestForApproval) {
        this.requestForApproval = requestForApproval;
    }

    public String getPendingForApproval() {
        return pendingForApproval;
    }

    public Seatalloted pendingForApproval(String pendingForApproval) {
        this.pendingForApproval = pendingForApproval;
        return this;
    }

    public void setPendingForApproval(String pendingForApproval) {
        this.pendingForApproval = pendingForApproval;
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
        Seatalloted seatalloted = (Seatalloted) o;
        if (seatalloted.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seatalloted.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Seatalloted{" +
            "id=" + getId() +
            ", seatNumber='" + getSeatNumber() + "'" +
            ", location='" + getLocation() + "'" +
            ", floor='" + getFloor() + "'" +
            ", vacancy='" + getVacancy() + "'" +
            ", requestForApproval='" + getRequestForApproval() + "'" +
            ", pendingForApproval='" + getPendingForApproval() + "'" +
            "}";
    }
}
