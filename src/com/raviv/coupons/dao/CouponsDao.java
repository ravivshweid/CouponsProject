package com.raviv.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.raviv.coupons.beans.Coupon;
import com.raviv.coupons.blo.DynamicQueryParameters;
import com.raviv.coupons.dao.interfaces.ICouponsDao;
import com.raviv.coupons.dao.utils.JdbcTransactionManager;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.utils.YyyyMmDd;

public class CouponsDao extends InfraDao implements ICouponsDao {

	/**
	 * Default constructor
	 */
	public 					CouponsDao() 
	{
		super();
	}
	
	public 					CouponsDao(JdbcTransactionManager jdbcTransactionManager) {
		super(jdbcTransactionManager);
	}

	@Override
	public void 			createCoupon(Coupon coupon) throws ApplicationException {
	
		PreparedStatement 	preparedStatement	= null;
		Connection 			connection 			= null;
		ResultSet 			generatedKeys		= null;


		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			String sql;
			
			sql = "INSERT INTO COUPONS (";
			sql += "	 CREATED_BY_USER_ID";
			sql += "	,UPDATED_BY_USER_ID";
			sql += "	,COMPANY_ID";
			sql += "	,COUPON_TITLE";
			sql += "	,COUPON_START_DATE";
			sql += "	,COUPON_END_DATE";
			sql += "	,COUPONS_IN_STOCK";
			sql += "	,COUPON_TYPE_ID";
			sql += "	,COUPON_MESSAGE";
			sql += "	,COUPON_PRICE";
			sql += "	,IMAGE_FILE_NAME";
			sql += ")";
			sql += "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			// Replacing question marks with the values inside  the bean.
			preparedStatement.setInt		(1 ,	coupon.getCreatedByUserId() );
			preparedStatement.setInt		(2 ,	coupon.getUpdatedByUserId() );
			preparedStatement.setLong		(3 ,	coupon.getCompanyId() );
			preparedStatement.setString		(4 ,	coupon.getCouponTitle() );
			preparedStatement.setTimestamp	(5 ,	new Timestamp ( coupon.getCouponStartDate() ) );
			preparedStatement.setTimestamp	(6 ,	new Timestamp ( coupon.getCouponEndDate() )   );
			preparedStatement.setInt		(7 ,	coupon.getCouponsInStock() );
			preparedStatement.setInt		(8 ,	coupon.getCouponTypeId() );
			preparedStatement.setString		(9 ,	coupon.getCouponMessage() );
			preparedStatement.setDouble 	(10,	coupon.getCouponPrice() );
			preparedStatement.setString		(11,	coupon.getImageFileName() );


			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();

			// Handle generated keys...
			generatedKeys = preparedStatement.getGeneratedKeys();
	        if ( generatedKeys.next() ) 
	        {
	        	int couponId = generatedKeys.getInt("GENERATED_KEYS");
	        	
	        	coupon.setCouponId( couponId );
	        }
	        
	        
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_CREATE_ERROR, e, "Failed to create coupon due to :" + e.getMessage() );
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
	public Coupon 			getCoupon(long couponId) throws ApplicationException 
	{
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		Coupon 				returnObj 			= new Coupon();

		// getting the specific entry by the input id
		// storing it into resultSet
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql = "SELECT * FROM COUPONS WHERE COUPON_ID = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponId);
			
			// execute query
			resultSet = preparedStatement.executeQuery();
	
			// Not found ...
			if ( !resultSet.next() ) { return null; } 
			
			
			//extract bean from result Set
			this.copyDataFromResultSetToBean (returnObj, resultSet);			
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get coupon with id : " + couponId + " " + e.getMessage());
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
	public void 			updateCoupon(Coupon coupon) throws ApplicationException 
	{
		PreparedStatement 	preparedStatement	= null;
		Connection 			connection 			= null;

		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			String sql;
			
			sql = "UPDATE COUPONS SET ";
			sql += "	 SYS_UPDATE_DATE           = ?";
			sql += "	,UPDATED_BY_USER_ID        = ? ";
			sql += "	,COMPANY_ID                = ? ";
			sql += "	,COUPON_TITLE              = ? ";
			sql += "	,COUPON_START_DATE         = ? ";
			sql += "	,COUPON_END_DATE           = ? ";
			sql += "	,COUPONS_IN_STOCK          = ? ";
			sql += "	,COUPON_TYPE_ID            = ? ";
			sql += "	,COUPON_MESSAGE            = ? ";
			sql += "	,COUPON_PRICE              = ? ";
			sql += "	,IMAGE_FILE_NAME           = ? ";
			sql += "WHERE";
			sql += "      	COUPON_ID = ? ";
			
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question marks with the values inside  the bean.
			preparedStatement.setTimestamp	(1 ,	new Timestamp ( System.currentTimeMillis() ) );
			preparedStatement.setInt		(2 ,	coupon.getUpdatedByUserId() );
			preparedStatement.setLong		(3 ,	coupon.getCompanyId() );
			preparedStatement.setString		(4 ,	coupon.getCouponTitle() );
			preparedStatement.setTimestamp	(5 ,	new Timestamp ( coupon.getCouponStartDate() ) );
			preparedStatement.setTimestamp	(6 ,	new Timestamp ( coupon.getCouponEndDate() ) );
			preparedStatement.setInt		(7 ,	coupon.getCouponsInStock() );
			preparedStatement.setInt		(8 ,	coupon.getCouponTypeId() );
			preparedStatement.setString		(9 ,	coupon.getCouponMessage() );
			preparedStatement.setDouble		(10,	coupon.getCouponPrice() );
			preparedStatement.setString		(11,	coupon.getImageFileName() );
			preparedStatement.setLong		(12,	coupon.getCouponId() );

			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();	        
	        
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_UPDATE_ERROR, e, "Failed to update coupon due to :" + e.getMessage() );
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
	public void 			deleteCoupon(long couponId) throws ApplicationException 
	{
		PreparedStatement 	preparedStatement	= null;
		Connection 			connection 			= null;

		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			String sql;
			
			// CUSTOMER_COUPON has FK to COUPONS with delete Cascade
			
			sql = "  DELETE FROM COUPONS ";
			sql += " WHERE";
			sql += "      	COUPON_ID = ? ";
			
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question marks with the values inside  the bean.
			preparedStatement.setLong		( 1,	couponId );

			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();	        
	        
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_DELETE_ERROR, e, "Failed to delete coupon due to :" + e.getMessage() );
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

	public List<Coupon> 	getCouponsByCompanyId(long companyId) throws ApplicationException {
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		List<Coupon>		coupons				= new ArrayList<Coupon>();
		Coupon 				coupon;

	// getting the specific entry by the input id
	// storing it into resultSet
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql = "SELECT * FROM COUPONS WHERE COMPANY_ID = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyId);
			
			// execute query
			resultSet = preparedStatement.executeQuery();
	
			// Loop through result set
			// For each company create bean and add it to output list
			while ( resultSet.next() ) 
			{
				coupon = new Coupon();
				//extract bean from result Set
				this.copyDataFromResultSetToBean (coupon, resultSet);
				coupons.add(coupon);
			} 
						
			
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get coupons by companyId : " + companyId + ". " + e.getMessage());
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
		
		return coupons;
	}

	public List<Coupon> 	getCouponsByCompanyIdAndDynamicFilter(long companyId, DynamicQueryParameters dynamicQueryParameters) throws ApplicationException {
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		List<Coupon>		coupons				= new ArrayList<Coupon>();
		Coupon 				coupon;

	// getting the specific entry by the input id
	// storing it into resultSet
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql;
			sql = "";
			sql += "\n  SELECT * FROM COUPONS ";
			sql += "\n  WHERE";
			sql += "\n		COMPANY_ID = ? ";

			// Build dynamic where clause
			for ( Map.Entry<String, String> entry:  dynamicQueryParameters.getQueryParameters().entrySet() )
			{
				String paramName  = entry.getKey();
				String paramValue = entry.getValue();
				if ( paramName  == null) continue;
				if ( paramValue == null) continue;
				System.out.println("parameter name is " + paramName + " Paramter value is " + paramValue );
				
				if ( DynamicQueryParameters.COUPON_TYPE_ID.equals(paramName) )
				{
					sql += "\n		AND	COUPON_TYPE_ID = ? ";
					continue;
				}
				if ( DynamicQueryParameters.FROM_PRICE.equals(paramName) )
				{
					sql += "\n		AND	COUPON_PRICE >= ? ";
					continue;
				}
				if ( DynamicQueryParameters.TO_PRICE.equals(paramName) )
				{
					sql += "\n		AND	COUPON_PRICE <= ? ";
					continue;
				}
				if ( DynamicQueryParameters.FROM_DATE.equals(paramName) )
				{
					sql += "\n		AND	COUPON_END_DATE >= ? ";
					continue;
				}
				if ( DynamicQueryParameters.TO_DATE.equals(paramName) )
				{
					sql += "\n		AND	COUPON_START_DATE <= ? ";
					continue;
				}
			}
			
			preparedStatement = connection.prepareStatement(sql);
			int i = 1;
			preparedStatement.setLong( i , companyId);
			i++;	
			// Set dynamic where clause parameters
			for ( Map.Entry<String, String> entry:  dynamicQueryParameters.getQueryParameters().entrySet() )
			{
				String paramName  = entry.getKey();
				String paramValue = entry.getValue();
				if ( paramName  == null) continue;
				if ( paramValue == null) continue;
				//System.out.println("parameter name is " + paramName + " Paramter value is " + paramValue );
				
				if ( DynamicQueryParameters.COUPON_TYPE_ID.equals(paramName) )
				{
					Integer couponTypeId = Integer.parseInt(paramValue);
					preparedStatement.setInt( i , couponTypeId );
					i++;
					//sql += " AND    COUPON_TYPE_ID = ? ";
					continue;
				}
				if ( DynamicQueryParameters.FROM_PRICE.equals(paramName) )
				{
					Double fromPrice = Double.parseDouble(paramValue);
					preparedStatement.setDouble( i , fromPrice );
					i++;
					//sql += " AND  	COUPON_PRICE >= ? ";
					continue;
				}
				if ( DynamicQueryParameters.TO_PRICE.equals(paramName) )
				{
					Double toPrice = Double.parseDouble(paramValue);
					preparedStatement.setDouble( i , toPrice );
					i++;
					//sql += " AND  	COUPON_PRICE <= ? ";
					continue;
				}
				if ( DynamicQueryParameters.FROM_DATE.equals(paramName) )
				{
					//Long 		fromDate 			= Long.parseLong(paramValue);
					//Timestamp	fromDateTimestamp	= new Timestamp(fromDate);
					Timestamp	fromDateTimestamp	= (new YyyyMmDd(paramValue)).toTimestamp();
					preparedStatement.setTimestamp( i , fromDateTimestamp );
					i++;
					//sql += " AND  	COUPON_END_DATE >= ? ";
					continue;
				}
				if ( DynamicQueryParameters.TO_DATE.equals(paramName) )
				{
					//Long 		toDate 			= Long.parseLong(paramValue);
					//Timestamp	toDateTimestamp	= new Timestamp(toDate);
					Timestamp	toDateTimestamp	= (new YyyyMmDd(paramValue)).toTimestamp();
					preparedStatement.setTimestamp( i , toDateTimestamp );
					i++;
					//sql += " AND  	COUPON_START_DATE <= ? ";
					continue;
				}
			}
			
			// execute query
			resultSet = preparedStatement.executeQuery();
	
			// Loop through result set
			// For each company create bean and add it to output list
			while ( resultSet.next() ) 
			{
				coupon = new Coupon();
				//extract bean from result Set
				this.copyDataFromResultSetToBean (coupon, resultSet);
				coupons.add(coupon);
			} 
						
			
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get coupons by companyId : " + companyId + ". " + e.getMessage());
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
		
		return coupons;
	}

	private void 			copyDataFromResultSetToBean (Coupon coupon, ResultSet resultSet) throws SQLException
	{
		coupon.setCouponId       	( resultSet.getLong      ( "COUPON_ID" ) );
		coupon.setSysCreationDate	( resultSet.getTimestamp ( "SYS_CREATION_DATE" ).getTime() );
		coupon.setSysUpdateDate  	( resultSet.getTimestamp ( "SYS_UPDATE_DATE" ).getTime() );
		coupon.setCreatedByUserId	( resultSet.getInt       ( "CREATED_BY_USER_ID" ) );
		coupon.setUpdatedByUserId	( resultSet.getInt       ( "UPDATED_BY_USER_ID" ) );
		coupon.setCompanyId      	( resultSet.getLong      ( "COMPANY_ID" ) );
		coupon.setCouponTitle    	( resultSet.getString    ( "COUPON_TITLE" ) );
		coupon.setCouponStartDate	( resultSet.getTimestamp ( "COUPON_START_DATE" ).getTime() );
		coupon.setCouponEndDate  	( resultSet.getTimestamp ( "COUPON_END_DATE" ).getTime() );
		coupon.setCouponsInStock 	( resultSet.getInt       ( "COUPONS_IN_STOCK" ) );
		coupon.setCouponTypeId   	( resultSet.getInt       ( "COUPON_TYPE_ID" ) );
		coupon.setCouponMessage  	( resultSet.getString    ( "COUPON_MESSAGE" ) );
		coupon.setCouponPrice    	( resultSet.getDouble    ( "COUPON_PRICE" ) );
		coupon.setImageFileName  	( resultSet.getString    ( "IMAGE_FILE_NAME" ) );
	}

	public void 			deleteExpiredCoupons() throws ApplicationException 
	{
		PreparedStatement 	preparedStatement	= null;
		Connection 			connection 			= null;

		try 
		{
			// Getting a connection from the connections pool or Transaction manager
			connection = super.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			String sql;
			
			// CUSTOMER_COUPON has FK to COUPONS with delete Cascade
			
			sql = "  DELETE FROM COUPONS ";
			sql += " WHERE";
			sql += "      	COUPON_END_DATE <= GETDATE()";
			
			preparedStatement = connection.prepareStatement(sql);

			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();	        
	        
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_DELETE_ERROR, e, "Failed to delete coupons due to :" + e.getMessage() );
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

	public Coupon 			getCouponForSale(long couponId) throws ApplicationException 
	{
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		Coupon 				returnObj 			= new Coupon();

		// getting the specific entry by the input id
		// storing it into resultSet
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql = "SELECT * FROM COUPONS WHERE COUPON_ID = ? AND  GETDATE() <= COUPON_END_DATE AND 1 <= COUPONS_IN_STOCK";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponId);
			
			// execute query
			resultSet = preparedStatement.executeQuery();
	
			// Not found ...
			if ( !resultSet.next() ) { return null; } 
			
			
			//extract bean from result Set
			this.copyDataFromResultSetToBean (returnObj, resultSet);			
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get coupon with id : " + couponId + " " + e.getMessage());
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

}
