package com.raviv.coupons.blo;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to send dynamic parameters to SQL statement
 * We will build dynamic where clause according to the parameters
 * @author raviv
 *
 */
public class DynamicQueryParameters 
{
	public static final String COUPON_TYPE_ID	= "COUPON_TYPE_ID";
	public static final String FROM_DATE		= "FROM_DATE";
	public static final String TO_DATE 			= "TO_DATE";
	public static final String FROM_PRICE		= "FROM_PRICE";
	public static final String TO_PRICE 		= "TO_PRICE";
		
	private	Map<String,String> map;

	public 	DynamicQueryParameters() 
	{
		this.map = new HashMap<String,String>();
	}
	
	public	void add (String paramName, String paramterValue)
	{
		this.map.put(paramName, paramterValue);
	}
	
	public	Map<String,String> getQueryParameters()
	{
		return this.map;
	}
	
}
