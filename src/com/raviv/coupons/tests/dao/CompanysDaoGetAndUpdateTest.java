package com.raviv.coupons.tests.dao;

import com.raviv.coupons.beans.Company;
import com.raviv.coupons.dao.CompanysDao;
import com.raviv.coupons.exceptions.ApplicationException;

public class CompanysDaoGetAndUpdateTest {

	public static void main(String[] args) throws ApplicationException 
	{	
		Company company = new Company();
		CompanysDao companysDAO = new CompanysDao();

		company = companysDAO.getCompany(1);
		System.out.println(company);

		company.setCompanyName("new name");
		
		companysDAO.updateCompany(company);

		company = companysDAO.getCompany(1);
		System.out.println(company);

	}

}
