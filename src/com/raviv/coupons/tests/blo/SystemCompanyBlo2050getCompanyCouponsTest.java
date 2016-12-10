package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CouponsBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;

public class SystemCompanyBlo2050getCompanyCouponsTest {

	public static void main(String[] args) throws ApplicationException 
	{
		UsersBlo usersBlo = new UsersBlo();
		CouponsBlo couponsBlo = new CouponsBlo();
		
		/**
		 *  Login as company
		 */		
		//User loggedUser = usersBlo.login("admin", "1234");
		User loggedUser = usersBlo.login("comp1", "1234");
		//User loggedUser = usersBlo.login("cust2", "1234");

		/**
		 *  Get company coupons
		 */		
		couponsBlo.getCompanyCoupons( loggedUser );				
	}

}