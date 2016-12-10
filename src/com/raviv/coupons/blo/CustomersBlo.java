package com.raviv.coupons.blo;

import java.util.List;

import com.raviv.coupons.beans.Customer;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.dao.CustomersDao;
import com.raviv.coupons.dao.UsersDao;
import com.raviv.coupons.dao.interfaces.ICustomersDao;
import com.raviv.coupons.dao.interfaces.IUsersDao;
import com.raviv.coupons.dao.utils.JdbcTransactionManager;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.enums.UserProfileType;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.utils.PrintUtils;

/**
 * 
 * Customers business logic
 * 
 * @author raviv
 *
 */
public class CustomersBlo {

	private CustomersDao			customersDao;	
	
	public CustomersBlo() throws ApplicationException
	{
		this.customersDao				= new CustomersDao();
	}
	
	public  void 			createCustomer( User loggedUser , User user, Customer customer) throws ApplicationException 
	{
		// =====================================================
		// Verify admin profile id
		// =====================================================
		ProfileIdVerifier.verifyAdminProfileId(loggedUser);

		// =====================================================
		// Start transaction by creating JdbcTransactionManager
		// =====================================================		
		JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager();
		// Inject transaction manager to DAO via constructors
		IUsersDao 		usersDao 		= new UsersDao   	( jdbcTransactionManager );
		ICustomersDao	customersDao	= new CustomersDao	( jdbcTransactionManager );

		try
		{
			// =====================================================
			// Create new user for the new customer
			// =====================================================
			// Prepare inputs
			user.setCreatedByUserId( loggedUser.getUserId() );
			user.setUpdatedByUserId( loggedUser.getUserId() );
			UserProfileType customerUserProfileType = UserProfileType.CUSTOMER;
			user.setUserProfileId( customerUserProfileType.getUserProfileId() );
			// Create the user
			usersDao.createUser(user);
		
			// =====================================================
			// Create new customer with the new user
			// =====================================================
			// Prepare inputs
			int newUserId = user.getUserId();
			customer.setUserId(newUserId);
			customer.setCreatedByUserId( loggedUser.getUserId() );
			customer.setUpdatedByUserId( loggedUser.getUserId() );
			// Create the customer
			customersDao.createCustomer(customer);
			

			// =====================================================
			// Commit transaction
			// =====================================================
			jdbcTransactionManager.commit();
			PrintUtils.printHeader("createCustomer created customer");
			System.out.println(user);
			System.out.println(customer);
			
		}
		catch (ApplicationException e)
		{
			// =====================================================
			// Rollback transaction
			// =====================================================
			jdbcTransactionManager.rollback();
			throw (e); 
		}
		finally
		{
			jdbcTransactionManager.closeConnection();
		}	
	}// createCustomer

	public  void 			deleteCustomer( User loggedUser , long customerId) throws ApplicationException 
	{
		// =====================================================
		// Verify admin profile id
		// =====================================================
		ProfileIdVerifier.verifyAdminProfileId(loggedUser);

		// =====================================================
		// Start transaction by creating JdbcTransactionManager
		// =====================================================		
		JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager();

		// Inject transaction manager to DAO via constructor
		ICustomersDao customersDao	= new CustomersDao( jdbcTransactionManager );
		
		try
		{
			// =====================================================
			// Delete customer and related customer coupons
			// CUSTOMER_COUPON has FK to CUSTOMERS using customer id, with delete Cascade
			// =====================================================			
			customersDao.deleteCustomer(customerId);
			
			// =====================================================
			// Commit transaction
			// =====================================================
			jdbcTransactionManager.commit();
			PrintUtils.printHeader("deleteCustomer deleted customerId : " + customerId );
			
		}
		catch (ApplicationException e)
		{
			// =====================================================
			// Rollback transaction
			// =====================================================

			jdbcTransactionManager.rollback();
			
			throw (e); 
			
		}
		finally
		{
			jdbcTransactionManager.closeConnection();
		}	
				
	}

	public  void 			updateCustomer( User loggedUser , Customer inputCustomer) throws ApplicationException 
	{
		// =====================================================
		// Verify admin profile id
		// =====================================================
		ProfileIdVerifier.verifyAdminProfileId(loggedUser);

		// =====================================================
		// Start transaction by creating JdbcTransactionManager
		// =====================================================		
		JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager();

		// Inject transaction manager to DAO via constructors
		ICustomersDao customersDao	= new CustomersDao( jdbcTransactionManager );

		// =====================================================
		// Get customer details from DB
		// =====================================================
		Customer customer;		
		customer = customersDao.getCustomer( inputCustomer.getCustomerId() );
		
		try
		{			
			// =====================================================
			// Update customer name
			// =====================================================
			
			// Prepare inputs
			customer.setUpdatedByUserId( loggedUser.getUserId() );
			
			customer.setCustomerName( inputCustomer.getCustomerName() );

			
			// Update the customer
			customersDao.updateCustomer(customer);

			// =====================================================
			// Commit transaction
			// =====================================================

			jdbcTransactionManager.commit();
			
			PrintUtils.printHeader("updateCustomer updated customer");
			System.out.println(customer);
			
		}
		catch (ApplicationException e)
		{
			// =====================================================
			// Rollback transaction
			// =====================================================

			jdbcTransactionManager.rollback();
			
			throw (e); 
			
		}
		finally
		{
			jdbcTransactionManager.closeConnection();
		}	
	}

	public  List<Customer>	getAllCustomers( User loggedUser ) throws ApplicationException 
	{		
		// =====================================================
		// Verify admin profile id
		// =====================================================
		ProfileIdVerifier.verifyAdminProfileId(loggedUser);
		
		// =====================================================
		// Get all customers from data layer
		// =====================================================
		List<Customer> customersList = customersDao.getAllCustomers();
		
		for ( Customer customer : customersList )
		{
			System.out.println(customer);
		}
		
		return customersList;
	}

	public  Customer 		getCustomer( User loggedUser , long customerId) throws ApplicationException 
	{		
		// =====================================================
		// Verify admin profile id
		// =====================================================
		ProfileIdVerifier.verifyAdminProfileId(loggedUser);
		
		// =====================================================
		// Get customer from data layer
		// =====================================================
		Customer customer = customersDao.getCustomer(customerId);		
		if ( customer == null )
		{
			throw new ApplicationException(ErrorType.GENERAL_ERROR, null, "Customer not found, customerId : " + customerId );			
		}
		
		System.out.println(customer);
		
		return customer;
	}

	public  Customer 		getCustomer( User loggedUser) throws ApplicationException 
	{		
		// =====================================================
		// Verify customer profile id
		// =====================================================
		ProfileIdVerifier.verifyCustomerProfileId(loggedUser);
		
		//==============================================
		// Get customer details with the user id
		//==============================================		
		int userId      = loggedUser.getUserId();
		Customer customer = customersDao.getCustomerByUserId(userId);
		if ( customer == null )
		{
			throw new ApplicationException(ErrorType.BLO_GET_ERROR
					, "Failed to get customer with userId : " + userId );
		}
		System.out.println(customer);		
		return customer;
	}

}
