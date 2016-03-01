package com.dasinong.farmerClub.util;

import java.util.Random;

public class Refcode {
	public static String GenerateRefcode() {
		Random rnd = new Random();
		// int org = rnd.nextInt(1<<30);
		int org = rnd.nextInt(900000) + 100000;
		// StringBuilder result = new StringBuilder();
		/*
		 * for(int i=0;i<6;i++){ int cur = org % 32; if (cur<10)
		 * result.append((char) (cur+48)); else result.append((char) (cur+87));
		 * org = org >>5; }
		 */
		// return result.toString();
		return "" + org;
	}
	
	

	public static void main(String[] args) {
		System.out.println(Refcode.GenerateRefcode());
		System.out.println(Refcode.GenerateRefcode());
		System.out.println(Refcode.GenerateRefcode());
		System.out.println(Refcode.GenerateRefcode());
		System.out.println(Refcode.GenerateRefcode());

		System.out.println(Refcode.GenerateRefcode());
		System.out.println(Refcode.GenerateRefcode());
	}
}
