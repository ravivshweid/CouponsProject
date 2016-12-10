package com.raviv.coupons.beans;

import java.util.List;

/**
 * Java bean for Companys entity
 * @author raviv
 *
 */
public class Company extends InfraBean {

	private	long                     	companyId;
//	private	long                     	sysCreationDate;
//	private	long                     	sysUpdateDate;
//	private	int                      	createdByUserId;
//	private	int                      	updatedByUserId;
	private	int                      	userId;
	private	String                   	companyName;
	private	String                   	companyEmail;
	
	private List<Coupon>				coupons;	
	
	
	public Company() 
	{
		super();
	}

	public Company(String companyName, String companyEmail) 
	{
		super();
		this.companyName	= companyName;
		this.companyEmail 	= companyEmail;
	}



	public Company(long companyId, String companyName, String companyEmail) 
	{
		super();
		this.companyId 		= companyId;
		this.companyName 	= companyName;
		this.companyEmail	= companyEmail;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return super.toString() + "Company\t[companyId=" + companyId + ", userId=" + userId + ", companyName=" + companyName + ", companyEmail=" + companyEmail + "]\n";
	}
	
	public long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	
	
}
