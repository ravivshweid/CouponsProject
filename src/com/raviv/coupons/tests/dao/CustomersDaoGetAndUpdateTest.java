package com.raviv.coupons.tests.dao;


import com.raviv.coupons.beans.Customer;
import com.raviv.coupons.dao.CustomersDao;
import com.raviv.coupons.exceptions.ApplicationException;

public class CustomersDaoGetAndUpdateTest {

	public static void main(String[] args) throws ApplicationException 
	{		
		Customer 		customer 	= new Customer();
		CustomersDao 	customersDao= new CustomersDao();

		customer = customersDao.getCustomer(1);
		System.out.println(customer);

		customer.setCustomerName("new customerName");	
		customer.setUserId(4);	
		customersDao.updateCustomer(customer);

		customer = customersDao.getCustomer(1);
		System.out.println(customer);
	}

}
