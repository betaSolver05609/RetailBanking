package com.tcs.ilp.LedFloyd.Transaction.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.ilp.LedFloyd.Transaction.Model.Transaction;
import com.tcs.ilp.LedFloyd.Transaction.Service.TransactionService;

@CrossOrigin(origins="*")
@RestController
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	@RequestMapping(value="/deposit", method=RequestMethod.POST)
	public ResponseEntity<String> deposit(@RequestBody Transaction transaction)
	{
		ResponseEntity<String> res=new ResponseEntity<String>("", HttpStatus.OK);

		String b=transactionService.Deposit(transaction);
		 res=new ResponseEntity<String>(b, HttpStatus.OK);
		return res;
	}
	@RequestMapping(value="/withdraw", method=RequestMethod.POST)
	public ResponseEntity<String> withdraw(@RequestBody Transaction transaction)
	{
		ResponseEntity<String> res=new ResponseEntity<String>("", HttpStatus.OK);

		String b=transactionService.Withdraw(transaction);
		res=new ResponseEntity<String>(b, HttpStatus.OK);
		return res;
	}
	@RequestMapping(value="/viewAllTransaction/{sdate}/{edate}", method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Transaction>> viewAllTransaction(@PathVariable("sdate") String sdate,@PathVariable("edate") String edate)
	{
		ResponseEntity<ArrayList<Transaction>> res=null;

		return new ResponseEntity<ArrayList<Transaction>>(transactionService.viewAllTransaction(sdate,edate), HttpStatus.OK);
	}
	@RequestMapping(value="/viewByAccountId/{accountId}/{sdate}/{edate}", method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Transaction>> viewByAccountId(@PathVariable("accountId") String accountId,@PathVariable("sdate") String sdate,@PathVariable("edate") String edate)
	{
		return new ResponseEntity<ArrayList<Transaction>>(transactionService.viewByAccountId(accountId,sdate,edate), HttpStatus.OK);
	}
	@RequestMapping(value="/viewByCustomerId/{customerId}/{sdate}/{edate}", method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Transaction>> viewByCustomerId(@PathVariable("customerId") String customerId,@PathVariable("sdate") String sdate,@PathVariable("edate") String edate)
	{
		return new ResponseEntity<ArrayList<Transaction>>(transactionService.viewByCustomerId(customerId,sdate,edate), HttpStatus.OK);
	}

	
	

}
