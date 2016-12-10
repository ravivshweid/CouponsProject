package com.raviv.coupons.enums;

public enum CouponType {
	
	
	HOLIDAY(1),
	RESUTRANT(2),
	ENTERTAINMENT(3),
	TRAVELLING(4);
	
	private int couponType;
	
			
	CouponType(int couponType){
		this.couponType = couponType;
	}

	public int getCouponType() {
		return this.couponType;
	}
	
	
	
}
