package com.tcs.ilp.LedFloyd.Customer.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TBL_CUSTOMER_LEDFLOYD")
public class Customer 
{
	@Id
	@Column(name="CUSTOMER_ID")
	@GenericGenerator(name = "SEQ_CUST_ID", strategy = "com.tcs.ilp.LedFloyd.Customer.IdGenerator.CustomerIdGenerator")
	@GeneratedValue(generator = "SEQ_CUST_ID")  
	private String customerId;
	
	@Column(name="SSN",length=12,unique=true)
	private String ssn;
	
	@Column(name="NAME",nullable=false)
	private String name;
	
	@Column(name="CONTACT_NO",nullable=false)
	private String contactNo;
	
	@Column(name="EMAIL",nullable=false)
	private String email;
	
	@Column(name="CITY",nullable=false)
	private String city;
	
	@Column(name="DELETED")
	private boolean deleted;
	
	public Customer() {
		super();
	}

	public Customer(String customerId, String ssn, String name, String contactNo, String email, String city,
			boolean deleted) {
		super();
		this.customerId = customerId;
		this.ssn = ssn;
		this.name = name;
		this.contactNo = contactNo;
		this.email = email;
		this.city = city;
		this.deleted = deleted;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	
}