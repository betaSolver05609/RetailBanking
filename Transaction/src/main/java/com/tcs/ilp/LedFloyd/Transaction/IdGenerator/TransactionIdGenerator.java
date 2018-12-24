package com.tcs.ilp.LedFloyd.Transaction.IdGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class TransactionIdGenerator implements IdentifierGenerator{


	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		 String prefix = "TRAN";
	        Connection connection = session.connection();

	        try {
	            Statement statement=connection.createStatement();

	            ResultSet rs=statement.executeQuery("select count(TRANSACTION_ID) as Id from TBL_TRANSACTION_LEDFLOYD");

	            if(rs.next())
	            {
	                int id=rs.getInt(1)+100001;
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
