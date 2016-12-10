package com.raviv.coupons.beans;

/**
 * Java bean for Customer_Coupon entity
 * @author raviv
 *
 */
public class CustomerCoupon extends InfraBean 
{
	private	long                     	internalId;
	private	long                     	customerId;
	private	long                     	couponId;
//	private	long                     	sysCreationDate;
//	private	long                     	sysUpdateDate;
//	private	int                      	createdByUserId;
//	private	int                      	updatedByUserId;
	
	private Coupon						coupon;	

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	
	/**
	 * Default constructor
	 */
	public CustomerCoupon() {
		super();
	}
	
	/**
	 * Constructor for create
	 */
	public CustomerCoupon	( long customerId, long couponId ) 
	{
		super();
		setCustomerId	( customerId	);
		setCouponId		( couponId		);
	}

	@Override
	public String toString() {
		return super.toString() + "CustomerCoupon\t[internalId=" + internalId + ", customerId=" + customerId + ", couponId=" + couponId
				+ "]\n" 
				+ this.coupon.toString();		
	}

	
	public long getInternalId() {
		return internalId;
	}

	public void setInternalId(long internalId) {
		this.internalId = internalId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
	
	
	
}