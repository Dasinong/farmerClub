package com.dasinong.farmerClub.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	public long resourceId;
	public String resourceType;

	public ResourceNotFoundException(Long resourceId, String resourceType) {
		this.resourceId = resourceId;
		this.resourceType = resourceType;
	}

	public Long getResourceId() {
		return this.resourceId;
	}

	public String getResourceType() {
		return this.resourceType;
	}
}
