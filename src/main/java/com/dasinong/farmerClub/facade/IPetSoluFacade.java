package com.dasinong.farmerClub.facade;

public interface IPetSoluFacade {

	public abstract Object getPetSoluDetail(Long petSoluId);

	Object getFormattedPetSoluDetail(Long petSoluId);

}