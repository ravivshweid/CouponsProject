package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CouponsBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;


public class SystemCompanyBlo2020deleteCouponTest {

	public static void main(String[] args) throws ApplicationException 
	{
		UsersBlo usersBlo = new UsersBlo();
		CouponsBlo couponsBlo = new CouponsBlo();
		
		/**
		 *  Login as company
		 */		
		User loggedUser = usersBlo.login("comp1", "1234");			

		/**
		 *  Delete coupon
		 */
		for ( int i = 1 ; i <= 100 ; i++)
		{
			couponsBlo.deleteCoupon( loggedUser ,  i );
		}
				
		
	}

}
