package com.tcs.ilp.LedFloyd.Account.Repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcs.ilp.LedFloyd.Account.Model.Account;

@Repository
public interface AccountRepo extends CrudRepository<Account, String> {
	@Query("FROM Account WHERE accountId = :accountId")
	Account viewByAccountId(@Param("accountId") String accountId);
	
	@Query("FROM Account WHERE customerId = :customerId")
	ArrayList<Account> viewByCustomerId(@Param("customerId") String customerId);
}
