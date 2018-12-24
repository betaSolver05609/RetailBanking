package com.tcs.ilp.LedFloyd.Account.Controller;

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

import com.tcs.ilp.LedFloyd.Account.Model.Account;
import com.tcs.ilp.LedFloyd.Account.Service.AccountService;

@CrossOrigin(origins="*")
@RestController
public class AccountController {
	@Autowired
	private AccountService accountService;

	@RequestMapping(value="/createAccount",method=RequestMethod.POST)
	public ResponseEntity<String> createAccount(@RequestBody Account account)
	{
		ResponseEntity<String> res= null;

		try {
			String result=accountService.createAccount(account);
			res=new ResponseEntity<String>(result, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return res;
		}
	}
	
	
	@RequestMapping(value="/deleteAccount/{accountId}",method=RequestMethod.GET)
	public ResponseEntity<Boolean> deleteAccount(@PathVariable("accountId") String accountId)
	{

		try {
			ResponseEntity<Boolean> res=new ResponseEntity<Boolean>(false, HttpStatus.OK);
			boolean result=accountService.deleteAccount(accountId);
			if(result)
			{
				res=new ResponseEntity<Boolean>(result, HttpStatus.OK);
			}
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return (new ResponseEntity<Boolean>(false, HttpStatus.OK));
		}
	}
	@RequestMapping(value="/viewByAccountId/{accountId}",method=RequestMethod.GET)
	public ResponseEntity<Account> viewByAccountId(@PathVariable("accountId") String accountId)
	{
		ResponseEntity<Account> res=null;
		try {
			Account account=accountService.viewById(accountId);
			res=new ResponseEntity<Account>(account, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return res;
		}
	}
	@RequestMapping(value="/viewAllAccounts",method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Account>> viewAllCustomers()
	{
		try {
			ResponseEntity<ArrayList<Account>> res=null;
			ArrayList<Account> accountlist=accountService.viewAllAccounts();
			res=new ResponseEntity<ArrayList<Account>>(accountlist, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/viewByCustomerId/{customerId}",method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Account>> viewByCustomerId(@PathVariable("customerId") String customerId)
	{
		try {
			ResponseEntity<ArrayList<Account>> res=null;
			ArrayList<Account> account=accountService.viewByCustomerId(customerId);
			res=new ResponseEntity<ArrayList<Account>>(account, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/checkByAccountId/{accountId}",method=RequestMethod.GET)
	public ResponseEntity<Boolean> checkByAccountId(@PathVariable("accountId") String accountId)
	{
		try {
			ResponseEntity<Boolean> res=new ResponseEntity<Boolean>(false, HttpStatus.OK);
			boolean result=accountService.checkById(accountId);
			res=new ResponseEntity<Boolean>(result, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/checkBalance/{accountId}",method=RequestMethod.GET)
	public ResponseEntity<Long> checkBalance(@PathVariable("accountId") String accountId)
	{
		try {
			ResponseEntity<Long> res=new ResponseEntity<Long>(0L, HttpStatus.OK);
			long result=accountService.checkBalance(accountId);
			res=new ResponseEntity<Long>(result, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/Deposit/{accountId}/{balance}",method=RequestMethod.GET)
	public ResponseEntity<Boolean> Transaction(@PathVariable("accountId") String accountId,@PathVariable("balance") long balance)
	{
		try {
			ResponseEntity<Boolean> res=new ResponseEntity<Boolean>(false, HttpStatus.OK);
			boolean result=accountService.Deposit(accountId,balance);
			res=new ResponseEntity<Boolean>(result, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/Withdraw/{accountId}/{balance}",method=RequestMethod.GET)
	public ResponseEntity<Boolean> Withdraw(@PathVariable("accountId") String accountId,@PathVariable("balance") long balance)
	{
		try {
			ResponseEntity<Boolean> res=new ResponseEntity<Boolean>(false, HttpStatus.OK);
			boolean result=accountService.Withdraw(accountId,balance);
			res=new ResponseEntity<Boolean>(result, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/getCustomerId/{accountId}",method=RequestMethod.GET)
	public ResponseEntity<String> getCustomerId(@PathVariable("accountId") String accountId)
	{
		try {
			ResponseEntity<String> res=new ResponseEntity<String>("", HttpStatus.OK);;
			String result=accountService.getCustomerId(accountId);
			res=new ResponseEntity<String>(result, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/checkCustomerExist/{customerId}",method=RequestMethod.GET)
	public ResponseEntity<Boolean> checkCustomerExist(@PathVariable("customerId") String customerId)
	{
		try {
			ResponseEntity<Boolean> res=new ResponseEntity<Boolean>(false, HttpStatus.OK);
			boolean result=accountService.checkCustomerExist(customerId);
			res=new ResponseEntity<Boolean>(result, HttpStatus.OK);
			return res;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
