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
import com.raviv.coupons.beans.CustomerCoupon;
import com.raviv.coupons.blo.DynamicQueryParameters;
import com.raviv.coupons.dao.interfaces.ICustomerCouponDao;
import com.raviv.coupons.dao.utils.JdbcTransactionManager;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.utils.YyyyMmDd;

public class CustomerCouponDao extends InfraDao implements ICustomerCouponDao {

	/**
	 * Default constructor
	 */
	public 						CustomerCouponDao() 
	{
		super();
	}
	
	/**
	 * Constructor with transaction manager
	 */
	public 						CustomerCouponDao(JdbcTransactionManager jdbcTransactionManager) {
		super(jdbcTransactionManager);
	}

	@Override
	public void 				createCustomerCoupon(CustomerCoupon customerCoupon) throws ApplicationException {
	
		PreparedStatement 	preparedStatement	= null;
		Connection 			connection 			= null;
		ResultSet 			generatedKeys		= null;


		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			String sql;
			
			sql = "INSERT INTO CUSTOMER_COUPON (";
			sql += "	 CUSTOMER_ID";
			sql += "	,COUPON_ID";
			sql += "	,CREATED_BY_USER_ID";
			sql += "	,UPDATED_BY_USER_ID";
			sql += ")";
			sql += "VALUES (?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			// Replacing question marks with the values inside  the bean.
			preparedStatement.setLong	(1 ,	customerCoupon.getCustomerId() );
			preparedStatement.setLong	(2 ,	customerCoupon.getCouponId() );
			preparedStatement.setInt	(3 ,	customerCoupon.getCreatedByUserId() );
			preparedStatement.setInt	(4 ,	customerCoupon.getUpdatedByUserId() );

			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();

			// Handle generated keys...
			generatedKeys = preparedStatement.getGeneratedKeys();
	        if ( generatedKeys.next() ) 
	        {
	        	int internalId = generatedKeys.getInt("GENERATED_KEYS");
	        	customerCoupon.setInternalId(internalId);
	        }
	        
	        
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_CREATE_ERROR, e, "Failed to create cutomer coupon due to :" + e.getMessage() );
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
	public CustomerCoupon 		getCustomerCoupon(long customerId, long couponId) throws ApplicationException 
	{
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		CustomerCoupon 		returnObj 			= new CustomerCoupon();

		// getting the specific entry by the input id
		// storing it into resultSet
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql;
			
			sql = "";
			sql += "\n SELECT ";
			
			sql += "\n	 CC.INTERNAL_ID			AS CC_INTERNAL_ID";
			sql += "\n	,CC.CUSTOMER_ID			AS CC_CUSTOMER_ID";
			sql += "\n	,CC.COUPON_ID			AS CC_COUPON_ID";
			sql += "\n	,CC.SYS_CREATION_DATE	AS CC_SYS_CREATION_DATE";
			sql += "\n	,CC.SYS_UPDATE_DATE		AS CC_SYS_UPDATE_DATE";
			sql += "\n	,CC.CREATED_BY_USER_ID	AS CC_CREATED_BY_USER_ID";
			sql += "\n	,CC.UPDATED_BY_USER_ID	AS CC_UPDATED_BY_USER_ID";
			
			sql += "\n	,C.COUPON_ID			AS C_COUPON_ID";
			sql += "\n	,C.SYS_CREATION_DATE	AS C_SYS_CREATION_DATE";
			sql += "\n	,C.SYS_UPDATE_DATE		AS C_SYS_UPDATE_DATE";
			sql += "\n	,C.CREATED_BY_USER_ID	AS C_CREATED_BY_USER_ID";
			sql += "\n	,C.UPDATED_BY_USER_ID	AS C_UPDATED_BY_USER_ID";
			sql += "\n	,C.COMPANY_ID			AS C_COMPANY_ID";
			sql += "\n	,C.COUPON_TITLE			AS C_COUPON_TITLE";
			sql += "\n	,C.COUPON_START_DATE	AS C_COUPON_START_DATE";
			sql += "\n	,C.COUPON_END_DATE		AS C_COUPON_END_DATE";
			sql += "\n	,C.COUPONS_IN_STOCK		AS C_COUPONS_IN_STOCK";
			sql += "\n	,C.COUPON_TYPE_ID		AS C_COUPON_TYPE_ID";
			sql += "\n	,C.COUPON_MESSAGE		AS C_COUPON_MESSAGE";
			sql += "\n	,C.COUPON_PRICE			AS C_COUPON_PRICE";
			sql += "\n	,C.IMAGE_FILE_NAME		AS C_IMAGE_FILE_NAME";
			
			sql += "\n FROM ";
			sql += "\n 		CUSTOMER_COUPON	CC";
			sql += "\n 		INNER JOIN ";
			sql += "\n 		COUPONS			C	ON CC.COUPON_ID = C.COUPON_ID";
			sql += "\n WHERE ";
			sql += "\n 		CC.CUSTOMER_ID = ? AND CC.COUPON_ID = ?";
			
			//System.out.println(sql);
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId );
			preparedStatement.setLong(2, couponId   );
			
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
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get customer coupon with customerId : " + customerId + " and  couponId : " + couponId + "\n" + e.getMessage());
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

