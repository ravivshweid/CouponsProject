package com.raviv.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.raviv.coupons.beans.Company;
import com.raviv.coupons.dao.interfaces.ICompanysDao;
import com.raviv.coupons.dao.utils.JdbcTransactionManager;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.exceptions.ApplicationException;

public class CompanysDao extends InfraDao implements ICompanysDao {
		
	public CompanysDao() 
	{
		super();
	}
	
	public CompanysDao(JdbcTransactionManager jdbcTransactionManager) {
		super(jdbcTransactionManager);
	}

	@Override
	public void 			createCompany(Company company) throws ApplicationException {
		
		PreparedStatement 	preparedStatement	= null;
		Connection 			connection 			= null;
		ResultSet 			generatedKeys		= null;

		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
				
			// Creating a statement object which holds the SQL we're about to execute
			String sql;
			
			sql = "INSERT INTO COMPANYS (";
			sql += "	 CREATED_BY_USER_ID";
			sql += "	,UPDATED_BY_USER_ID";
			sql += "	,USER_ID";
			sql += "	,COMPANY_NAME";
			sql += "	,COMPANY_EMAIL";
			sql += ")";
			sql += "VALUES (?, ?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			// Replacing question mark with the value inside bean
			preparedStatement.setInt	(1 ,	company.getCreatedByUserId() );
			preparedStatement.setInt	(2 ,	company.getUpdatedByUserId() );
			preparedStatement.setInt	(3 ,	company.getUserId() );
			preparedStatement.setString	(4 ,	company.getCompanyName() );
			preparedStatement.setString	(5 ,	company.getCompanyEmail() );
			
			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();

			// Handle generated keys...
			generatedKeys = preparedStatement.getGeneratedKeys();			
	        if ( generatedKeys.next() ) 
	        {
	        	long identityId = generatedKeys.getLong("GENERATED_KEYS");
	        	company.setCompanyId( identityId );
	        }
	        else 
	        {
	            throw new SQLException("Creating company failed, no ID obtained.");
	        }
	        
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_CREATE_ERROR, e, "Failed to create company due to :" + e.getMessage() );
		} 
		finally 
		{
			if ( super.isJdbcTransactionManagerInUse() )
			{	
				// We will close connection with Transaction manager
				this.connectionPoolManager.closeResources(preparedStatement, generatedKeys);
			}
			else
			{
				this.connectionPoolManager.closeResources(connection, preparedStatement, generatedKeys);
			}
		}
	}
	
	@Override
	public Company 			getCompany(long companyId) throws ApplicationException {
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		Company 			returnObj 			= new Company();

	// getting the specific entry by the input id
	// storing it into resultSet
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql = "SELECT * FROM COMPANYS WHERE COMPANY_ID = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyId);
			
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
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get company with id : " + companyId + " " + e.getMessage());
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

	public Company 			getCompanyByUserId(long userId) throws ApplicationException {
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		Company 			returnObj 			= new Company();

	// getting the specific entry by the input id
	// storing it into resultSet
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql = "SELECT * FROM COMPANYS WHERE USER_ID = ?";
			
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
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get company with userId : " + userId + " " + e.getMessage());
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

	public List<Company> 	getAllCompanys() throws ApplicationException {
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		List<Company>		allCompanys			= new ArrayList<Company>();
		Company 			company;

	// getting the specific entry by the input id
	// storing it into resultSet
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql = "SELECT * FROM COMPANYS";
			
			preparedStatement = connection.prepareStatement(sql);
			
			// execute query
			resultSet = preparedStatement.executeQuery();
	
			// Loop through result set
			// For each company create bean and add it to output list
			while ( resultSet.next() ) 
			{
				company = new Company();
				//extract bean from result Set
				this.copyDataFromResultSetToBean (company, resultSet);
				allCompanys.add(company);
			} 
						
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get all companys" + e.getMessage());
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
		
		return allCompanys;
	}

	@Override
	public void 			updateCompany(Company company) throws ApplicationException {
		PreparedStatement 	preparedStatement	= null;
		Connection 			connection 			= null;

		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			String sql;
			
			sql = "UPDATE COMPANYS SET ";
			sql += "	 SYS_UPDATE_DATE           = ?";
			sql += "	,UPDATED_BY_USER_ID        = ? ";
			sql += "	,USER_ID                   = ? ";
			sql += "	,COMPANY_NAME              = ? ";
			sql += "	,COMPANY_EMAIL             = ? ";
			sql += "WHERE";
			sql += "      	COMPANY_ID = ? ";
			
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with the value inside bean
			preparedStatement.setTimestamp	(1 ,	new Timestamp ( System.currentTimeMillis() ) );
			preparedStatement.setInt		(2 ,	company.getUpdatedByUserId() );
			preparedStatement.setInt		(3 ,	company.getUserId() );
			preparedStatement.setString		(4 ,	company.getCompanyName() );
			preparedStatement.setString		(5 ,	company.getCompanyEmail() );
			preparedStatement.setLong		(6 ,	company.getCompanyId() );
			

			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();
	        
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_UPDATE_ERROR, e, "Failed to update company due to :" + e.getMessage() );
		} 
		finally 
		{				
			if ( super.isJdbcTransactionManagerInUse() )
			{
				// Transaction manager will close the connection later.
				this.connectionPoolManager.closeResources(preparedStatement);
			}
			else
			{
				// We do not have transaction manager.
				this.connectionPoolManager.closeResources(connection, preparedStatement);
			}		
		}
		
	}

	@Override
	public void 			deleteCompany(long companyId) throws ApplicationException 
	{
		PreparedStatement 	preparedStatement	= null;
		Connection 			connection 			= null;

		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			String sql;
			
			// =====================================================
			// Delete company, related coupons and related customer coupons
			// COUPONS         has FK to COMPANYS using company id, with delete Cascade
			// CUSTOMER_COUPON has FK to COUPONS  using company id, with delete Cascade
			// =====================================================	
			
			sql = "  DELETE FROM COMPANYS ";
			sql += " WHERE";
			sql += "      	COMPANY_ID = ? ";
			
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with the input
			preparedStatement.setLong ( 1 ,	companyId );
			

			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();
	        
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_DELETE_ERROR, e, "Failed to delete company due to :" + e.getMessage() );
		} 
		finally 
		{				
			if ( super.isJdbcTransactionManagerInUse() )
			{
				// Transaction manager will close the connection later.
				this.connectionPoolManager.closeResources(preparedStatement);
			}
			else
			{
				// We do not have transaction manager.
				this.connectionPoolManager.closeResources(connection, preparedStatement);
			}		
		}
				
	}

	private void 			copyDataFromResultSetToBean (Company company, ResultSet resultSet) throws SQLException
	{
		company.setCompanyId      	( resultSet.getLong      ( "COMPANY_ID" ) );
		company.setSysCreationDate	( resultSet.getTimestamp ( "SYS_CREATION_DATE" ).getTime() );
		company.setSysUpdateDate  	( resultSet.getTimestamp ( "SYS_UPDATE_DATE" ).getTime() );
		company.setCreatedByUserId	( resultSet.getInt       ( "CREATED_BY_USER_ID" ) );
		company.setUpdatedByUserId	( resultSet.getInt       ( "UPDATED_BY_USER_ID" ) );
		company.setUserId         	( resultSet.getInt       ( "USER_ID" ) );
		company.setCompanyName    	( resultSet.getString    ( "COMPANY_NAME" ) );
		company.setCompanyEmail   	( resultSet.getString    ( "COMPANY_EMAIL" ) );
	}
	
}
