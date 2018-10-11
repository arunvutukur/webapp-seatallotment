package com.application.seatallotment.domain;

import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Document(collection = "access")
public class Access implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("associate_id")
    private String associateId;

    @Field("role")
    private String role;

    @Field("password")
    private String password;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssociateId() {
        return associateId;
    }

    public Access associateId(String associateId) {
        this.associateId = associateId;
        return this;
    }

    public void setAssociateId(String associateId) {
        this.associateId = associateId;
    }

    public String getRole() {
        return role;
    }

    public Access role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public Access password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
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
        Access access = (Access) o;
        if (access.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), access.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Access{" +
            "id=" + getId() +
            ", associateId='" + getAssociateId() + "'" +
            ", role='" + getRole() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
