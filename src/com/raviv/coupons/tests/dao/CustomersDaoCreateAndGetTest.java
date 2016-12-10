package com.raviv.coupons.tests.dao;


import com.raviv.coupons.beans.Customer;
import com.raviv.coupons.dao.CustomersDao;
import com.raviv.coupons.exceptions.ApplicationException;

public class CustomersDaoCreateAndGetTest {

	public static void main(String[] args) throws ApplicationException {
		
		Customer customer = new Customer();
		
		customer.setCreatedByUserId(1);
		customer.setUpdatedByUserId(1);
		customer.setUserId(4);
		customer.setCustomerName("CustomerName");
		System.out.println(customer);
	

		CustomersDao customersDao = new CustomersDao();		
		customersDao.createCustomer(customer);
		
		
		long customerId = customer.getCustomerId();
		customer = customersDao.getCustomer(customerId);
		System.out.println(customer);
	}

}
