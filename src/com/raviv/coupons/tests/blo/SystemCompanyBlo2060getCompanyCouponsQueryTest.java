package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CouponsBlo;
import com.raviv.coupons.blo.DynamicQueryParameters;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;

public class SystemCompanyBlo2060getCompanyCouponsQueryTest {

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
		 * Get company coupons with dynamicQueryParameters
		 */		
		DynamicQueryParameters dynamicQueryParameters = new DynamicQueryParameters();

		//dynamicQueryParameters.add(DynamicQueryParameters.COUPON_TYPE_ID	, "3"			);
		dynamicQueryParameters.add(DynamicQueryParameters.FROM_PRICE		, "65"			);
		dynamicQueryParameters.add(DynamicQueryParameters.TO_PRICE			, "95"			);
		//dynamicQueryParameters.add(DynamicQueryParameters.FROM_DATE			, "20160602"	);
		dynamicQueryParameters.add(DynamicQueryParameters.TO_DATE  			, "20170601"	);
		
		couponsBlo.getCompanyCouponsQuery( loggedUser , dynamicQueryParameters );
		
	}

}