	public List<CustomerCoupon>	getCustomerCouponsByCustomerId(long customerId) throws ApplicationException 
	{
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		List<CustomerCoupon>customerCoupons		= new ArrayList<CustomerCoupon>();
		CustomerCoupon 		customerCoupon 		;

		// getting the specific entry by the input id
		// storing it into resultSet
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql;
			
			sql = "";
			sql += "\n SELECT ";
			
			sql += "\n	 CC.INTERNAL_ID			AS CC_INTERNAL_ID";
			sql += "\n	,CC.CUSTOMER_ID			AS CC_CUSTOMER_ID";
			sql += "\n	,CC.COUPON_ID			AS CC_COUPON_ID";
			sql += "\n	,CC.SYS_CREATION_DATE	AS CC_SYS_CREATION_DATE";
			sql += "\n	,CC.SYS_UPDATE_DATE		AS CC_SYS_UPDATE_DATE";
			sql += "\n	,CC.CREATED_BY_USER_ID	AS CC_CREATED_BY_USER_ID";
			sql += "\n	,CC.UPDATED_BY_USER_ID	AS CC_UPDATED_BY_USER_ID";
			
			sql += "\n	,C.COUPON_ID			AS C_COUPON_ID";
			sql += "\n	,C.SYS_CREATION_DATE	AS C_SYS_CREATION_DATE";
			sql += "\n	,C.SYS_UPDATE_DATE		AS C_SYS_UPDATE_DATE";
			sql += "\n	,C.CREATED_BY_USER_ID	AS C_CREATED_BY_USER_ID";
			sql += "\n	,C.UPDATED_BY_USER_ID	AS C_UPDATED_BY_USER_ID";
			sql += "\n	,C.COMPANY_ID			AS C_COMPANY_ID";
			sql += "\n	,C.COUPON_TITLE			AS C_COUPON_TITLE";
			sql += "\n	,C.COUPON_START_DATE	AS C_COUPON_START_DATE";
			sql += "\n	,C.COUPON_END_DATE		AS C_COUPON_END_DATE";
			sql += "\n	,C.COUPONS_IN_STOCK		AS C_COUPONS_IN_STOCK";
			sql += "\n	,C.COUPON_TYPE_ID		AS C_COUPON_TYPE_ID";
			sql += "\n	,C.COUPON_MESSAGE		AS C_COUPON_MESSAGE";
			sql += "\n	,C.COUPON_PRICE			AS C_COUPON_PRICE";
			sql += "\n	,C.IMAGE_FILE_NAME		AS C_IMAGE_FILE_NAME";
			
			sql += "\n FROM ";
			sql += "\n 		CUSTOMER_COUPON	CC";
			sql += "\n 		INNER JOIN ";
			sql += "\n 		COUPONS			C	ON CC.COUPON_ID = C.COUPON_ID";
			sql += "\n WHERE ";
			sql += "\n 		CC.CUSTOMER_ID = ?";
			
			//System.out.println(sql);
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId );
			
