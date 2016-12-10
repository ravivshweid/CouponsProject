package com.raviv.coupons.utils;


public class StringUtils 
{

    public static final String makeLength(final String str, final char ch, final int len) 
    {
    	return makeLength1(str, ch, len, false);
    }

    public static final String makeLength1(final String str,
            final char ch,
            final int len,
            final boolean bPrefix) 
    {
    		return makeLength2(new StringBuffer(len), str, ch, len, bPrefix).toString();
    }

    public static final StringBuffer	makeLength2(final StringBuffer sb,
            final String str,
            final char ch,
            final int len,
            final boolean bPrefix) 
    {
		// If we're not prefixing, append the string to the stringbuffer here.
		if (!bPrefix) {
		sb.append(str);
		}
		
		// Add the specified char until the specifed length is reached.
		for (int i = 0, n = len - str.length(); i < n; i++) {
		sb.append(ch);
		}
		
		// Since we already added the specified char,
		// all we need to do is append the specified string.
		if (bPrefix) {
		sb.append(str);
		}
		return sb;
    }

    /**
     * Concatenates s1, s2 & s3 to one
     */
    public static final String concat(String s1, String s2, String s3) {
        return new StringBuffer(strlen(s1) +
                strlen(s2) +
                strlen(s3))
                .append(s1).append(s2).append(s3).toString();
    }

    private static final int strlen(final String s) {
        return s == null ? 4 : s.length();
    }
    
}
