package com.tcs.ilp.LedFloyd.Account.IdGenerator;

import java.io.Serializable;
import java.sql.*;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;


public class	AccountIdGenerator implements IdentifierGenerator{


	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		 String prefix = "ACC";
	        Connection connection = session.connection();

	        try {
	            Statement statement=connection.createStatement();

	            ResultSet rs=statement.executeQuery("select count(Account_ID) as Id from TBL_Account_LEDFLOYD");

	            if(rs.next())
	            {
	                int id=rs.getInt(1)+101;
	                String generatedId = prefix + new Integer(id).toString();
	                System.out.println("Generated Id: " + generatedId);
	                return generatedId;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }


	        return null;
	}

}
