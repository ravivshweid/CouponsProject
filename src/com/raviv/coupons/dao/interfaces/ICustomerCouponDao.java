package com.raviv.coupons.dao.interfaces;

import com.raviv.coupons.beans.CustomerCoupon;
import com.raviv.coupons.exceptions.ApplicationException;

public interface ICustomerCouponDao {

	public void 			createCustomerCoupon	( CustomerCoupon	customerCoupon	) throws ApplicationException;

	public CustomerCoupon	getCustomerCoupon	  	( long customerId, long couponId	) throws ApplicationException;

	public void 			deleteCustomerCoupon	( long				internalId		) throws ApplicationException;
	
}
