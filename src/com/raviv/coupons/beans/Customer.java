package com.raviv.coupons.beans;

import java.util.List;

/**
 * Java bean for Customers entity
 * @author raviv
 *
 */
public class Customer extends InfraBean {

	private	long                     	customerId;
//	private	long                     	sysCreationDate;
//	private	long                     	sysUpdateDate;
//	private	int                      	createdByUserId;
//	private	int                      	updatedByUserId;
	private	int                      	userId;
	private	String                   	customerName;
	
	private List<CustomerCoupon>		customerCoupons;
	
	public Customer() 
	{
		super();
	}

	public Customer(long customerId, String customerName) 
	{
		super();
		this.customerId = customerId;
		this.customerName = customerName;
	}

	public Customer(String customerName) {
		super();
		setCustomerName( customerName );
	}

	public List<CustomerCoupon> getCustomerCoupons() {
		return this.customerCoupons;
	}

	public void setCustomerCoupons( List<CustomerCoupon> customerCoupons) {
		this.customerCoupons = customerCoupons;
	}

	@Override
	public String toString() {
		return super.toString() + "Customer\t[customerId=" + customerId + ", userId=" + userId + ", customerName=" + customerName + "]\n";
	}
	
	
	
	
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}