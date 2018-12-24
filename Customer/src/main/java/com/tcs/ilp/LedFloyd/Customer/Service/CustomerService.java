package com.tcs.ilp.LedFloyd.Customer.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tcs.ilp.LedFloyd.Customer.Model.Customer;
import com.tcs.ilp.LedFloyd.Customer.Repo.CustomerRepo;

@Component
public class CustomerService {
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private ServiceInstance instance;
	@Autowired
	private LoadBalancerClient DC;
	//Create
	public String createCustomer(Customer customer) throws HibernateException
	{
		if(!checkCustomer(customer.getSsn())) {
			customer.setDeleted(false);
			System.out.println(customer.isDeleted());
			return (customerRepo.save(customer).getCustomerId());
		}
		if(checkCustomer(customer.getSsn()))
		{
			System.out.println(customer.getSsn());
			Customer cust=viewBySsn(customer.getSsn());
			String customerId=cust.getCustomerId();
			customer.setCustomerId(customerId);
			customer.setDeleted(false);
			return (customerRepo.save(customer).getCustomerId());
		}
		return "";
	}
	//Update
	public boolean updateCustomer(Customer customer) throws HibernateException
	{
		if(checkCustomer(customer.getSsn())) {
			customer.setDeleted(false);
			return (customerRepo.save(customer)!=null);
		}
		return false;
	}
	//viewByCustomerId
	public Customer viewById(String id) throws HibernateException
	{
		try {
			return customerRepo.findById(id).get();
		}
		catch(NoSuchElementException e)
		{
			return null;
		}
	}
	//viewByCustomerId
	public Customer viewBySsn(String ssn) throws HibernateException
	{
		return customerRepo.viewBySsn(ssn);
	}
	public Customer viewBySsnDirty(String ssn) throws HibernateException
	{
		Customer customer= customerRepo.viewBySsn(ssn);
		if(customer==null)return null;
		if(customer.isDeleted()==true)
		{
			return null;
		}
		return customer;
	}
	//viewByName
	public ArrayList<Customer> viewByName(String name) throws HibernateException
	{
		return filterDeleted((ArrayList<Customer>)customerRepo.viewByName(name));
	}
	//viewByContactNo
	public ArrayList<Customer> viewByContactNo(String contactNo) throws HibernateException
	{
		return filterDeleted((ArrayList<Customer>)customerRepo.viewByContactNo(contactNo));
	}
	//viewByEmail
	public ArrayList<Customer> viewByEmail(String email) throws HibernateException
	{
		return filterDeleted((ArrayList<Customer>)customerRepo.viewByEmail(email));
	}
	//viewByCity
	public ArrayList<Customer> viewByCity(String city) throws HibernateException
	{
		return filterDeleted((ArrayList<Customer>)customerRepo.viewByCity(city));
	}
	
	//ViewAll
	public ArrayList<Customer> viewAllCustomers() throws HibernateException
	{
		return filterDeleted((ArrayList<Customer>)customerRepo.findAll());
	}
	
	//delete
	public boolean deleteCustomer(String ssn) throws HibernateException
	{
		Customer customer=viewBySsn(ssn);
		if((customer==null)||(customer.isDeleted())) return false;
		RestTemplate restTemplate=new RestTemplate();

	
		instance=DC.choose("LedFloyd_Zuul_Proxy");
		String url=instance.getUri().toString();
		String url1=url+"/api/account/checkCustomerExist/";
		final String uri=url1+customer.getCustomerId();
		System.out.println(uri);
		ResponseEntity<Boolean> b=restTemplate.getForEntity(uri, Boolean.class, HttpStatus.OK);
		boolean result=b.getBody();
		System.out.println(result);
		if(result==true) {
		customer.setDeleted(true);
		customerRepo.save(customer);
		return true;
		}
		return false;
	}
	//filter out deleted customers
	public ArrayList<Customer> filterDeleted(ArrayList<Customer> list)
	{
		ArrayList<Customer> filteredList=new ArrayList<Customer>();
		for(Customer customer:list)
			if(!customer.isDeleted())
				filteredList.add(customer);
		return filteredList;
	}
	//checkCustomer
	public boolean checkCustomer(String ssn) throws HibernateException
	{
		 Customer customer=customerRepo.viewBySsn(ssn);
		 if(customer==null)return false;
		 return true;
	}
	
	//checkCustomerByCustomerId
	public boolean checkByCustomerId(String id)
	{
		
		Customer customer;
		try {
			customer = customerRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			return false;
		}
		
		if(customer.isDeleted()==true)
		{
			return false;
		}
		return true;
	}

}
