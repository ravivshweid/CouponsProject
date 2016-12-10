package com.raviv.coupons.tests.dao;


import com.raviv.coupons.beans.Coupon;
import com.raviv.coupons.dao.CouponsDao;
import com.raviv.coupons.exceptions.ApplicationException;

public class CouponsDaoGetAndUpdateTest {

	public static void main(String[] args) throws ApplicationException 
	{
		Coupon coupon = new Coupon();
		CouponsDao couponsDao = new CouponsDao();

		coupon = couponsDao.getCoupon(1);
		System.out.println(coupon);

		coupon.setCouponTitle("new title 122");	
		coupon.setCouponsInStock(97);	
		couponsDao.updateCoupon(coupon);

		coupon = couponsDao.getCoupon(1);
		System.out.println(coupon);	
	}

}
