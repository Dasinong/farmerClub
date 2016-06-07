package com.dasinong.farmerClub.coupon.exceptions;

public class NotEnoughAuthException extends Exception{
	double jiandaauth;
	double jiandasum;
	double kairunauth;
	double kairunsum;
	public NotEnoughAuthException(double jiandaauth, double jiandasum, double kairunauth, double kairunsum) {
		super();
		this.jiandaauth = jiandaauth;
		this.jiandasum = jiandasum;
		this.kairunauth = kairunauth;
		this.kairunsum = kairunsum;
	}
	
	public String toString(){
		StringBuilder result = new StringBuilder();
		if (jiandaauth<jiandasum){
			result.append("健达授权："+ jiandaauth + "L ");
			result.append("需要授权："+ jiandasum + "L");
		}
		if (kairunauth<kairunsum){
			result.append("凯润授权："+ kairunauth + "L ");
			result.append("需要授权："+ kairunsum + "L");
		}
		return result.toString();
	}
}
