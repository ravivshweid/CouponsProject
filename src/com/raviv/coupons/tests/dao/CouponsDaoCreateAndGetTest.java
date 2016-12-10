package com.raviv.coupons.tests.dao;


import com.raviv.coupons.beans.Coupon;
import com.raviv.coupons.dao.CouponsDao;
import com.raviv.coupons.enums.CouponType;
import com.raviv.coupons.exceptions.ApplicationException;

public class CouponsDaoCreateAndGetTest {

	public static void main(String[] args) throws ApplicationException {
		
		Coupon coupon = new Coupon();
		
		coupon.setCreatedByUserId(1);
		coupon.setUpdatedByUserId(1);
		coupon.setCompanyId(6);
		coupon.setCouponTitle("COUPON_TITLE");
		coupon.setCouponsInStock(100);

		CouponType couponType = CouponType.HOLIDAY;
		coupon.setCouponTypeId(couponType.getCouponType());

		coupon.setCouponMessage("COUPON_MESSAGE");
		coupon.setCouponPrice(75);
		coupon.setImageFileName("IMAGE_FILE_NAME");
		
		coupon.setCouponStartDate(0);
		coupon.setCouponEndDate(0);

		System.out.println(coupon);

		//coupon.setCouponStartDate(Timestamp.UTC(2016, 10 , 06 , 13 , 45 , 0 ));
		//coupon.setCouponEndDate  (Timestamp.UTC(2016, 10 , 23 , 13 , 45 , 0 ));

		
		/*				
		returnObj.setCouponStartDate	( resultSet.getTimestamp ( "COUPON_START_DATE" ).getTime() );
		returnObj.setCouponEndDate  	( resultSet.getTimestamp ( "COUPON_END_DATE" ).getTime() );
*/		
		

		CouponsDao couponsDao = new CouponsDao();		
		couponsDao.createCoupon(coupon);
		
		
		long couponId = coupon.getCouponId();
		coupon = couponsDao.getCoupon(couponId);
		System.out.println(coupon);
	}

}
