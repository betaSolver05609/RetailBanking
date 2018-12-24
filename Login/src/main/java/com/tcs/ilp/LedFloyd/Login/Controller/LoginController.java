package com.tcs.ilp.LedFloyd.Login.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.ilp.LedFloyd.Login.Model.Login;
import com.tcs.ilp.LedFloyd.Login.Service.LoginService;
@CrossOrigin(origins="*")
@RestController
public class LoginController {
	
	@Autowired
	LoginService loginService;

	@RequestMapping(value = "/Validate", method = RequestMethod.POST)
	public ResponseEntity<Login> validateUser(@RequestBody Login login)
	{
		ResponseEntity<Login> result = null;
		Login l = loginService.validate(login.getUsername(), login.getPassword());
		if(l!=null)
		{
			result = new ResponseEntity<Login>(l,HttpStatus.OK);
		}
		return result;
	}
	@RequestMapping(value = "/authorizeLogin", method = RequestMethod.POST)
	public ResponseEntity<Login> authorizeLogin(@RequestBody Login login)
	{
		ResponseEntity<Login> result = null;
		Login l = loginService.authorizeLogin(login.getUsername(), login.getPassword());
		if(l!=null)
		{
			result = new ResponseEntity<Login>(l,HttpStatus.OK);
		}
		return result;
	}
	
	@RequestMapping(value = "/Invalidate/{username}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> invalidateUser(@PathVariable("username") String username)
	{
		ResponseEntity<Boolean> result = new ResponseEntity<Boolean>(false, HttpStatus.OK);
		boolean b = loginService.invalidate(username);
		if(b)
		{
			result = new ResponseEntity<Boolean>(true,HttpStatus.OK);
		}
		else
		{
			result = new ResponseEntity<Boolean>(false,HttpStatus.OK);
		}
		return result;
	}
	
	@RequestMapping(value = "/HiddenAdd", method = RequestMethod.POST)
	public ResponseEntity<Boolean> hiddenAdd(@RequestBody Login login)
	{
		ResponseEntity<Boolean> result = null;
		boolean b = loginService.hiddenAdd(login);
		if(b)
		{
			result = new ResponseEntity<Boolean>(true,HttpStatus.OK);
		}
		else
		{
			result = new ResponseEntity<Boolean>(false,HttpStatus.OK);
		}
		return result;
	}
}
