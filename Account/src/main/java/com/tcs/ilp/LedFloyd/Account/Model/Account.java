package com.tcs.ilp.LedFloyd.Account.Model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Check(constraints="Account_Type in('Saving','Current') AND  Status in('Active','Inactive')")
@Table(name="TBL_ACCOUNT_LEDFLOYD")
public class Account {
	@Id
	@Column(name="Account_ID",length=9)
	@GenericGenerator(name = "SEQ_ACC_ID", strategy = "com.tcs.ilp.LedFloyd.Account.IdGenerator.AccountIdGenerator")
	@GeneratedValue(generator = "SEQ_ACC_ID")  
	private String accountId;
	
	
	@Column(name="Customer_ID",length=9,nullable=false)
	private String customerId;
	
	@Column(name="Account_Type",nullable=false)
	private String accountType;
	
	@Column(name="Balance",nullable=false)
	private long balance;
	
	@Column(name="Creation_Date",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date creationDate;
	
	
	@Column(name="Status",nullable = false)
	@ColumnDefault("'Active'")
	private String status;


	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public long getBalance() {
		return balance;
	}


	public void setBalance(long balance) {
		this.balance = balance;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", customerId=" + customerId + ", accountType=" + accountType
				+ ", balance=" + balance + ", creationDate=" + creationDate + ", status=" + status + "]";
	}
	
}
