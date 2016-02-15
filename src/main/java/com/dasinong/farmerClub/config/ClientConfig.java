package com.dasinong.farmerClub.config;

import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.model.UserType;

public class ClientConfig {
	public boolean enableWelfare;
	public boolean isDaren;
	public ClientConfig (boolean enableWelfare,boolean isDaren ){
		this.enableWelfare = enableWelfare;
		this.isDaren = isDaren;
	}
	
	public ClientConfig (User user){
		if (user==null){
			this.enableWelfare = false;
			this.isDaren = false;
		}
		else{
			this.enableWelfare = user.getInstitutionId()==3L;
			this.isDaren = "jiadadaren".equals(user.getUserType());
		}
	}
}
