package com.dasinong.farmerClub.outputWrapper;

import java.sql.Timestamp;

import com.dasinong.farmerClub.model.Store;
import com.dasinong.farmerClub.store.StoreSource;
import com.dasinong.farmerClub.store.StoreStatus;

public class StoreWrapper {

	public Long id;
	public String name;
	public String desc;
	public Long ownerId;
	public String contactName;
	public String location;
	public String province;
	public String streetAndNumber;
	public String phone;
	public String cellphone;
	public int type;
	public StoreStatus status;
	public StoreSource source;
	public Timestamp createdAt;
	public Timestamp updatedAt;
	
	public StoreWrapper(Store store) {
		this.id = store.getId();
		this.name = store.getName();
		this.desc = store.getDesc();
		this.ownerId = store.getOwnerId();
		this.contactName = store.getContactName();
		this.location = store.getLocation().toString();
		//Change this to city when UI updated.
		this.province = store.getLocation().getCity();
		this.streetAndNumber = store.getStreetAndNumber();
		this.phone = store.getPhone();
		this.cellphone = store.getCellphone();
		this.type = store.getType();
		this.status = store.getStatus();
		this.source = store.getSource();
		this.createdAt = store.getCreatedAt();
		this.updatedAt = store.getUpdatedAt();
	}
}
