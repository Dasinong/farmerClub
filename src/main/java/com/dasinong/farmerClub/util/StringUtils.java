package com.dasinong.farmerClub.util;

public class StringUtils {

	public static String join(String delimiter, String[] arr) {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0;i<arr.length;i++) {
			sb.append(arr[i]);
			if (i != arr.length - 1) {
				sb.append(delimiter);
			}
		}
		
		return sb.toString();
	}
}
