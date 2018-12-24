package com.tcs.ilp.LedFloyd.Login.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.ilp.LedFloyd.Login.Model.Login;
import com.tcs.ilp.LedFloyd.Login.Repo.LoginRepo;

@Service
public class LoginService {

	@Autowired
	LoginRepo loginRepo;
	public Login authorizeLogin(String username, String password)
	{
		Login login=validate(username, password);
		if(login==null)
		{
			return null;
		}
		else if(!(login.isActive()))
		{
			login.setActive(true);
			login.setLastUsed(new java.util.Date());
			loginRepo.save(login);
			return login;
		}
		else
		{
			return new Login("", "", "Already Active", false, null);
		}
	}
	
	public Login validate(String username, String password)
	{
		try
		{
			Login l = loginRepo.findById(username).get();
			if(l!=null)
			{
				if(l.getPassword().equals(password))
				{
					return l;
				}
				else
					return null;
			}
			else
				return null;
			
		}
		catch(Exception ex)
		{
			return null;
		}
		
	}
	
	public Boolean invalidate(String username)
	{
		try
		{
			Login l = loginRepo.findById(username).get();
			System.out.println("HELLO" + l);
			if(l!=null)
			{
				l.setActive(false);
				loginRepo.save(l);
				return true;
			}
			else
			return false;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public boolean hiddenAdd(Login l)
	{
		l.setActive(false);
		l.setLastUsed(new java.util.Date());
		l = loginRepo.save(l);
		if(l!=null)
			return true;
		else
			return false;
	}
	
}
