package com.raviv.coupons.tests.blo;

import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.system.CouponsSystem;

public class SystemThreadBlo4000DeleteExpiredCouponsThreadTest {

	public static void main(String[] args) throws ApplicationException {
		
		/**
		 * DeleteExpiredCouponsThread
		 */
		CouponsSystem couponsSystem = CouponsSystem.getInstance();
				
		couponsSystem.shutDown();
	}

}
