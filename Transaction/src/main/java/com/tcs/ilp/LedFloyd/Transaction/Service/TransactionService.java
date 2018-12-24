package com.tcs.ilp.LedFloyd.Transaction.Service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tcs.ilp.LedFloyd.Transaction.Model.Transaction;
import com.tcs.ilp.LedFloyd.Transaction.Repo.TransactionRepo;


@Service
public class TransactionService {
	@Autowired
	private TransactionRepo transactionRepo;
	@Autowired 
	private ServiceInstance instance;
	/*@Autowired
	private DiscoveryClient DC;*/
	@Autowired 
	private LoadBalancerClient DC;
	public String Deposit(Transaction transaction)
	{
		Transaction tran=null;
		//REST API CALL TO ACCOUNT TO DEPOSIT/WITHDRAW AMOUNT
		RestTemplate restTemplate=new RestTemplate();
		
		//Api call to deposit/withdraw Amount
		
		instance=DC.choose("LedFloyd_Zuul_Proxy");
		String url=instance.getUri().toString();
		String url1=url+"/api/account/checkByAccountId/";
		final String uri=url1+transaction.getAccountId();
		ResponseEntity<Boolean> b=restTemplate.getForEntity(uri, Boolean.class, HttpStatus.OK);
		boolean result=b.getBody();
		
		if(result)
		{
			instance=DC.choose("LedFloyd_Zuul_Proxy");
			String urlr=instance.getUri().toString();
			String url2=urlr+"/api/account/Deposit/";
			final String uri1=url2+transaction.getAccountId()+"/"+transaction.getAmount();
			ResponseEntity<Boolean> c=restTemplate.getForEntity(uri1, Boolean.class, HttpStatus.OK);
			if(c.getBody()==true)
			{
				instance=DC.choose("LedFloyd_Zuul_Proxy");
				String urlk=instance.getUri().toString();
				String url3=urlk+"/api/account/getCustomerId/";
				final String urip=url3+transaction.getAccountId();
				
				ResponseEntity<String> custid=restTemplate.getForEntity(urip, String.class, HttpStatus.OK);
				transaction.setCustomerId(custid.getBody());
				transaction.setTimestamp(new java.util.Date());
				transaction.setTransactionType("Deposit");
				tran=transactionRepo.save(transaction);
				if(tran!=null)
					return tran.getTransactionId();
			}
		}
//		//api call to get customerId
//		url="http://localhost:7020/api/account/getCustomerId/"+accountId;
//		ResponseEntity<Integer> c=restTemplate.getForEntity(url, Integer.class);
//		transaction.setCustomerId(c.getBody());
//		transaction.setTimestamp(new Date());
//		//Making entry of transaction into transaction tables for Destination Account
//		transaction=transactionRepo.save(transaction);
//		//Making entry of transaction into transaction tables for Source Account
//		transaction.setAccountId(sourceAccountId);
//		transaction=transactionRepo.save(transaction);
//		if(transaction!=null)
//			return transaction.getTransactionId();
//		else
//			return "";
		return null;
			
	}
	
