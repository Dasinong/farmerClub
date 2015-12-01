package com.dasinong.farmerClub.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dasinong.farmerClub.outputWrapper.CPProductWrapper;
import com.dasinong.farmerClub.outputWrapper.PetDisSpecWrapper;
import com.dasinong.farmerClub.outputWrapper.VarietyWrapper;

public interface IBaiKeFacade {

	public abstract CPProductWrapper getCPProductById(Long id);

	public abstract VarietyWrapper getVarietyById(Long id);

	public abstract PetDisSpecWrapper getPetDisSpecById(Long id);

	Object getCropByType(String type);

	Object browseVarietyByCropId(Long cropId);

	Object browseCPProductByModel(String model);

	Object browsePetDisByType(String type);

	Object getVarietysByName(String name);

	Object getCPProdcutsByIngredient(String ingredient);

	List<HashMap<String, String>> searchVariety(String key);

	List<HashMap<String, String>> searchCPProduct(String key);

	Map<String, List<HashMap<String, String>>> searchPetDisSpec(String key);

	Object browsePetDisSpecsByCropIdAndType(Long cropId, String type);

}