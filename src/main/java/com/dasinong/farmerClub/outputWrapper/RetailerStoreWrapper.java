package com.dasinong.farmerClub.outputWrapper;

import com.dasinong.farmerClub.model.RetailerStore;

public class RetailerStoreWrapper {

	public long id;
	public long ownerId;
	public String name;
	
	public RetailerStoreWrapper(RetailerStore store) {
		this.ownerId = store.getOwnerId();
		this.name = store.getName();
		this.id = store.getId();
	}
}
