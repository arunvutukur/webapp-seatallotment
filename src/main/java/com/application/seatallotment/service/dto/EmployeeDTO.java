package com.application.seatallotment.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Employee entity.
 */
public class EmployeeDTO implements Serializable {

    private String id;

    private String name;

    private String empId;

    private String manager;

    private String email;

    private String location;

    private Boolean requestForApproval;

    private Boolean pendingForApproval;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean isRequestForApproval() {
        return requestForApproval;
    }

    public void setRequestForApproval(Boolean requestForApproval) {
        this.requestForApproval = requestForApproval;
    }

    public Boolean isPendingForApproval() {
        return pendingForApproval;
    }

    public void setPendingForApproval(Boolean pendingForApproval) {
        this.pendingForApproval = pendingForApproval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (employeeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", empId='" + getEmpId() + "'" +
            ", manager='" + getManager() + "'" +
            ", email='" + getEmail() + "'" +
            ", location='" + getLocation() + "'" +
            ", requestForApproval='" + isRequestForApproval() + "'" +
            ", pendingForApproval='" + isPendingForApproval() + "'" +
            "}";
    }
}
