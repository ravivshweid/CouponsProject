package com.raviv.coupons.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.raviv.coupons.beans.Customer;
import com.raviv.coupons.dao.interfaces.ICustomersDao;
import com.raviv.coupons.dao.utils.JdbcTransactionManager;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.exceptions.ApplicationException;

public class CustomersDao extends InfraDao implements ICustomersDao {

	public CustomersDao() 
	{
		super();
	}
	
	public CustomersDao(JdbcTransactionManager jdbcTransactionManager) {
		super(jdbcTransactionManager);
	}

	@Override
	public void 			createCustomer(Customer customer) throws ApplicationException {
	
		PreparedStatement 	preparedStatement	= null;
		Connection 			connection 			= null;
		ResultSet 			generatedKeys		= null;
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();


			// Creating a statement object which holds the SQL we're about to execute
			String sql;
			
			sql = "INSERT INTO CUSTOMERS (";
			sql += "	 CREATED_BY_USER_ID";
			sql += "	,UPDATED_BY_USER_ID";
			sql += "	,USER_ID";
			sql += "	,CUSTOMER_NAME";
			sql += ")";
			sql += "VALUES (?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			// Replacing question marks with the values inside  the bean.
			preparedStatement.setInt	(1 ,	customer.getCreatedByUserId() );
			preparedStatement.setInt	(2 ,	customer.getUpdatedByUserId() );
			preparedStatement.setInt	(3 ,	customer.getUserId() );
			preparedStatement.setString	(4 ,	customer.getCustomerName() );


			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();

			// Handle generated keys...
			generatedKeys = preparedStatement.getGeneratedKeys();
	        if ( generatedKeys.next() ) 
	        {
	        	int customerId = generatedKeys.getInt("GENERATED_KEYS");
	        	
	        	customer.setCustomerId( customerId );
	        }
	        
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_CREATE_ERROR, e, "Failed to create customer due to :" + e.getMessage() );
		} 
		finally 
		{
			if ( super.isJdbcTransactionManagerInUse() )
			{
				// Transaction manager will close the connection later.
				super.connectionPoolManager.closeResources(preparedStatement, generatedKeys);
			}
			else
			{
				// We do not have transaction manager.
				super.connectionPoolManager.closeResources(connection, preparedStatement, generatedKeys);
			}	
		}
		
	}
	
	@Override
	public Customer			getCustomer(long customerId) throws ApplicationException 
	{
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		Customer 			returnObj 			= new Customer();

		// getting the specific entry by the input id
		// storing it into resultSet
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			
			// execute query
			resultSet = preparedStatement.executeQuery();
	
			// Not found ...
			if ( !resultSet.next() ) { return null; } 
			
			//extract bean from result Set
			this.copyDataFromResultSetToBean (returnObj, resultSet);

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get customer with id : " + customerId + " " + e.getMessage());
		} 
		finally 
		{
			if ( super.isJdbcTransactionManagerInUse() )
			{
				// Transaction manager will close the connection later.
				super.connectionPoolManager.closeResources(preparedStatement, resultSet);
			}
			else
			{
				// We do not have transaction manager.
				super.connectionPoolManager.closeResources(connection, preparedStatement, resultSet);
			}	
		}
		
		return returnObj;
}

	public Customer 		getCustomerByUserId(long userId) throws ApplicationException {
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		Customer 			returnObj 			= new Customer();

	// getting the specific entry by the input id
	// storing it into resultSet
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql = "SELECT * FROM CUSTOMERS WHERE USER_ID = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, userId);
			
			// execute query
			resultSet = preparedStatement.executeQuery();
	
			// Not found ...
			if ( !resultSet.next() ) { return null; } 
						
			//extract bean from result Set
			this.copyDataFromResultSetToBean (returnObj, resultSet);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get customer with userId : " + userId + " " + e.getMessage());
		} 
		finally 
		{
			if ( super.isJdbcTransactionManagerInUse() )
			{
				// Transaction manager will close the connection later.
				super.connectionPoolManager.closeResources(preparedStatement, resultSet);
			}
			else
			{
				// We do not have transaction manager.
				super.connectionPoolManager.closeResources(connection, preparedStatement, resultSet);
			}				
		}
		
		return returnObj;
	}

	@Override
	public void 			updateCustomer(Customer customer) throws ApplicationException 
	{	
		PreparedStatement 	preparedStatement	= null;
		Connection 			connection 			= null;

		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			String sql;
			
			sql = "UPDATE CUSTOMERS SET ";
			sql += "	 SYS_UPDATE_DATE           = ?";
			sql += "	,UPDATED_BY_USER_ID        = ? ";
			sql += "	,USER_ID                   = ? ";
			sql += "	,CUSTOMER_NAME             = ? ";
			sql += "WHERE";
			sql += "      	CUSTOMER_ID = ? ";
			
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question marks with the beans values.
			preparedStatement.setTimestamp	(1 ,	 new Timestamp ( System.currentTimeMillis() ) );
			preparedStatement.setInt		(2 ,	customer.getUpdatedByUserId() );
			preparedStatement.setInt		(3 ,	customer.getUserId() );
			preparedStatement.setString		(4 ,	customer.getCustomerName() );
			preparedStatement.setLong		(5 ,	customer.getCustomerId() );


			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();	        
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_UPDATE_ERROR, e, "Failed to update customer due to :" + e.getMessage() );
		} 
		finally 
		{
			if ( super.isJdbcTransactionManagerInUse() )
			{
				// Transaction manager will close the connection later.
				super.connectionPoolManager.closeResources(preparedStatement);
			}
			else
			{
				// We do not have transaction manager.
				super.connectionPoolManager.closeResources(connection, preparedStatement);
			}
		}
	}// updateCustomer
	
	@Override
	public void 			deleteCustomer(long customerId) throws ApplicationException {
		PreparedStatement 	preparedStatement	= null;
		Connection 			connection 			= null;

		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			String sql;
			
			// =====================================================
			// Delete customer and related customer coupons
			// CUSTOMER_COUPON has FK to CUSTOMERS using customer id, with delete Cascade
			// =====================================================	
			
			sql = " DELETE FROM CUSTOMERS ";
			sql += "WHERE";
			sql += "      	CUSTOMER_ID = ? ";
			
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question marks with the beans values.
			preparedStatement.setLong ( 1 ,	customerId );


			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();	        
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_DELETE_ERROR, e, "Failed to delete customer due to :" + e.getMessage() );
		} 
		finally 
		{
			if ( super.isJdbcTransactionManagerInUse() )
			{
				// Transaction manager will close the connection later.
				super.connectionPoolManager.closeResources(preparedStatement);
			}
			else
			{
				// We do not have transaction manager.
				super.connectionPoolManager.closeResources(connection, preparedStatement);
			}
		}
		
	}

	public List<Customer> 	getAllCustomers() throws ApplicationException {
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		List<Customer>		allCustomers			= new ArrayList<Customer>();
		Customer 			customer;

	// getting the specific entry by the input id
	// storing it into resultSet
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql = "SELECT * FROM CUSTOMERS";
			
			preparedStatement = connection.prepareStatement(sql);
			
			// execute query
			resultSet = preparedStatement.executeQuery();
	
			// Loop through result set
			// For each company create bean and add it to output list
			while ( resultSet.next() ) 
			{
				customer = new Customer();
				//extract bean from result Set
				this.copyDataFromResultSetToBean (customer, resultSet);
				allCustomers.add(customer);
			} 
						
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get all customers" + e.getMessage());
		} 
		finally 
		{
			if ( super.isJdbcTransactionManagerInUse() )
			{
				// Transaction manager will close the connection later.
				super.connectionPoolManager.closeResources(preparedStatement, resultSet);
			}
			else
			{
				// We do not have transaction manager.
				super.connectionPoolManager.closeResources(connection, preparedStatement, resultSet);
			}				
		}
		
		return allCustomers;
	}
	
	private void 			copyDataFromResultSetToBean (Customer customer, ResultSet resultSet) throws SQLException
	{
		customer.setCustomerId     	( resultSet.getLong      ( "CUSTOMER_ID" ) );
		customer.setSysCreationDate	( resultSet.getTimestamp ( "SYS_CREATION_DATE" ).getTime() );
		customer.setSysUpdateDate  	( resultSet.getTimestamp ( "SYS_UPDATE_DATE" ).getTime() );
		customer.setCreatedByUserId	( resultSet.getInt       ( "CREATED_BY_USER_ID" ) );
		customer.setUpdatedByUserId	( resultSet.getInt       ( "UPDATED_BY_USER_ID" ) );
		customer.setUserId         	( resultSet.getInt       ( "USER_ID" ) );
		customer.setCustomerName   	( resultSet.getString    ( "CUSTOMER_NAME" ) );			
	}

}
