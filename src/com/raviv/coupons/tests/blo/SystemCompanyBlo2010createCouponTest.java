package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.Company;
import com.raviv.coupons.beans.Coupon;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CouponsBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.dao.CompanysDao;
import com.raviv.coupons.enums.CouponType;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.utils.PrintUtils;
import com.raviv.coupons.utils.YyyyMmDd;

public class SystemCompanyBlo2010createCouponTest {

	public static void main(String[] args) throws ApplicationException 
	{
		UsersBlo usersBlo = new UsersBlo();
		CouponsBlo couponsBlo = new CouponsBlo();
		CompanysDao companysDao = new CompanysDao();
		
		/**
		 *  Login as company
		 */		
		User loggedUser = usersBlo.login("comp1", "1234");
		
		
		//==============================================
		// Get company details with the user id
		//==============================================		
		int userId      = loggedUser.getUserId();
		Company company = companysDao.getCompanyByUserId(userId);
		if ( company == null )
		{
			throw new ApplicationException(ErrorType.GENERAL_ERROR
					, "Failed to get company with userId : " + userId );
		}
		PrintUtils.printHeader("Company deatils : ");		
		System.out.println(company);					
		long companyId = company.getCompanyId();


		//==============================================
		// Create 1st coupon
		//==============================================		
		Coupon 	coupon;
		coupon	= new Coupon (	  companyId
								,"coupon1 Title"
								, new YyyyMmDd ("20160101")
								, new YyyyMmDd ("20160601")
								, 100
								, CouponType.ENTERTAINMENT.getCouponType()
								, "coupon1 message"						
								, 70 
								, "1 imageFileName"	
							 ) ;
		
		couponsBlo.createCoupon(loggedUser,coupon);

		//==============================================
		// Create 2nd coupon
		//==============================================		
		coupon	= new Coupon (	companyId
								,"coupon2 Title"
								, new YyyyMmDd ("20160602")
								, new YyyyMmDd ("20161231")
								, 100
								, CouponType.HOLIDAY.getCouponType()
								, "coupon2 message"						
								, 80 
								, "2 imageFileName"	
							 ) ;
		couponsBlo.createCoupon(loggedUser,coupon);

		
		//==============================================
		// Create 3rd coupon
		//==============================================		
		coupon	= new Coupon (	companyId
								,"coupon3 Title"
								, new YyyyMmDd ("20170602")
								, new YyyyMmDd ("20171231")
								, 100
								, CouponType.HOLIDAY.getCouponType()
								, "coupon3 message"						
								, 90 
								, "3 imageFileName"	
							 ) ;
		couponsBlo.createCoupon(loggedUser,coupon);
		
	}

}
