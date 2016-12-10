package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CompanysBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;

public class SystemAdminBlo1020deleteCompanyTest {

	public static void main(String[] args) throws ApplicationException 
	{
		
		UsersBlo usersBlo = new UsersBlo();
		CompanysBlo companyBl = new CompanysBlo();
		
		/**
		 *  login as admin
		 */		
		User loggedUser = usersBlo.login("admin", "1234");
		

		/**
		 *  Delete company
		 */		
				
		companyBl.deleteCompany( loggedUser, 5);
		

	}

}
