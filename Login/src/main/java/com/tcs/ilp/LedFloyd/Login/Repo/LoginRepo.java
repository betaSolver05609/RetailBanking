package com.tcs.ilp.LedFloyd.Login.Repo;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcs.ilp.LedFloyd.Login.Model.Login;

@Repository
public interface LoginRepo extends CrudRepository<Login, String>{

//	@Query("select l from Login l where l.username = :username and l.password = :password")
//	Login giveCompleteObject(@Param("username") String username, @Param("password") String password);
	
	
}
