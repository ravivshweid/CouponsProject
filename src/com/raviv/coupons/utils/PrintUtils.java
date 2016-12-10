package com.raviv.coupons.utils;

public class PrintUtils 
{
	/*
	 	Example for method print format:
		#--+---------------------------+---------------------------------------------------------
		#--| LoginBlo : User logged in |---------------------------------------------------------
		#--+---------------------------+---------------------------------------------------------
	*/
    public static final void printHeader(final String str) {
        final int    nRowSize = 89;
        final String sTmp     = StringUtils.concat("#--| ", str, " |");
        final int    nTmp     = sTmp.length() - 1;
        final String sLine    = StringUtils.makeLength(StringUtils.makeLength("#--+-", '-', nTmp) + '+', '-', nRowSize);
        System.out.println(sLine);
        System.out.println(StringUtils.makeLength(sTmp, '-', nRowSize));
        System.out.println(sLine);
    }

}
