package com.dasinong.farmerClub.facade;

public interface IDisasterReportFacade {

	public abstract Object insertDisasterReport(String cropName, Long fieldId, String disasterType, String disasterName,
			String affectedArea, String eruptionTime, String disasterDist, String fieldOperations,
			String imageFilenames);
}