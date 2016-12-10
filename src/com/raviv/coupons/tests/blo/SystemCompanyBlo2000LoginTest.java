package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;

public class SystemCompanyBlo2000LoginTest {

	public static void main(String[] args) throws ApplicationException 
	{
		UsersBlo usersBlo = new UsersBlo();
		
		User user = usersBlo.login("comp1", "1234");
				
		System.out.println(user);

	}

}
