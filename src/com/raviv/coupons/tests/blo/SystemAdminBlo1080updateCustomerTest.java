package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.Customer;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CustomersBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;

public class SystemAdminBlo1080updateCustomerTest {

	public static void main(String[] args) throws ApplicationException 
	{
		UsersBlo usersBlo = new UsersBlo();
		CustomersBlo customersBlo = new CustomersBlo();
		
		/**
		 *  Login as admin
		 */		
		User loggedUser = usersBlo.login("admin", "1234");
		
		
		Customer	customer	= new Customer	( 5 , "CUSTOMER 5" );
		
		customersBlo.updateCustomer( loggedUser , customer );

	}

}
