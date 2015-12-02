package com.dasinong.farmerClub.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.dasinong.farmerClub.exceptions.InvalidParameterException;
import com.dasinong.farmerClub.exceptions.MissingParameterException;

/**
 * 
 * @author xiahonggao
 *
 *         HttpServeletRequest的wrapper，它的好处是 - 封装了各种常见的类型解析 -
 *         抛出的异常能被BaseController处理
 * 
 */
public class HttpServletRequestX {

	private HttpServletRequest request;

	public HttpServletRequestX(HttpServletRequest request) {
		this.request = request;
	}

	public Long getLong(String paramName) throws InvalidParameterException, MissingParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			throw new MissingParameterException(paramName);
		}

		try {
			return Long.valueOf(valStr);
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Long");
		}
	}

	public Long getLongOptional(String paramName, Long defaultVal) throws InvalidParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			return defaultVal;
		}

		try {
			return Long.valueOf(valStr);
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Long");
		}
	}

	public Long[] getArrayOfLong(String paramName) throws MissingParameterException, InvalidParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			throw new MissingParameterException(paramName);
		}

		String[] units = valStr.split(",");
		Long[] ret = new Long[units.length];

		try {
			for (int i = 0; i < units.length; i++) {
				ret[i] = Long.valueOf(ret[i]);
			}
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Array<Long>");
		}

		return ret;
	}

	public Long[] getArrayOfLongOptional(String paramName, Long[] defaultVal) throws InvalidParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			return defaultVal;
		}

		String[] units = valStr.split(",");
		Long[] ret = new Long[units.length];

		try {
			for (int i = 0; i < units.length; i++) {
				ret[i] = Long.valueOf(units[i]);
			}
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Array<Long>");
		}

		return ret;
	}

	public Integer getInt(String paramName) throws InvalidParameterException, MissingParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			throw new MissingParameterException(paramName);
		}

		try {
			return Integer.parseInt(valStr);
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Integer");
		}
	}

	public Integer getIntOptional(String paramName, Integer defaultVal) throws InvalidParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			return defaultVal;
		}

		try {
			return Integer.parseInt(valStr);
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Integer");
		}
	}

	public Integer[] getArrayOfInt(String paramName) throws MissingParameterException, InvalidParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			throw new MissingParameterException(paramName);
		}

		String[] units = valStr.split(",");
		Integer[] ret = new Integer[units.length];

		try {
			for (int i = 0; i < units.length; i++) {
				ret[i] = Integer.valueOf(ret[i]);
			}
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Array<Long>");
		}

		return ret;
	}

	public Integer[] getArrayOfIntOptional(String paramName, Integer[] defaultVal) throws InvalidParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			return defaultVal;
		}

		String[] units = valStr.split(",");
		Integer[] ret = new Integer[units.length];

		try {
			for (int i = 0; i < units.length; i++) {
				ret[i] = Integer.valueOf(units[i]);
			}
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Array<Long>");
		}

		return ret;
	}

	public Double getDouble(String paramName) throws InvalidParameterException, MissingParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			throw new MissingParameterException(paramName);
		}

		try {
			return Double.parseDouble(valStr);
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Double");
		}
	}

	public Double getDoubleOptional(String paramName, Double defaultVal) throws InvalidParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			return defaultVal;
		}

		try {
			return Double.parseDouble(valStr);
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Double");
		}
	}

	public Double[] getArrayOfDouble(String paramName) throws MissingParameterException, InvalidParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			throw new MissingParameterException(paramName);
		}

		String[] units = valStr.split(",");
		Double[] ret = new Double[units.length];

		try {
			for (int i = 0; i < units.length; i++) {
				ret[i] = Double.valueOf(ret[i]);
			}
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Array<Long>");
		}

		return ret;
	}

	public Double[] getArrayOfDoubleOptional(String paramName, Double[] defaultVal) throws InvalidParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			return defaultVal;
		}

		String[] units = valStr.split(",");
		Double[] ret = new Double[units.length];

		try {
			for (int i = 0; i < units.length; i++) {
				ret[i] = Double.valueOf(units[i]);
			}
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Array<Long>");
		}

		return ret;
	}

	public Boolean getBool(String paramName) throws InvalidParameterException, MissingParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			throw new MissingParameterException(paramName);
		}

		try {
			return Boolean.parseBoolean(valStr);
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Boolean");
		}
	}

	public Boolean getBoolOptional(String paramName, Boolean defaultVal) throws InvalidParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			return defaultVal;
		}

		try {
			return Boolean.parseBoolean(valStr);
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Boolean");
		}
	}

	public Boolean[] getArrayOfBoolean(String paramName) throws MissingParameterException, InvalidParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			throw new MissingParameterException(paramName);
		}

		String[] units = valStr.split(",");
		Boolean[] ret = new Boolean[units.length];

		try {
			for (int i = 0; i < units.length; i++) {
				ret[i] = Boolean.parseBoolean(units[i]);
			}
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Array<Long>");
		}

		return ret;
	}

	public Boolean[] getArrayOfBooleanOptional(String paramName, Boolean[] defaultVal)
			throws InvalidParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			return defaultVal;
		}

		String[] units = valStr.split(",");
		Boolean[] ret = new Boolean[units.length];

		try {
			for (int i = 0; i < units.length; i++) {
				ret[i] = Boolean.parseBoolean(units[i]);
			}
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Array<Long>");
		}

		return ret;
	}

	public String getString(String paramName) throws MissingParameterException {
		String valStr = request.getParameter(paramName);
		// It's valid that client side pass a string as ""
		if (valStr == null) {
			throw new MissingParameterException(paramName);
		}

		return valStr;
	}

	// getString allows empty string (""), while getNonEmptyString doesn't
	public String getNonEmptyString(String paramName) throws MissingParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || "".equals(valStr)) {
			throw new MissingParameterException(paramName);
		}

		return valStr;
	}

	public String getStringOptional(String paramName, String defaultVal) {
		String valStr = request.getParameter(paramName);
		if (valStr == null) {
			return defaultVal;
		}

		return valStr;
	}

	public Date getDate(String paramName) throws InvalidParameterException, MissingParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			throw new MissingParameterException(paramName);
		}

		try {
			try {
				return new Date(Long.parseLong(valStr));
			} catch (Exception ex) {
				return new Date(valStr);
			}
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Date");
		}
	}

	public Date getDateOptional(String paramName, Date defaultVal) throws InvalidParameterException {
		String valStr = request.getParameter(paramName);
		if (valStr == null || valStr.isEmpty()) {
			return defaultVal;
		}

		try {
			try {
				return new Date(Long.parseLong(valStr));
			} catch (Exception ex) {
				return new Date(valStr);
			}
		} catch (Exception ex) {
			throw new InvalidParameterException(paramName, "Date");
		}
	}

	public boolean hasParameter(String parameter) {
		return (request.getParameter(parameter) != null);
	}
}
