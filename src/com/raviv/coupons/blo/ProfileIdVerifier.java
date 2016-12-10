package com.raviv.coupons.blo;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.enums.UserProfileType;
import com.raviv.coupons.exceptions.ApplicationException;
/**
 * Business logic will verify logged user has right profile : admin, company or customer
 * 
 * @author raviv
 *
 */
public class ProfileIdVerifier {
	
	public static  void 			verifyAdminProfileId(User user) throws ApplicationException 
	{
		int userProfileId = user.getUserProfileId();

		UserProfileType  adminUserProfileType = UserProfileType.ADMIN;
		if      ( userProfileId != adminUserProfileType.getUserProfileId() )
		{
			throw new ApplicationException(ErrorType.INVALID_USER_PROFILE_ID, null
					, "User profile ID must be ADMIN. input userProfileId: " + userProfileId );		

		}		
	}

	public static  void 			verifyCompanyProfileId(User user) throws ApplicationException 
	{
		int userProfileId = user.getUserProfileId();

		UserProfileType  companyUserProfileType = UserProfileType.COMPANY;
		
		if	( userProfileId == companyUserProfileType.getUserProfileId() )
		{
			// COMPANY
			return;
		}		

		throw new ApplicationException ( ErrorType.INVALID_USER_PROFILE_ID, null
					, "User profile ID must be COMPANY. input userProfileId: " + userProfileId );				
	}

	public static  void 			verifyAdminOrCompanyProfileId(User user) throws ApplicationException 
	{
		int userProfileId = user.getUserProfileId();

		UserProfileType  adminUserProfileType = UserProfileType.ADMIN;
		if	( userProfileId == adminUserProfileType.getUserProfileId() )
		{
			// ADMIN
			return;
		}		

		UserProfileType  companyUserProfileType = UserProfileType.COMPANY;
		
		if	( userProfileId == companyUserProfileType.getUserProfileId() )
		{
			// COMPANY
			return;
		}		

		throw new ApplicationException ( ErrorType.INVALID_USER_PROFILE_ID, null
					, "User profile ID must be ADMIN or COMPANY. input userProfileId: " + userProfileId );				
	}

	public static  void 			verifyAdminOrCustomerProfileId(User user) throws ApplicationException 
	{
		int userProfileId = user.getUserProfileId();

		UserProfileType  adminUserProfileType = UserProfileType.ADMIN;
		if	( userProfileId == adminUserProfileType.getUserProfileId() )
		{
			// ADMIN
			return;
		}		

		UserProfileType  customerUserProfileType = UserProfileType.CUSTOMER;
		
		if	( userProfileId == customerUserProfileType.getUserProfileId() )
		{
			// CUSTOMER
			return;
		}		

		throw new ApplicationException ( ErrorType.INVALID_USER_PROFILE_ID, null
					, "User profile ID must be ADMIN or CUSTOMER. input userProfileId: " + userProfileId );				
	}

	public static  void 			verifyCustomerProfileId(User user) throws ApplicationException 
	{
		int userProfileId = user.getUserProfileId();

		UserProfileType  customerUserProfileType = UserProfileType.CUSTOMER;
		
		if	( userProfileId == customerUserProfileType.getUserProfileId() )
		{
			// CUSTOMER
			return;
		}		

		throw new ApplicationException ( ErrorType.INVALID_USER_PROFILE_ID, null
					, "User profile ID must be CUSTOMER. input userProfileId: " + userProfileId );				
	}

}
