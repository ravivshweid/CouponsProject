package com.raviv.coupons.tests.dao;


import com.raviv.coupons.beans.User;
import com.raviv.coupons.dao.UsersDao;
import com.raviv.coupons.enums.UserProfileType;
import com.raviv.coupons.exceptions.ApplicationException;

public class UsersDaoCreateAndGetTest {

	public static void main(String[] args) throws ApplicationException {
		
		User user = new User();
		
		user.setCreatedByUserId(1);
		user.setUpdatedByUserId(1);
		
		UserProfileType userProfileType = UserProfileType.COMPANY;
		
		user.setUserProfileId( userProfileType.getUserProfileId() );
		
		user.setUserName( "USER_NAME" );
		user.setLoginName( "LOGIN_NAME" );
		user.setLoginPassword( "LOGIN_PASSWORD" );

		System.out.println(user);

		
		UsersDao usersDao = new UsersDao();
		usersDao.createUser(user);
		
		
		
		int userId = user.getUserId();
		user = usersDao.getUser(userId);
		System.out.println(user);
	}

}
