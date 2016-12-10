package com.raviv.coupons.tests.dao;


import com.raviv.coupons.beans.User;
import com.raviv.coupons.dao.UsersDao;
import com.raviv.coupons.exceptions.ApplicationException;

public class UsersDaoGetAndUpdateTest {

	public static void main(String[] args) throws ApplicationException 
	{
		User 		user 			= new User();
		UsersDao 	customersDao	= new UsersDao();

		user = customersDao.getUser(1);
		System.out.println(user);

		user.setUserName("new userName");	
		user.setLoginName("admin01");	
		user.setLoginPassword("4321");	

		customersDao.updateUser(user);

		user = customersDao.getUser(1);
		System.out.println(user);
	}

}
