package com.dasinong.farmerClub.facade;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface IWeatherFacade {

	public abstract Object getWeather(double lat, double lon)
			throws IOException, ParseException, NumberFormatException, ParserConfigurationException, SAXException;

	public abstract Object getWeather(Long areaId);

}