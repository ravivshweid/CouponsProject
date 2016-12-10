package com.raviv.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.dao.interfaces.IUsersDao;
import com.raviv.coupons.dao.utils.JdbcTransactionManager;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.exceptions.ApplicationException;

public class UsersDao extends InfraDao implements IUsersDao {

	public 		UsersDao() {
		super();
		this.jdbcTransactionManager = null;
	}
	
	public 		UsersDao(JdbcTransactionManager jdbcTransactionManager) {
		super();
		this.jdbcTransactionManager = jdbcTransactionManager;
	}
		
	@Override
	public void createUser(User user) throws ApplicationException {
	
		PreparedStatement 	preparedStatement	= null;
		Connection 			connection 			= null;
		ResultSet 			generatedKeys		= null;

		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			// Creating a statement object which holds the SQL we're about to execute
			String sql;
			
			sql = "INSERT INTO USERS (";
			sql += "	 CREATED_BY_USER_ID";
			sql += "	,UPDATED_BY_USER_ID";
			sql += "	,USER_PROFILE_ID";
			sql += "	,USER_NAME";
			sql += "	,LOGIN_NAME";
			sql += "	,LOGIN_PASSWORD";
			sql += ")";
			sql += "VALUES (?, ?, ?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			// Replacing question marks with the values inside  the bean.
			preparedStatement.setInt	(1 ,	user.getCreatedByUserId() );
			preparedStatement.setInt	(2 ,	user.getUpdatedByUserId() );
			preparedStatement.setInt	(3 ,	user.getUserProfileId()   );
			preparedStatement.setString	(4 ,	user.getUserName()        );
			preparedStatement.setString	(5 ,	user.getLoginName()       );
			preparedStatement.setString	(6 ,	user.getLoginPassword()   );


			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();

			// Handle generated keys.
			generatedKeys = preparedStatement.getGeneratedKeys();
	        if ( generatedKeys.next() ) 
	        {
	        	int userId = generatedKeys.getInt("GENERATED_KEYS");
	        	
	        	user.setUserId( userId );
	        }
	        
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, e, "Failed to create user due to :" + e.getMessage() );

		} 
		finally 
		{
			if ( super.isJdbcTransactionManagerInUse() )
			{
				// We will close connection with transaction manager
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
	public User getUser(int userId) throws ApplicationException 
	{
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		User 				returnObj 			= new User();

		// get the specific entry by the input id
		// put it into result set
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userId);
			
			// execute query
			resultSet = preparedStatement.executeQuery();
	
			// Not found ...
			if ( !resultSet.next() ) { return null; } 
			
			//extract bean from result Set
			copyDataFromResultSetToBean ( returnObj, resultSet );
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, e, "Failed to get user with id : " + userId + " " + e.getMessage() );

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
	public void updateUser(User user) throws ApplicationException {
	
		PreparedStatement 	preparedStatement	= null;
		Connection 			connection 			= null;

		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			String sql;
			
			sql = "UPDATE USERS SET ";
			sql += "	 SYS_UPDATE_DATE           = ? ";
			sql += "	,UPDATED_BY_USER_ID        = ? ";
			sql += "	,USER_PROFILE_ID           = ? ";
			sql += "	,USER_NAME                 = ? ";
			sql += "	,LOGIN_NAME                = ? ";
			sql += "	,LOGIN_PASSWORD            = ? ";
			sql += "WHERE";
			sql += "      	USER_ID = ? ";
			
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question marks with the beans values.
			preparedStatement.setTimestamp	(1 ,	new Timestamp ( System.currentTimeMillis() ) );
			preparedStatement.setInt		(2 ,	user.getUpdatedByUserId() );
			preparedStatement.setInt		(3 ,	user.getUserProfileId() );
			preparedStatement.setString		(4 ,	user.getUserName() );
			preparedStatement.setString		(5 ,	user.getLoginName() );
			preparedStatement.setString		(6 ,	user.getLoginPassword() );
			preparedStatement.setInt		(7 ,	user.getUserId() );

			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();	        
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, e, "Failed to upadte user due to :" + e.getMessage() );

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

	@Override
	public void deleteUser(int userId) {
		// TODO Auto-generated method stub
		
	}

	public User getUserByLoginNameLoginPassword(String  loginName, String loginPassword) throws ApplicationException 
	{
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		User 				returnObj 			= new User();

		// get the specific entry by the input id
		// put it into result set
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();			
			
			String sql = "SELECT * FROM USERS WHERE LOGIN_NAME = ? AND LOGIN_PASSWORD = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, loginName);
			preparedStatement.setString(2, loginPassword);
			
			// execute query
			resultSet = preparedStatement.executeQuery();
	
			// Not found ...
			if ( !resultSet.next() ) { return null; } 
			
			//extract bean from result Set
			copyDataFromResultSetToBean ( returnObj, resultSet );
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, e
					, "Failed to get user with loginName : " + loginName + ",  loginPassword : " + loginPassword + "." + e.getMessage() );
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

	public User getUserByLoginNameLoginPasswordUserProfileId(String  loginName, String loginPassword, int userProfileId) throws ApplicationException 
	{
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		User 				returnObj 			= new User();

		// get the specific entry by the input id
		// put it into result set
		try 
		{
			connection = super.getConnection();
			
			String sql = "SELECT * FROM USERS WHERE LOGIN_NAME = ? AND LOGIN_PASSWORD = ? AND USER_PROFILE_ID = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, loginName);
			preparedStatement.setString(2, loginPassword);
			preparedStatement.setInt   (3, userProfileId);
			
			// execute query
			resultSet = preparedStatement.executeQuery();
	
			// Not found ...
			if ( !resultSet.next() ) { return null; } 
			
			//extract bean from result Set
			copyDataFromResultSetToBean ( returnObj, resultSet );
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, e
					, "Failed to get user with loginName : " + loginName 
					+ ",  loginPassword : " + loginPassword
					+ ",  userProfileId : " + userProfileId
					+ "." + e.getMessage() );
		} 
		finally 
		{
			super.connectionPoolManager.closeResources(connection, preparedStatement, resultSet);
		}
		
		return returnObj;
}
	
	private void copyDataFromResultSetToBean (User user, ResultSet resultSet) throws SQLException
	{
		user.setUserId         	( resultSet.getInt       ( "USER_ID" ) );
		user.setSysCreationDate	( resultSet.getTimestamp ( "SYS_CREATION_DATE" ).getTime() );
		user.setSysUpdateDate  	( resultSet.getTimestamp ( "SYS_UPDATE_DATE" ).getTime() );
		user.setCreatedByUserId	( resultSet.getInt       ( "CREATED_BY_USER_ID" ) );
		user.setUpdatedByUserId	( resultSet.getInt       ( "UPDATED_BY_USER_ID" ) );
		user.setUserProfileId  	( resultSet.getInt       ( "USER_PROFILE_ID" ) );
		user.setUserName       	( resultSet.getString    ( "USER_NAME" ) );
		user.setLoginName      	( resultSet.getString    ( "LOGIN_NAME" ) );
		user.setLoginPassword  	( resultSet.getString    ( "LOGIN_PASSWORD" ) );			
	}
	
}
