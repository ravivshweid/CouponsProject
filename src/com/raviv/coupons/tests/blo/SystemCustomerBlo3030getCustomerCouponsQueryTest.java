package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CustomerCouponBlo;
import com.raviv.coupons.blo.DynamicQueryParameters;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;

public class SystemCustomerBlo3030getCustomerCouponsQueryTest {

	public static void main(String[] args) throws ApplicationException {
		UsersBlo usersBlo = new UsersBlo();
		CustomerCouponBlo customerCouponBlo = new CustomerCouponBlo();
		
		/**
		 *  Login as customer
		 */		
		//User loggedUser = usersBlo.login("admin", "1234");
		//User loggedUser = usersBlo.login("comp1", "1234");
		User loggedUser = usersBlo.login("cust4", "1234");

		/**
		 * Get customer coupons with dynamicQueryParameters
		 */
		
		DynamicQueryParameters dynamicQueryParameters = new DynamicQueryParameters();

		dynamicQueryParameters.add(DynamicQueryParameters.COUPON_TYPE_ID	, "1"			);
		dynamicQueryParameters.add(DynamicQueryParameters.FROM_PRICE		, "85"			);
		dynamicQueryParameters.add(DynamicQueryParameters.TO_PRICE			, "95"			);

		
		customerCouponBlo.getCustomerCouponsQuery( loggedUser , dynamicQueryParameters) ;

	}

}
