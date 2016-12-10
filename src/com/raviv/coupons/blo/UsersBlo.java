package com.raviv.coupons.blo;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.dao.UsersDao;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.utils.PrintUtils;

/**
 * 
 * Users business logic
 *
 */
public class UsersBlo {
	
	private UsersDao usersDao;

	public UsersBlo()
	{
		usersDao = new UsersDao();
	}
	
	public  User login(String loginName, String loginPassword) throws ApplicationException 
	{
		//==================================================
		// Get user from data layer
		//==================================================
		User 		user;				
		user = this.usersDao.getUserByLoginNameLoginPassword( loginName, loginPassword );
		if ( user == null )
		{
			throw new ApplicationException(ErrorType.LOGIN_ERROR, null
					, "Login error. loginName : " + loginName + ",  loginPassword : " + loginPassword );
		}
		PrintUtils.printHeader("LoginBlo : User logged in");		
		System.out.println(user);
		
		return user;
	}

}