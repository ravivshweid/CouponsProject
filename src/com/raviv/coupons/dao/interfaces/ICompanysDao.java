package com.raviv.coupons.dao.interfaces;

import com.raviv.coupons.beans.Company;
import com.raviv.coupons.exceptions.ApplicationException;

public interface ICompanysDao {
	
	public void 	createCompany(Company company) throws ApplicationException;

	public Company 	getCompany	 (long 	  companyId) throws ApplicationException;

	public void 	updateCompany(Company company) throws ApplicationException;

	public void 	deleteCompany(long    companyId) throws ApplicationException;

}