			// execute query
			resultSet = preparedStatement.executeQuery();
	
			// Loop through result set
			// For each company create bean and add it to output list
			while ( resultSet.next() ) 
			{
				customerCoupon = new CustomerCoupon();
				//extract bean from result Set
				this.copyDataFromResultSetToBean (customerCoupon, resultSet);
				customerCoupons.add(customerCoupon);
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get customer coupons with customerId : " + customerId + "\n" + e.getMessage());
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
		
		return customerCoupons;
}

	public List<CustomerCoupon>	getCustomerCouponsByCustomerIdAndDynamicFilter(long customerId, DynamicQueryParameters dynamicQueryParameters) throws ApplicationException 
	{
		Connection 			connection 			= null;
		PreparedStatement 	preparedStatement 	= null;		
		ResultSet 			resultSet 			= null;
		List<CustomerCoupon>customerCoupons		= new ArrayList<CustomerCoupon>();
		CustomerCoupon 		customerCoupon 		;

		// getting the specific entry by the input id
		// storing it into resultSet
		try 
		{
			// Getting a connection from the connections manager or Transaction manager
			connection = super.getConnection();
			
			String sql;
			
			sql = "";
			sql += "\n SELECT ";
			
			sql += "\n	 CC.INTERNAL_ID			AS CC_INTERNAL_ID";
			sql += "\n	,CC.CUSTOMER_ID			AS CC_CUSTOMER_ID";
			sql += "\n	,CC.COUPON_ID			AS CC_COUPON_ID";
			sql += "\n	,CC.SYS_CREATION_DATE	AS CC_SYS_CREATION_DATE";
			sql += "\n	,CC.SYS_UPDATE_DATE		AS CC_SYS_UPDATE_DATE";
			sql += "\n	,CC.CREATED_BY_USER_ID	AS CC_CREATED_BY_USER_ID";
			sql += "\n	,CC.UPDATED_BY_USER_ID	AS CC_UPDATED_BY_USER_ID";
			
			sql += "\n	,C.COUPON_ID			AS C_COUPON_ID";
			sql += "\n	,C.SYS_CREATION_DATE	AS C_SYS_CREATION_DATE";
			sql += "\n	,C.SYS_UPDATE_DATE		AS C_SYS_UPDATE_DATE";
			sql += "\n	,C.CREATED_BY_USER_ID	AS C_CREATED_BY_USER_ID";
			sql += "\n	,C.UPDATED_BY_USER_ID	AS C_UPDATED_BY_USER_ID";
			sql += "\n	,C.COMPANY_ID			AS C_COMPANY_ID";
			sql += "\n	,C.COUPON_TITLE			AS C_COUPON_TITLE";
			sql += "\n	,C.COUPON_START_DATE	AS C_COUPON_START_DATE";
			sql += "\n	,C.COUPON_END_DATE		AS C_COUPON_END_DATE";
			sql += "\n	,C.COUPONS_IN_STOCK		AS C_COUPONS_IN_STOCK";
			sql += "\n	,C.COUPON_TYPE_ID		AS C_COUPON_TYPE_ID";
			sql += "\n	,C.COUPON_MESSAGE		AS C_COUPON_MESSAGE";
			sql += "\n	,C.COUPON_PRICE			AS C_COUPON_PRICE";
			sql += "\n	,C.IMAGE_FILE_NAME		AS C_IMAGE_FILE_NAME";
			
			sql += "\n FROM ";
			sql += "\n 		CUSTOMER_COUPON	CC";
			sql += "\n 		INNER JOIN ";
			sql += "\n 		COUPONS			C	ON CC.COUPON_ID = C.COUPON_ID";
			sql += "\n WHERE ";
			sql += "\n 		CC.CUSTOMER_ID = ?";
			
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
					sql += "\n		AND	C.COUPON_TYPE_ID = ? ";
					continue;
				}
				if ( DynamicQueryParameters.FROM_PRICE.equals(paramName) )
				{
					sql += "\n		AND	C.COUPON_PRICE >= ? ";
					continue;
				}
				if ( DynamicQueryParameters.TO_PRICE.equals(paramName) )
				{
					sql += "\n		AND	C.COUPON_PRICE <= ? ";
					continue;
				}
			}
			//System.out.println(sql);
			
