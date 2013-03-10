package com.example.dropbox.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DataUtils {
	
	
	public  String convertBytesToMegaBytes(String bytes) {
		BigDecimal lBytes;
		try {
			 lBytes = new BigDecimal(bytes);
			lBytes = lBytes.divideToIntegralValue(new BigDecimal(new BigInteger("1000000")));
		} catch(Exception ex) {
			System.out.println("exception at Datautils:"+ex.toString());
			return bytes;
		}
		return String.valueOf(lBytes);
	}
	
	  public  String truncateText(String str, int maxLen, int lenFromEnd) {
		    int len;
		    if (str != null && (len = str.length()) > maxLen) {
		      return str.substring(0, maxLen - lenFromEnd - 3) + "..." +
		          str.substring(len - lenFromEnd, len);
		    }
		    return str;
		  }


}
