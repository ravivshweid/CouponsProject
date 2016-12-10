package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CompanysBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;

public class SystemAdminBlo1050getCompanyTest {

	public static void main(String[] args) throws ApplicationException 
	{
		
		UsersBlo usersBlo = new UsersBlo();
		CompanysBlo companysBlo = new CompanysBlo();
		
		/**
		 *  Login as admin
		 */		
		User loggedUser = usersBlo.login("admin", "1234");
		//User loggedUser = usersBlo.login("comp1", "1234");
		//User loggedUser = usersBlo.login("cust2", "1234");

		
		/**
		 *  Get company
		 */		
		companysBlo.getCompany( loggedUser , 3 );

	}

}
