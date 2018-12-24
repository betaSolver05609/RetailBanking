package com.tcs.ilp.LedFloyd.Account.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.hibernate.HibernateException;
import org.hibernate.boot.registry.selector.spi.StrategySelectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tcs.ilp.LedFloyd.Account.Model.Account;
import com.tcs.ilp.LedFloyd.Account.Repo.AccountRepo;

@Component
public class AccountService {
	
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private ServiceInstance instance;
	@Autowired
	private LoadBalancerClient DC;
	//Create
		public String createAccount(Account account) throws HibernateException
		{
			//REST API CALL TO ACCOUNT TO CHECK CUSTOMERID
			RestTemplate restTemplate=new RestTemplate();
			
			//Api call to to check customer id
			instance=DC.choose("LedFloyd_Zuul_Proxy");
			String url=instance.getUri().toString();
			String url1=url+"/api/customer/checkCustomerByCustomerId/";
			final String uri=url1+account.getCustomerId();
			System.out.println(uri);
			ResponseEntity<Boolean> sup = restTemplate.getForEntity(uri, Boolean.class, HttpStatus.OK);

			//String url="http://localhost:7010/api/customer/checkCustomerByCustomerId/"+account.getCustomerId();
			Account addedAccount=null;
			if(sup.getBody()==true) {
				boolean b=true;
				boolean c=true;
			ArrayList<Account> list=viewByCustId(account.getCustomerId());
			for(Account acc:list)
			{
				if(acc.getAccountType().equalsIgnoreCase(account.getAccountType()) )
				{
					System.out.println(acc.getAccountType());
					c=false;
					if(acc.getStatus().equalsIgnoreCase("Inactive"))
					{
						//System.out.println(acc.getAccountId()+acc.getStatus());
						String id=acc.getAccountId();
						acc.setAccountId(id);
						acc.setStatus("Active");
						addedAccount=accountRepo.save(acc);
						return addedAccount.getAccountId();
					}
				}
			}
			
			
			if(c)
			{
				//System.out.println(account.getCustomerId());
				account.setStatus("Active");
				account.setCreationDate(new java.util.Date());
				addedAccount=accountRepo.save(account);
				if(addedAccount!=null)
				{
					return addedAccount.getAccountId();
				}
			}
			}
			return null;
		}
		
		
		//viewByAccountId
		public Account viewById(String id) throws HibernateException
		{
			try {
				Account acc=null;
				acc=accountRepo.findById(id).get();
				if(acc.getStatus().equalsIgnoreCase("Inactive"))
				{
					return null;
				}
				return acc;
			}
			catch(NoSuchElementException e)
			{
				return null;
			}
		}
		
		//getbycustomerid
		public ArrayList<Account> viewByCustId(String id) throws HibernateException
		{
			try {
				return (ArrayList<Account>)accountRepo.viewByCustomerId(id);
			}
			catch(NoSuchElementException e)
			{
				return null;
			}
		}
		
		//viewByCustomerId
		public ArrayList<Account> viewByCustomerId(String id) throws HibernateException
		{
			try {
				return filterDeleted((ArrayList<Account>)accountRepo.viewByCustomerId(id));
			}
			catch(NoSuchElementException e)
			{
				return null;
			}
		}
		
		//delete
		public boolean deleteAccount(String accountId) throws HibernateException
		{
			Account account=accountRepo.viewByAccountId(accountId);
			if((account==null)) {
				System.out.println("null");
				return false;
			
				}
			if(account.getStatus().equalsIgnoreCase("Inactive"))
			{
				//System.out.println("inactive");
				return false;
			}
			if(account.getBalance()==0) {
				//System.out.println(account.getAccountId()+"delete");
			account.setStatus("Inactive");
			accountRepo.save(account);
			return true;
			}
			return false;
		}
		
		//checkAccount
		public boolean checkAccount(String accountId) throws HibernateException
		{
			 Account account=accountRepo.viewByAccountId(accountId);
			 if(account==null)return false;
			if(account.getStatus().equalsIgnoreCase("Active"))
			{
				return true;
			}
			return false;
			
		}
		
		//filter out deleted customers
		public ArrayList<Account> filterDeleted(ArrayList<Account> list)
		{
			ArrayList<Account> filteredList=new ArrayList<Account>();
			for(Account account:list) {
				if(account.getStatus().equalsIgnoreCase("Active")) {
				filteredList.add(account);
				}
			
		}
			return filteredList;
		}
		
		//ViewAll
		public ArrayList<Account> viewAllAccounts() throws HibernateException
		{
			return filterDeleted((ArrayList<Account>)accountRepo.findAll());
		}
		
		
		//CheckByAccountId
		public boolean checkById(String id) throws HibernateException
		{
			Account account=accountRepo.viewByAccountId(id);
			 if(account==null)return false;
			 if(account.getStatus().equalsIgnoreCase("Inactive"))
				 return false;
			 return true;
		}
		
		//CheckBalanceForWithdraw
		public long checkBalance(String id)
		{
			Account account=accountRepo.viewByAccountId(id);
			 if(account==null)return 0;
			if(account.getStatus().equalsIgnoreCase("Inactive"))
			{
				return 0;
			}
			return account.getBalance();
		}
		
		//ViewCustomerIdByAccountId
		public String ViewCustomerId(String id)
		{
			Account account=accountRepo.viewByAccountId(id);
			 if(account==null)return null;
			 if(account.getStatus().equalsIgnoreCase("Active"))
			 return account.getCustomerId();
			 return null;
		}
		//Deposit
		public boolean Deposit(String aid,long amount)
		{
			Account account1=accountRepo.viewByAccountId(aid);
			if(account1==null)return false;
			if(account1.getStatus().equalsIgnoreCase("Inactive"))
				return false;
			long d=account1.getBalance()+amount;
			account1.setBalance(d);
			accountRepo.save(account1);
			
			return true;
		}
		//Withdraw
		public boolean Withdraw(String aid,long amount)
		{
			Account account=accountRepo.viewByAccountId(aid);
			if(account==null)return false;
			if(account.getStatus().equalsIgnoreCase("Inactive"))
				return false;
			if(account.getBalance()<amount)
			{
				return false;
			}
			long c=account.getBalance()-amount;
			account.setBalance(c);
			accountRepo.save(account);
			return true;
		}
		
		//getCustomerId
		public String getCustomerId(String accountId)
		{
			Account account=accountRepo.viewByAccountId(accountId);
			if(account==null)return null;
			return account.getCustomerId();
			
		}

		//checkcustomer
		public boolean checkCustomerExist(String customerId) {
			// TODO Auto-generated method stub
			ArrayList<Account> list=viewByCustomerId(customerId);
			for(Account acc:list)
			{
				System.out.println(acc.getStatus());
				if(acc.getStatus().equalsIgnoreCase("Active")) 
				{
					
					return false;
				}
			}
			return true;
		}
}
