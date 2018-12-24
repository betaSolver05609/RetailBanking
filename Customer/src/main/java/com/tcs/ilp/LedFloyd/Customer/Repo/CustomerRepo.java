package com.tcs.ilp.LedFloyd.Customer.Repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcs.ilp.LedFloyd.Customer.Model.Customer;

import java.util.List;

@Repository
public interface CustomerRepo extends CrudRepository<Customer, String>{
	@Query("SELECT customer FROM Customer customer WHERE customer.ssn = :ssn")
    public Customer viewBySsn(@Param("ssn") String ssn);
	
	@Query("SELECT customer FROM Customer customer WHERE customer.name like %:name%")
    public List<Customer> viewByName(@Param("name") String name);
	
	@Query("SELECT customer FROM Customer customer WHERE customer.contactNo like %:contactNo%")
    public List<Customer> viewByContactNo(@Param("contactNo") String contactNo);
	
	@Query("SELECT customer FROM Customer customer WHERE customer.email like %:email%")
    public List<Customer> viewByEmail(@Param("email") String email);
	
	@Query("SELECT customer FROM Customer customer WHERE customer.city like %:city%")
    public List<Customer> viewByCity(@Param("city") String city);
	
	
	
}