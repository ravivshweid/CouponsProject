package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.Company;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CompanysBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;

public class SystemAdminBlo1010createCompanyTest {

	public static void main(String[] args) throws ApplicationException 
	{
		UsersBlo usersBlo = new UsersBlo();
		CompanysBlo companyBl = new CompanysBlo();
		
		/**
		 *  login as admin
		 */		
		User loggedUser = usersBlo.login("admin", "1234");
		

		/**
		 *  Create new company
		 */		

		User 	newUser	= new User		( "Raviv Shweid"  , "raviv9", "unix11" 			);
		Company	company	= new Company	( "Company test 8", "raviv.shweid@gmail.com"	);
				
		companyBl.createCompany( loggedUser, newUser , company);
		
	}

}
