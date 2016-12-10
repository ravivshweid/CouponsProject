package com.raviv.coupons.enums;

public enum ErrorType {
	
	 CUSTOMER_NAME_ALREADY_EXISTS	( 1)
	,COUPON_NAME_ALREADY_EXISTS		( 2)
	,INVALID_EMAIL					( 3)
	,GENERAL_ERROR					( 4)
	
	,DAO_CREATE_ERROR				( 5)
	,DAO_GET_ERROR					( 6)
	,DAO_UPDATE_ERROR				( 7)
	,DAO_DELETE_ERROR				( 8)

	,LOGIN_ERROR					( 9)
	,COUPON_OUT_OF_STOCK_ERROR		(10)
	,DUPLICATE_COUPON_FOR_CUSTOMER	(11)
	,INVALID_USER_PROFILE_ID		(12)
	,BLO_GET_ERROR					(13)
	,COUPON_IS_NOT_FOR_SALE			(14)
	
	;
	
	private int internalErrorCode;
	
	ErrorType(int internalErrorCode){
		this.internalErrorCode = internalErrorCode;
	}
	
	public int getInternalErrorCode() {
		return internalErrorCode;
	}
	
}	
