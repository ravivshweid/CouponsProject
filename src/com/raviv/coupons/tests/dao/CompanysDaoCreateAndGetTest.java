package com.raviv.coupons.tests.dao;

import com.raviv.coupons.beans.Company;
import com.raviv.coupons.dao.CompanysDao;
import com.raviv.coupons.exceptions.ApplicationException;

public class CompanysDaoCreateAndGetTest {

	public static void main(String[] args) throws ApplicationException {
		
		Company company = new Company();
		
		company.setCreatedByUserId(1);
		company.setUpdatedByUserId(1);
		company.setUserId(6);
		company.setCompanyName("comp raviv");
		company.setCompanyEmail("comp.raviv@gmail.com");
		System.out.println(company);

		
		CompanysDao companysDAO = new CompanysDao();		
		companysDAO.createCompany(company);
				
		long companyId = company.getCompanyId();
		company = companysDAO.getCompany(companyId);
		System.out.println(company);
	}

}
