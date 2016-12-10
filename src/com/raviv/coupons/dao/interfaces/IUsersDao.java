package com.raviv.coupons.dao.interfaces;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.exceptions.ApplicationException;

public interface IUsersDao {

	public void 	createUser	(User 	user  ) throws ApplicationException;

	public User 	getUser		(int	userId) throws ApplicationException;

	public void 	updateUser	(User	user  ) throws ApplicationException;

	public void 	deleteUser	(int	userId) throws ApplicationException;
	
}
