package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.Company;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CompanysBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;

public class SystemAdminBlo1030updateCompanyTest {

	public static void main(String[] args) throws ApplicationException 
	{
		UsersBlo usersBlo = new UsersBlo();
		CompanysBlo companyBl = new CompanysBlo();
		
		/**
		 *  Login as admin
		 */		
		User loggedUser = usersBlo.login("admin", "1234");
			
		/**
		 *  Update company
		 */		
						
		Company	company	= new Company	( 4 , "Company test yyy" ,"yyy@gmail.com"	);
		
		companyBl.updateCompany( loggedUser , company );

	}

}
