package com.raviv.coupons.blo;

import java.util.List;
import com.raviv.coupons.beans.Coupon;
import com.raviv.coupons.beans.Customer;
import com.raviv.coupons.beans.CustomerCoupon;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.dao.CouponsDao;
import com.raviv.coupons.dao.CustomerCouponDao;
import com.raviv.coupons.dao.interfaces.ICustomerCouponDao;
import com.raviv.coupons.dao.utils.JdbcTransactionManager;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.utils.PrintUtils;

/**
 * 
 * Customer Coupon business logic
 * 
 * 
 * @author raviv
 *
 */
public class CustomerCouponBlo {

	private	CustomerCouponDao		customerCouponDao;
	
	public CustomerCouponBlo()
	{
		this.customerCouponDao = new CustomerCouponDao();
	}
	
	public	synchronized void	buyCoupon( User loggedUser , long couponId ) throws ApplicationException 
	{
		// =====================================================
		// Verify customer profile id
		// =====================================================
		ProfileIdVerifier.verifyCustomerProfileId(loggedUser);

		//======================================================
		// Get customer details by logged user
		//======================================================	
		CustomersBlo	customersBlo	= new CustomersBlo();
		Customer		customer 		= customersBlo.getCustomer(loggedUser);
		
		// =====================================================
		// Get coupon for sale from data layer
		// Effective and in stock
		// =====================================================
		CouponsDao	couponsDao			= new CouponsDao();
		Coupon		coupon = couponsDao.getCouponForSale(couponId);
		if ( coupon == null )
		{
			throw new ApplicationException(ErrorType.COUPON_IS_NOT_FOR_SALE, null , "Coupon is not for sale couponId : " + couponId);
		}
			
		// =====================================================
		// Check if customer was buying this coupon already.
		// =====================================================
		ICustomerCouponDao		customerCouponDao;
		customerCouponDao	= new CustomerCouponDao ();
		
		CustomerCoupon	customerCoupon = customerCouponDao.getCustomerCoupon( customer.getCustomerId(), couponId );
		if ( customerCoupon != null ) 
		{
			throw new ApplicationException(ErrorType.DUPLICATE_COUPON_FOR_CUSTOMER, null
					, "CustomerBlo : buyCoupon Failed. Duplicate coupon for customer, couponId : " + couponId  );
		}
		
		// =====================================================
		// Start transaction by creating JdbcTransactionManager
		// =====================================================		
		JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager();

		// Inject transaction manager to DAO via constructors
		customerCouponDao	= new CustomerCouponDao ( jdbcTransactionManager );

		try
		{				
			// =====================================================
			// Create new customer coupon with the new coupon id
			// =====================================================
			customerCoupon = new CustomerCoupon ( customer.getCustomerId(), couponId );
			customerCoupon.setCreatedByUserId( loggedUser.getUserId() );
			customerCoupon.setUpdatedByUserId( loggedUser.getUserId() );
			// data layer action
			customerCouponDao.createCustomerCoupon(customerCoupon);

			// =====================================================
			// Update coupons stock and take one coupon away
			// =====================================================
			int  couponsInStock = coupon.getCouponsInStock();
			couponsInStock--;
			coupon.setCouponsInStock( couponsInStock );
			coupon.setUpdatedByUserId( loggedUser.getUserId() );
			// data layer action
			couponsDao.updateCoupon(coupon);
			
			// =====================================================
			// Commit transaction
			// =====================================================
			jdbcTransactionManager.commit();
					
			PrintUtils.printHeader("CustomerBlo : buyCoupon success.");
			//System.out.println(coupon);
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

	public  List<CustomerCoupon>getCustomerCoupons( User loggedUser ) throws ApplicationException 
	{
		PrintUtils.printHeader ("CustomerCouponBlo: getCustomerCoupons");
		// =====================================================
		// Verify customer profile id
		// =====================================================
		ProfileIdVerifier.verifyCustomerProfileId(loggedUser);

		//==============================================
		// Get customer details with the user id
		//==============================================	
		CustomersBlo 	customersBlo	= new CustomersBlo();
		Customer		customer 		= customersBlo.getCustomer(loggedUser);

		//==============================================
		// Get customer coupons from data layer
		//==============================================	
		long 					customerId		= customer.getCustomerId();
		List<CustomerCoupon>	customerCoupons	= this.customerCouponDao.getCustomerCouponsByCustomerId(customerId);

		for ( CustomerCoupon customerCoupon : customerCoupons )
		{
			System.out.println(customerCoupon);
		}
		return customerCoupons;
	}

	public  List<CustomerCoupon>getCustomerCouponsQuery( User loggedUser , DynamicQueryParameters dynamicQueryParameters ) throws ApplicationException 
	{
		PrintUtils.printHeader ("CustomerCouponBlo: getCustomerCouponsQuery");
		// =====================================================
		// Verify customer profile id
		// =====================================================
		ProfileIdVerifier.verifyCustomerProfileId(loggedUser);

		//======================================================
		// Get customer details with the user id
		//======================================================	
		CustomersBlo 	customersBlo	= new CustomersBlo();
		Customer		customer 		= customersBlo.getCustomer(loggedUser);

		//======================================================
		// Get customer coupons from data layer
		//======================================================
		long customerId 		= customer.getCustomerId();
		List<CustomerCoupon>	customerCoupons= customerCouponDao.getCustomerCouponsByCustomerIdAndDynamicFilter( customerId , dynamicQueryParameters);
										
		for ( CustomerCoupon customerCoupon : customerCoupons )
		{
			System.out.println(customerCoupon);
		}
		
		return customerCoupons;
	}

}
