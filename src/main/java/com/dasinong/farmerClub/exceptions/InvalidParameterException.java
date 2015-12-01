package com.dasinong.farmerClub.exceptions;

import java.util.HashMap;
import java.util.Map;

public class InvalidParameterException extends Exception {

	HashMap<String, String> parameters;

	public InvalidParameterException(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	public InvalidParameterException(String paramName, String paramType) {
		parameters = new HashMap<String, String>();
		parameters.put(paramName, paramType);
	}

	public InvalidParameterException(String para1, String type1, String para2, String type2) {
		parameters = new HashMap<String, String>();
		parameters.put(para1, type1);
		parameters.put(para2, type2);
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			result.append(entry.getKey() + ":" + entry.getValue() + ";");
		}
		return result.toString();
	}

	public HashMap<String, String> getParams() {
		return this.parameters;
	}
}
