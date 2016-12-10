package com.raviv.coupons.threads;

import com.raviv.coupons.dao.CouponsDao;
import com.raviv.coupons.dao.utils.JdbcTransactionManager;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.utils.PrintUtils;

/**
 * 
 * Delete expired coupons thread
 * 
 * @author raviv
 *
 */
public class DeleteExpiredCouponsThread extends Thread {
		
	@Override
	public  void 			run()
	{		
		PrintUtils.printHeader("DeleteExpiredCouponsThread started");

		// =====================================================
		// Start transaction by creating JdbcTransactionManager
		// =====================================================		
		JdbcTransactionManager jdbcTransactionManager = null;

		try 
		{
			jdbcTransactionManager = new JdbcTransactionManager();
		} 
		catch (ApplicationException e1) 
		{
			e1.printStackTrace();
		}


		// Inject transaction manager to DAO via constructor
		CouponsDao couponsDao	= new CouponsDao( jdbcTransactionManager );
		
		try
		{
			// =====================================================
			// Delete coupons and related customer coupons
			// CUSTOMER_COUPON has FK to COUPONS  using coupon id, with delete Cascade
			// =====================================================			
			
			couponsDao.deleteExpiredCoupons();
			
			// =====================================================
			// Commit transaction
			// =====================================================

			jdbcTransactionManager.commit();
						
			PrintUtils.printHeader("DeleteExpiredCouponsThread ended");

		}
		catch (ApplicationException e)
		{
			// =====================================================
			// Rollback transaction
			// =====================================================

			try 
			{
				jdbcTransactionManager.rollback();
			} 
			catch (ApplicationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			
		}
		finally
		{
			jdbcTransactionManager.closeConnection();
		}	
				
	}


}
