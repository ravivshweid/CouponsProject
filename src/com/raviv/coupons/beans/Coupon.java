package com.raviv.coupons.beans;

import java.sql.Timestamp;

import com.raviv.coupons.utils.YyyyMmDd;

/**
 * Java bean for Coupons entity
 * @author raviv
 *
 */
public class Coupon extends InfraBean {


	private	long                     	couponId;
//	private	long                     	sysCreationDate;
//	private	long                     	sysUpdateDate;
//	private	int                      	createdByUserId;
//	private	int                      	updatedByUserId;
	private	long                     	companyId;
	private	String                   	couponTitle;
	private	long                     	couponStartDate;
	private	long                     	couponEndDate;
	private	int                      	couponsInStock;
	private	int                      	couponTypeId;
	private	String                   	couponMessage;
	private	double                   	couponPrice;
	private	String                   	imageFileName;
	
	
	private Timestamp   				couponStartDateTimeStamp;
	private Timestamp   				couponEndDateTimeStamp;
	
	
	/**
	 * Default constructor
	 */
	public Coupon() {
		super();
	}


	/**
	 * Constructor for update 
	 */
	public Coupon( long couponId, YyyyMmDd couponEndDate, double couponPrice ) 
	{
		super();
		setCouponId			( couponId					);		
		setCouponEndDate	( couponEndDate.toLong() 	);
		setCouponPrice		( couponPrice				);
	}

	/**
	 * Constructor for create
	 */
	public Coupon	( 
						  String 	couponTitle
						, YyyyMmDd	couponStartDate
						, YyyyMmDd 	couponEndDate
						, int       couponsInStock
						, int       couponTypeId
						, String    couponMessage						
						, double 	couponPrice 
						, String    imageFileName						
			) 
	{
		super();
		setCouponTitle		( couponTitle				);
		setCouponStartDate	( couponStartDate.toLong() 	);
		setCouponEndDate	( couponEndDate.toLong() 	);
		setCouponsInStock	( couponsInStock			);
		setCouponTypeId		( couponTypeId				);
		setCouponPrice		( couponPrice				);
		setImageFileName	( imageFileName				);		
	}

	/**
	 * Constructor for create with companyId
	 */
	public Coupon	( 
						  long      companyId
						, String 	couponTitle
						, YyyyMmDd	couponStartDate
						, YyyyMmDd 	couponEndDate
						, int       couponsInStock
						, int       couponTypeId
						, String    couponMessage						
						, double 	couponPrice 
						, String    imageFileName						
			) 
	{
		super();
		setCompanyId		( companyId  				);		
		setCouponTitle		( couponTitle				);
		setCouponStartDate	( couponStartDate.toLong() 	);
		setCouponEndDate	( couponEndDate.toLong() 	);
		setCouponsInStock	( couponsInStock			);
		setCouponTypeId		( couponTypeId				);
		setCouponPrice		( couponPrice				);
		setImageFileName	( imageFileName				);		
	}

	
	@Override
	public String toString() {
		return  super.toString() + "Coupon\t[couponId=" + couponId + ", companyId=" + companyId + ", couponTitle=" + couponTitle
				+ ", couponStartDate=" + couponStartDateTimeStamp + ", couponEndDate=" + couponEndDateTimeStamp + ", couponsInStock="
				+ couponsInStock + ", couponTypeId=" + couponTypeId + ", couponMessage=" + couponMessage
				+ ", couponPrice=" + couponPrice + ", imageFileName=" + imageFileName + "]\n";
	}
	
	
	public long getCouponId() {
		return couponId;
	}
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getCouponTitle() {
		return couponTitle;
	}
	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}
	public long getCouponStartDate() {
		return couponStartDate;
	}
	public void setCouponStartDate(long couponStartDate) {
		this.couponStartDate = couponStartDate;
		this.couponStartDateTimeStamp = new Timestamp(couponStartDate);
	}
	public long getCouponEndDate() {
		return couponEndDate;
	}
	public void setCouponEndDate(long couponEndDate) {
		this.couponEndDate = couponEndDate;
		this.couponEndDateTimeStamp = new Timestamp(couponEndDate);
	}
	public int getCouponsInStock() {
		return couponsInStock;
	}
	public void setCouponsInStock(int couponsInStock) {
		this.couponsInStock = couponsInStock;
	}
	public int getCouponTypeId() {
		return couponTypeId;
	}
	public void setCouponTypeId(int couponTypeId) {
		this.couponTypeId = couponTypeId;
	}
	public String getCouponMessage() {
		return couponMessage;
	}
	public void setCouponMessage(String couponMessage) {
		this.couponMessage = couponMessage;
	}
	public double getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(double couponPrice) {
		this.couponPrice = couponPrice;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	
	

}