package com.raviv.coupons.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.raviv.coupons.dao.utils.ConnectionPoolManager;
import com.raviv.coupons.dao.utils.JdbcTransactionManager;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.exceptions.ApplicationException;

public class InfraDao {

	protected ConnectionPoolManager connectionPoolManager;

	protected JdbcTransactionManager jdbcTransactionManager;

	protected Connection getConnection() throws ApplicationException
	{
		if ( isJdbcTransactionManagerInUse() )
		{
			// Getting a connection from the Transaction manager
			return this.jdbcTransactionManager.getConnection();
		}
		else
		{
			// Getting a connection from the connections pool manager (getConnection is a static method)
			try 
			{
				return this.connectionPoolManager.getConnection();
			} 
			catch (SQLException e) 
			{
				//e.printStackTrace();
				throw new ApplicationException(ErrorType.GENERAL_ERROR, e, "JdbcUtils, Failed to get connection :" + e.getMessage() );
			}
		}
	
	}

	protected boolean isJdbcTransactionManagerInUse()
	{
		if ( this.jdbcTransactionManager == null )
		{
			return false;
		}
	
		return true;
	}
	
	public 		InfraDao() {
		super();
		this.jdbcTransactionManager = null;
		this.connectionPoolManager = ConnectionPoolManager.getInstance();
	}
	
	public 		InfraDao(JdbcTransactionManager jdbcTransactionManager) {
		super();
		this.jdbcTransactionManager = jdbcTransactionManager;
		this.connectionPoolManager = ConnectionPoolManager.getInstance();
	}
	
}
