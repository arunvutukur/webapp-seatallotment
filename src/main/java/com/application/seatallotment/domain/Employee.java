package com.application.seatallotment.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Objects;

/**
 * A Employee.
 */
@Document(collection = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("emp_id")
    private String empId;

    @Field("manager")
    private String manager;

    @Field("email")
    private String email;

    @Field("location")
    private String location;

    @Field("requestForApproval")
    private Boolean requestForApproval;

    @Field("pendingForApproval")
    private Boolean pendingForApproval;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Employee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpId() {
        return empId;
    }

    public Employee empId(String empId) {
        this.empId = empId;
        return this;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getManager() {
        return manager;
    }

    public Employee manager(String manager) {
        this.manager = manager;
        return this;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public Employee location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean isRequestForApproval() {
        return requestForApproval;
    }

    public Employee requestForApproval(Boolean requestForApproval) {
        this.requestForApproval = requestForApproval;
        return this;
    }

    public void setRequestForApproval(Boolean requestForApproval) {
        this.requestForApproval = requestForApproval;
    }

    public Boolean isPendingForApproval() {
        return pendingForApproval;
    }

    public Employee pendingForApproval(Boolean pendingForApproval) {
        this.pendingForApproval = pendingForApproval;
        return this;
    }

    public void setPendingForApproval(Boolean pendingForApproval) {
        this.pendingForApproval = pendingForApproval;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Employee(){
        super();
    }

    public Employee(Object[] values) throws ParseException {
		super();
		//this.id = values[0];
		this.name = (String) values[1];
		this.empId = (String) values[2];
		this.email = (String) values[3];
		this.location = (String) values[4];
		//DateFormat df=new SimpleDateFormat("MMM dd yyyy hh:mmaa");
		//Date date=df.parse((String) values[5]);
		//this.createdDate = date;
		this.manager = (String) values[6];
		//this.roles = null;
		//this.seatNo = (String) values[8];
		Boolean requestForApproval = new Boolean((boolean) values[9]);
        this.requestForApproval = requestForApproval;
        Boolean pendingForApproval = new Boolean((boolean) values[10]);
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
        Employee employee = (Employee) o;
        if (employee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Employee{" +
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