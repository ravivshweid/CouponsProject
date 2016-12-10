package com.raviv.coupons.blo;


import java.util.List;

import com.raviv.coupons.beans.Company;
import com.raviv.coupons.beans.Coupon;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.dao.CouponsDao;
import com.raviv.coupons.dao.interfaces.ICouponsDao;
import com.raviv.coupons.dao.utils.JdbcTransactionManager;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.utils.PrintUtils;

/**
 * 
 * Coupons business logic
 * 
 * @author raviv
 *
 */
public class CouponsBlo {
	
	public CouponsBlo()
	{
	}
	
	public  void 			createCoupon( User loggedUser , Coupon coupon ) throws ApplicationException 
	{
		// =====================================================
		// Verify company profile id
		// =====================================================
		ProfileIdVerifier.verifyCompanyProfileId(loggedUser);

		// =====================================================
		// Start transaction by creating JdbcTransactionManager
		// =====================================================		
		JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager();
		// Inject transaction manager to DAO via constructors
		ICouponsDao		couponsDao	= new CouponsDao ( jdbcTransactionManager );

		try
		{				
			// =====================================================
			// Create new coupon
			// =====================================================
			coupon.setCreatedByUserId( loggedUser.getUserId() );
			coupon.setUpdatedByUserId( loggedUser.getUserId() );
			couponsDao.createCoupon(coupon);
			
			// =====================================================
			// Commit transaction
			// =====================================================
			jdbcTransactionManager.commit();
			PrintUtils.printHeader("CouponsBlo : createCoupon created coupon");
			System.out.println(coupon);
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

	public  void 			deleteCoupon( User loggedUser , long couponId) throws ApplicationException 
	{		
		// =====================================================
		// Verify company profile id
		// =====================================================
		ProfileIdVerifier.verifyCompanyProfileId(loggedUser);
		
		// =====================================================
		// Start transaction by creating JdbcTransactionManager
		// =====================================================		
		JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager();
		// Inject transaction manager to DAO via constructors
		ICouponsDao couponsDao	= new CouponsDao( jdbcTransactionManager );
		
		try
		{
			// =====================================================
			// Delete coupon and related customer coupons
			// CUSTOMER_COUPON has FK to COUPONS using coupon id, with delete Cascade
			// =====================================================			
			couponsDao.deleteCoupon(couponId);
			
			// =====================================================
			// Commit transaction
			// =====================================================
			jdbcTransactionManager.commit();
			PrintUtils.printHeader("CouponsBlo : deleteCoupon deleted couponId : " + couponId );
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

	public  void 			updateCoupon( User loggedUser , Coupon inputCoupon) throws ApplicationException 
	{
		// =====================================================
		// Verify company profile id
		// =====================================================
		ProfileIdVerifier.verifyCompanyProfileId(loggedUser);

		// =====================================================
		// Start transaction by creating JdbcTransactionManager
		// =====================================================		
		JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager();

		// Inject transaction manager to DAO via constructors
		ICouponsDao couponsDao	= new CouponsDao( jdbcTransactionManager );
		
		try
		{
			// =====================================================
			// Get coupon from data layer
			// =====================================================			
			Coupon coupon;
			long couponId = inputCoupon.getCouponId();
			coupon = couponsDao.getCoupon( couponId );
			
			if ( coupon == null )
			{
				throw new ApplicationException(ErrorType.BLO_GET_ERROR, "Failed to get coupon with id : " + couponId + " " + "" );
			}

			// =====================================================
			// Set coupon: end date , price
			// =====================================================
			
			// Prepare inputs
			coupon.setUpdatedByUserId( loggedUser.getUserId()    );
			
			coupon.setCouponEndDate  ( inputCoupon.getCouponEndDate() );
			coupon.setCouponPrice    ( inputCoupon.getCouponPrice()   );

			
			// =====================================================
			// Update coupon in data layer
			// =====================================================			
			couponsDao.updateCoupon(coupon);

			// =====================================================
			// Commit transaction
			// =====================================================

			jdbcTransactionManager.commit();
			
			
			PrintUtils.printHeader("updateCoupon updated coupon");
			System.out.println(coupon);
			
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

	public  List<Coupon>	getCompanyCoupons( User loggedUser ) throws ApplicationException 
	{
		PrintUtils.printHeader ("CouponsBlo: getCompanyCoupons");
		// =====================================================
		// Verify company profile id
		// =====================================================
		ProfileIdVerifier.verifyCompanyProfileId(loggedUser);
		
		// =====================================================
		// Get company by user id
		// =====================================================		
		CompanysBlo companysBlo = new CompanysBlo();
		Company 	company 	= companysBlo.getCompany( loggedUser );
		long 		companyId 	= company.getCompanyId();
		
		// =====================================================
		// Get company coupons from data layer
		// =====================================================				
		CouponsDao couponsDao = new CouponsDao();
		List<Coupon> couponsList 	= couponsDao.getCouponsByCompanyId(companyId);
		
		for ( Coupon coupon : couponsList )
		{
			System.out.println(coupon);
		}

		return couponsList;
	}

	public  List<Coupon>	getCompanyCouponsQuery( User loggedUser , DynamicQueryParameters dynamicQueryParameters) throws ApplicationException 
	{
		PrintUtils.printHeader ("CouponsBlo: getCompanyCouponsQuery");
		// =====================================================
		// Verify company profile id
		// =====================================================
		ProfileIdVerifier.verifyCompanyProfileId(loggedUser);

		// =====================================================
		// Get company by user id
		// =====================================================		
		CompanysBlo companysBlo = new CompanysBlo();
		Company 	company 	= companysBlo.getCompany( loggedUser );
		long 		companyId 	= company.getCompanyId();

		// =====================================================
		// Get company coupons from data layer
		// =====================================================				
		CouponsDao couponsDao = new CouponsDao();
		List<Coupon> couponsList 	= couponsDao.getCouponsByCompanyIdAndDynamicFilter( companyId , dynamicQueryParameters);
		for ( Coupon coupon : couponsList )
		{
			System.out.println(coupon);
		}
		return couponsList;
	}

}
