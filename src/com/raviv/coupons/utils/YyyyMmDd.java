package com.raviv.coupons.utils;

import java.sql.Timestamp;
/**
 * Convert date from string format yyyymmdd to Timestamp and long
 * @author raviv
 *
 */
public class YyyyMmDd {
		
	private	Timestamp	timestamp;
	
	public YyyyMmDd ( String yyyyMmDd)
	{
		String 	strYear   	= yyyyMmDd.substring(0, 4);
		String 	strMonth	= yyyyMmDd.substring(4, 6);
		String 	strDay   	= yyyyMmDd.substring(6, 8);
		
		long	year 		= Long.parseLong(strYear	);
		long	month		= Long.parseLong(strMonth	);
		long	day			= Long.parseLong(strDay		);
		
		this.timestamp = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00", year , month ,  day ));
		
	}

	@Override
	public String toString() {
		return "YyyyMmDd [timestamp=" + this.timestamp + "]";
	}
	
	public long toLong() {
		return this.timestamp.getTime();
	}

	public Timestamp toTimestamp() {
		return this.timestamp;
	}

}
