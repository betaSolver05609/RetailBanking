package com.tcs.ilp.LedFloyd.Transaction.Repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcs.ilp.LedFloyd.Transaction.Model.Transaction;
@Repository
public interface TransactionRepo extends CrudRepository<Transaction, String>{
	/*@Query("select t from Transaction t where t.accountId= :accountId")
	public ArrayList<Transaction> viewByAccountId(@Param("accountId") String accountId);*/
	@Query("from Transaction where customerId = :customerId and TIMESTAMP between to_date(:sdate,'YYYY-MM-DD') and to_date(:edate,'YYYY-MM-DD') order by TIMESTAMP desc")
	public ArrayList<Transaction> viewByCustomerId(@Param("customerId") String customerId,@Param("sdate") String sdate,@Param("edate") String edate);
	
	@Query("from Transaction where TIMESTAMP between to_date(:sdate,'YYYY-MM-DD') and to_date(:edate,'YYYY-MM-DD') order by TIMESTAMP desc")
	public ArrayList<Transaction> getAll(@Param("sdate") String sdate,@Param("edate") String edate);
	
	@Query("from Transaction where accountId = :accountId and TIMESTAMP between to_date(:sdate,'YYYY-MM-DD') and to_date(:edate,'YYYY-MM-DD') order by TIMESTAMP desc")
	public ArrayList<Transaction> viewByAccountId(@Param("accountId") String accountId,@Param("sdate") String sdate,@Param("edate") String edate);
}

