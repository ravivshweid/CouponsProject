package com.raviv.coupons.dao.interfaces;

import com.raviv.coupons.beans.Coupon;
import com.raviv.coupons.exceptions.ApplicationException;

public interface ICouponsDao {

	public void 	createCoupon(Coupon coupon  ) throws ApplicationException;

	public Coupon 	getCoupon	(long 	couponId) throws ApplicationException;

	public void 	updateCoupon(Coupon coupon  ) throws ApplicationException;

	public void 	deleteCoupon(long	couponId) throws ApplicationException;
	
}
