package com.raviv.coupons.threads;

import java.sql.Timestamp;

import com.raviv.coupons.utils.PrintUtils;

/**
 * 
 * Delete expired coupons scheduler
 * Call deleteExpiredCouponsThread every 24 hours
 * 
 * @author raviv
 *
 */
public class DeleteExpiredCouponsScheduler extends Thread 
{
	
	private boolean isAlive = true;

	public  void shutDown()
	{
		isAlive = false;
		PrintUtils.printHeader("DeleteExpiredCouponsScheduler : shutDown() : isAlive = " + isAlive );
	}
	
	@Override
	public  void run()
	{	
		DeleteExpiredCouponsThread deleteExpiredCouponsThread = new DeleteExpiredCouponsThread();
		Timestamp timestamp;
		
		// =========================================================
		// Infinite loop , public  void shutDown() will stop it
		// =========================================================
		while ( isAlive )
		{
			timestamp = new Timestamp(System.currentTimeMillis());
			PrintUtils.printHeader("DeleteExpiredCouponsScheduler started " + timestamp );
			// =========================================================
			// delete expired coupons 
			// =========================================================
			deleteExpiredCouponsThread.start();

			// =========================================================
			// Pause for 24 hours
			// =========================================================
			PrintUtils.printHeader("DeleteExpiredCouponsScheduler go to sleep 24 hours");
            try 
            {
            	long ms = 3600 * 24 ;
				Thread.sleep(ms);
			} 
            catch (InterruptedException e) 
            {
				e.printStackTrace();
			}						
		}// while end
				
	}

}