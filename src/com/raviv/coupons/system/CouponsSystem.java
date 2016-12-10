package com.raviv.coupons.system;

import com.raviv.coupons.dao.utils.ConnectionPoolManager;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.threads.DeleteExpiredCouponsScheduler;
import com.raviv.coupons.utils.PrintUtils;

public class CouponsSystem 
{
	
	private static CouponsSystem singletonInstance;
	
	
	private static DeleteExpiredCouponsScheduler deleteExpiredCouponsScheduler;
	
	static 
	{ 
		singletonInstance = new CouponsSystem();
		
		deleteExpiredCouponsScheduler = new DeleteExpiredCouponsScheduler();
		
		// =====================================================
		// Start the delete expired coupons thread
		// =====================================================
		deleteExpiredCouponsScheduler.start();
	}

	/**
	 * System shut down
	 * @throws ApplicationException 
	 * */
	public void shutDown() throws ApplicationException
	{
		PrintUtils.printHeader("CouponsSystem : shutDown()" );
		// =====================================================
		// Shut down delete expired coupons thread
		// =====================================================
		deleteExpiredCouponsScheduler.shutDown();

		// =====================================================
		// Close all connection pool connections
		// =====================================================
		ConnectionPoolManager.getInstance().closeAllConnections();
	}


	/**
	 * @return an instance for the CouponsSystem singleton
	 * */
	public static CouponsSystem 	getInstance()  
	{
		return singletonInstance;
	}

	/**
	 * private contractor, single tone class
	 */
	private CouponsSystem()
	{
	}
	
}
