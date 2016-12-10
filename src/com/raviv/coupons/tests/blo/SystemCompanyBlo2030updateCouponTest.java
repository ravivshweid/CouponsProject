package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.Coupon;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CouponsBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.utils.YyyyMmDd;

public class SystemCompanyBlo2030updateCouponTest {

	public static void main(String[] args) throws ApplicationException 
	{
		UsersBlo usersBlo = new UsersBlo();
		CouponsBlo couponsBlo = new CouponsBlo();
		
		/**
		 * Login as company
		 */		
		User loggedUser = usersBlo.login("comp1", "1234");	
		
		/**
		 * Update coupon
		 */
		couponsBlo.updateCoupon( loggedUser, new Coupon ( 11 , new YyyyMmDd("20171230") , 91)   );

	}

}
