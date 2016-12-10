package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CustomersBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;

public class SystemAdminBlo1090getAllCustomersTest {

	public static void main(String[] args) throws ApplicationException 
	{
		UsersBlo usersBlo = new UsersBlo();
		CustomersBlo customersBlo = new CustomersBlo();
		
		/**
		 *  Login as admin
		 */		
		User loggedUser = usersBlo.login("admin", "1234");
		
		/**
		 *  Get all customers
		 */				
		customersBlo.getAllCustomers(loggedUser);

	}

}
