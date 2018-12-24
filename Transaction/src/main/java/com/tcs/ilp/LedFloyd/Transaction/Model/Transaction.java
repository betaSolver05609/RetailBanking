package com.tcs.ilp.LedFloyd.Transaction.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;


	@Entity
	@Check(constraints="TRANSACTION_TYPE in('Deposit','Withdraw')")
	@Table(name="TBL_TRANSACTION_LEDFLOYD")
	public class Transaction {
		@Id
		@GenericGenerator(name = "SEQ_TRANS_ID", strategy = "com.tcs.ilp.LedFloyd.Transaction.IdGenerator.TransactionIdGenerator")
		@GeneratedValue(generator = "SEQ_TRANS_ID")  
		@Column(name="TRANSACTION_ID")
		private String transactionId;
		
		@Column(name="CUSTOMER_ID")
		private String customerId;
		
		@Column(name="ACCOUNT_ID")
		private String accountId;
		
		@Column(name="TRANSACTION_TYPE")
		private String transactionType;
		
		@Column(name="AMOUNT")
		private long amount;
		
		
		@Column(name="TIMESTAMP")
		@Temporal(TemporalType.TIMESTAMP)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
		private Date timestamp;


		public String getTransactionId() {
			return transactionId;
		}


		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}


		public String getCustomerId() {
			return customerId;
		}


		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}


		public String getAccountId() {
			return accountId;
		}


		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}


		public String getTransactionType() {
			return transactionType;
		}


		public void setTransactionType(String transactionType) {
			this.transactionType = transactionType;
		}


		public long getAmount() {
			return amount;
		}


		public void setAmount(long amount) {
			this.amount = amount;
		}


		public Date getTimestamp() {
			return timestamp;
		}


		public void setTimestamp(Date timestamp) {
			this.timestamp = timestamp;
		}


		@Override
		public String toString() {
			return "Transaction [transactionId=" + transactionId + ", customerId=" + customerId + ", accountId="
					+ accountId + ", transactionType=" + transactionType + ", amount=" + amount + ", timestamp="
					+ timestamp + "]";
		}
		

		
		

}