	public String Withdraw(Transaction transaction)
	{

		Transaction tran=null;
		//REST API CALL TO ACCOUNT TO DEPOSIT/WITHDRAW AMOUNT
		RestTemplate restTemplate=new RestTemplate();
		
		//Api call to deposit/withdraw Amount
		
		instance=DC.choose("LedFloyd_Zuul_Proxy");
		String url=instance.getUri().toString();
		String url1=url+"/api/account/checkByAccountId/";
		final String uri=url1+transaction.getAccountId();
		ResponseEntity<Boolean> b=restTemplate.getForEntity(uri, Boolean.class, HttpStatus.OK);
		boolean result=b.getBody();
		if(result)
		{
			instance=DC.choose("LedFloyd_Zuul_Proxy");
			String urlr=instance.getUri().toString();
			String url2=urlr+"/api/account/Withdraw/";
			final String uri1=url2+transaction.getAccountId()+"/"+transaction.getAmount();
			ResponseEntity<Boolean> c=restTemplate.getForEntity(uri1, Boolean.class, HttpStatus.OK);
			if(c.getBody()==true)
			{
				instance=DC.choose("LedFloyd_Zuul_Proxy");
				String urlk=instance.getUri().toString();
				String url3=urlk+"/api/account/getCustomerId/";
				final String urip=url3+transaction.getAccountId();
				System.out.println(urip);
				ResponseEntity<String> custid=restTemplate.getForEntity(urip, String.class, HttpStatus.OK);
				transaction.setCustomerId(custid.getBody());
				//String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//				transaction.setTimestamp(new java.util.Date());
				Timestamp sqltimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
				System.out.println(sqltimestamp);
				transaction.setTimestamp(sqltimestamp);
				transaction.setTransactionType("Withdraw");
				tran=transactionRepo.save(transaction);
				if(tran!=null)
					return tran.getTransactionId();
			}
		}
		return null;
	}
	public String nextDay(String edate)
	{
		int end[]={31,28,31,30,31,30,31,31,30,31,30,31};
		StringTokenizer st=new StringTokenizer(edate,"-");
		int y=Integer.parseInt(st.nextToken());
		int m=Integer.parseInt(st.nextToken());
		int d=Integer.parseInt(st.nextToken());
		if(isLeap(y))
		    end[1]=29;
		if((m==12)&&(d==31))
		{
		    d=1;
		    m=1;
		    y++;
		}
		else if(d==(end[m+1]))
		{
		    d=1;
		    m++;
		}
		else
		{
		    d++;
		}
		String dd=(d>9?"":"0")+d;
		String mm=(m>9?"":"0")+m;
		return ""+y+"-"+mm+"-"+dd;
		
		
	}
	public boolean isLeap(int y)
	{
	    if((y%4==0)&&(y%100!=0))
	        return true;
	    else if(y%400==0)   
	        return true;
	   return false;
	}
	public ArrayList<Transaction> viewAllTransaction(String sdate,String edate)
	{
		 ArrayList<Transaction> list= (ArrayList<Transaction>)transactionRepo.getAll(sdate,nextDay(edate));
		 ArrayList<Transaction> refined=new ArrayList<Transaction>();
		 RestTemplate restTemplate=new RestTemplate();		
		 instance=DC.choose("LedFloyd_Zuul_Proxy");
		 String url=instance.getUri().toString();
		 String url1=url+"/api/account/checkByAccountId/";
		 for(Transaction transaction:list)
		 {
			 	
				String uri=url1+transaction.getAccountId();
				ResponseEntity<Boolean> b=restTemplate.getForEntity(uri, Boolean.class, HttpStatus.OK);
				boolean result=b.getBody();
				if(result==true) 
					refined.add(transaction);
		 }
		 return refined;
	}
	public ArrayList<Transaction> viewByAccountId(String accountId,String sdate,String edate)
	{
		RestTemplate restTemplate=new RestTemplate();
		
		//Api call to deposit/withdraw Amount
		
		instance=DC.choose("LedFloyd_Zuul_Proxy");
		String url=instance.getUri().toString();
		String url1=url+"/api/account/checkByAccountId/";
		final String uri=url1+accountId;
		ResponseEntity<Boolean> b=restTemplate.getForEntity(uri, Boolean.class, HttpStatus.OK);
		boolean result=b.getBody();
		if(result==true) {
		return transactionRepo.viewByAccountId(accountId,sdate,nextDay(edate));
		}
		return null;
	}
	public ArrayList<Transaction> viewByCustomerId(String customerId,String sdate,String edate)
	{
		RestTemplate restTemplate=new RestTemplate();
		instance=DC.choose("LedFloyd_Zuul_Proxy");
		String url=instance.getUri().toString();
		String url1=url+"/api/customer/checkCustomerByCustomerId/";
		final String uri=url1+customerId;
		System.out.println(uri);
		ResponseEntity<Boolean> sup = restTemplate.getForEntity(uri, Boolean.class, HttpStatus.OK);
		if(sup.getBody()==true) {
		return transactionRepo.viewByCustomerId(customerId,sdate,nextDay(edate));
		}
		return null;
	}
}
		