			preparedStatement = connection.prepareStatement(sql);

			int i = 1;			
			preparedStatement.setLong( i , customerId );
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
			}
			
			// execute query
			resultSet = preparedStatement.executeQuery();
	
			// Loop through result set
			// For each company create bean and add it to output list
			while ( resultSet.next() ) 
			{
				customerCoupon = new CustomerCoupon();
				//extract bean from result Set
				this.copyDataFromResultSetToBean (customerCoupon, resultSet);
				customerCoupons.add(customerCoupon);
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get customer coupons with customerId : " + customerId + "\n" + e.getMessage());
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
		
		return customerCoupons;
}

	@Override
	public void 				deleteCustomerCoupon(long internalId) {
		// TODO Auto-generated method stub
		
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
				//this.copyDataFromResultSetToBean (coupon, resultSet);
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

	private void 			copyDataFromResultSetToBean (CustomerCoupon customerCoupon, ResultSet resultSet) throws SQLException
	{
		
		Coupon coupon = new Coupon();
				
		coupon.setCouponId       	( resultSet.getLong      ( "C_COUPON_ID" ) );
		coupon.setSysCreationDate	( resultSet.getTimestamp ( "C_SYS_CREATION_DATE" ).getTime() );
		coupon.setSysUpdateDate  	( resultSet.getTimestamp ( "C_SYS_UPDATE_DATE" ).getTime() );
		coupon.setCreatedByUserId	( resultSet.getInt       ( "C_CREATED_BY_USER_ID" ) );
		coupon.setUpdatedByUserId	( resultSet.getInt       ( "C_UPDATED_BY_USER_ID" ) );
		coupon.setCompanyId      	( resultSet.getLong      ( "C_COMPANY_ID" ) );
		coupon.setCouponTitle    	( resultSet.getString    ( "C_COUPON_TITLE" ) );
		coupon.setCouponStartDate	( resultSet.getTimestamp ( "C_COUPON_START_DATE" ).getTime() );
		coupon.setCouponEndDate  	( resultSet.getTimestamp ( "C_COUPON_END_DATE" ).getTime() );
		coupon.setCouponsInStock 	( resultSet.getInt       ( "C_COUPONS_IN_STOCK" 	) );
		coupon.setCouponTypeId   	( resultSet.getInt       ( "C_COUPON_TYPE_ID" 	) );
		coupon.setCouponMessage  	( resultSet.getString    ( "C_COUPON_MESSAGE" 	) );
		coupon.setCouponPrice    	( resultSet.getDouble    ( "C_COUPON_PRICE" 		) );
		coupon.setImageFileName  	( resultSet.getString    ( "C_IMAGE_FILE_NAME"	) );
		
		customerCoupon.setCoupon(coupon);
		
		customerCoupon.setInternalId       	( resultSet.getLong      ( "CC_INTERNAL_ID" ) );
		customerCoupon.setCustomerId       	( resultSet.getLong      ( "CC_CUSTOMER_ID" ) );
		customerCoupon.setCouponId       	( resultSet.getLong      ( "CC_COUPON_ID" ) );
		customerCoupon.setSysCreationDate	( resultSet.getTimestamp ( "CC_SYS_CREATION_DATE" ).getTime() );
		customerCoupon.setSysUpdateDate  	( resultSet.getTimestamp ( "CC_SYS_UPDATE_DATE" ).getTime() );
		customerCoupon.setCreatedByUserId	( resultSet.getInt       ( "CC_CREATED_BY_USER_ID" ) );
		customerCoupon.setUpdatedByUserId	( resultSet.getInt       ( "CC_UPDATED_BY_USER_ID" ) );
		

	}

}
