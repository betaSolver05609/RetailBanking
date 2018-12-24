package com.tcs.ilp.LedFloyd.Customer.Controller;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.ilp.LedFloyd.Customer.Model.Customer;
import com.tcs.ilp.LedFloyd.Customer.Service.CustomerService;

@CrossOrigin(origins="*")
@RestController
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/createCustomer",method=RequestMethod.POST)
	public ResponseEntity<String> createCustomer(@RequestBody Customer customer)
	{
		ResponseEntity<String> res=new ResponseEntity<String>("", HttpStatus.OK);

		try {
			String result=customerService.createCustomer(customer);
			res=new ResponseEntity<String>(result, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return res;
		}
	}
	
	@RequestMapping(value="/updateCustomer",method=RequestMethod.POST)
	public ResponseEntity<Boolean> updateCustomer(@RequestBody Customer customer)
	{

		try {
			ResponseEntity<Boolean> res=new ResponseEntity<Boolean>(false, HttpStatus.OK);
			boolean result=customerService.updateCustomer(customer);
			if(result)
			{
				res=new ResponseEntity<Boolean>(result, HttpStatus.OK);
			}
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/viewBySsn/{ssn}",method=RequestMethod.GET)
	public ResponseEntity<Customer> viewBySsn(@PathVariable("ssn") String ssn)
	{
		try {
			ResponseEntity<Customer> res=null;
			Customer customer=customerService.viewBySsn(ssn);
			res=new ResponseEntity<Customer>(customer, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/viewBySsnDirty/{ssn}",method=RequestMethod.GET)
	public ResponseEntity<Customer> viewBySsnDirty(@PathVariable("ssn") String ssn)
	{	ResponseEntity<Customer> res=null;
		try {
			Customer customer=customerService.viewBySsnDirty(ssn);
			res=new ResponseEntity<Customer>(customer, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/viewByCustomerId/{id}",method=RequestMethod.GET)
	public ResponseEntity<Customer> viewById(@PathVariable("id") String id)
	{
		try {
			ResponseEntity<Customer> res=null;
			Customer customer=customerService.viewById(id);
			res=new ResponseEntity<Customer>(customer, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/viewAllCustomers",method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Customer>> viewAllCustomers()
	{
		try {
			ResponseEntity<ArrayList<Customer>> res=null;
			ArrayList<Customer> customerlist=customerService.viewAllCustomers();
			res=new ResponseEntity<ArrayList<Customer>>(customerlist, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/viewByName/{name}",method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Customer>> viewByName(@PathVariable("name") String name)
	{
		try {
			ResponseEntity<ArrayList<Customer>> res=null;
			ArrayList<Customer> customerlist=customerService.viewByName(name);
			res=new ResponseEntity<ArrayList<Customer>>(customerlist, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/viewByContactNo/{contactNo}",method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Customer>> viewByContactNo(@PathVariable("contactNo") String contactNo)
	{
		try {
			ResponseEntity<ArrayList<Customer>> res=null;
			ArrayList<Customer> customerlist=customerService.viewByContactNo(contactNo);
			res=new ResponseEntity<ArrayList<Customer>>(customerlist, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/viewByCity/{city}",method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Customer>> viewByCity(@PathVariable("city") String city)
	{
		try {
			ResponseEntity<ArrayList<Customer>> res=null;
			ArrayList<Customer> customerlist=customerService.viewByCity(city);
			res=new ResponseEntity<ArrayList<Customer>>(customerlist, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/viewByEmail/{email}",method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Customer>> viewByEmail(@PathVariable("email") String email)
	{
		try {
			ResponseEntity<ArrayList<Customer>> res=null;
			ArrayList<Customer> customerlist=customerService.viewByEmail(email);
			res=new ResponseEntity<ArrayList<Customer>>(customerlist, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/deleteCustomer/{ssn}",method=RequestMethod.GET)
	public ResponseEntity<Boolean> deleteCustomer(@PathVariable("ssn") String ssn)
	{

		try {
			ResponseEntity<Boolean> res=new ResponseEntity<Boolean>(false, HttpStatus.OK);
			boolean result=customerService.deleteCustomer(ssn);
			if(result)
			{
				res=new ResponseEntity<Boolean>(result, HttpStatus.OK);
			}
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/checkCustomer/{ssn}",method=RequestMethod.GET)
	public ResponseEntity<Boolean> checkCustomer(@PathVariable("ssn") String ssn)
	{

		try {
			ResponseEntity<Boolean> res=new ResponseEntity<Boolean>(false, HttpStatus.OK);
			boolean result=customerService.checkCustomer(ssn);
			if(result)
			{
				res=new ResponseEntity<Boolean>(result, HttpStatus.OK);
			}
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/checkCustomerByCustomerId/{customerId}",method=RequestMethod.GET)
	public ResponseEntity<Boolean> checkCustomerByCustomerId(@PathVariable("customerId") String customerId)
	{

		try {
			ResponseEntity<Boolean> res=new ResponseEntity<Boolean>(false, HttpStatus.OK);
			boolean result=customerService.checkByCustomerId(customerId);
			if(result)
			{
				res=new ResponseEntity<Boolean>(result, HttpStatus.OK);
			}
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
