package com.dasinong.farmerClub.outputWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.model.PetDisSpec;
import com.dasinong.farmerClub.model.SubStage;
import com.dasinong.farmerClub.model.TaskSpec;

public class CropDetailsWrapper {
	
	public CropWrapper crop;
	public List<SubStageWrapper> substagews = new ArrayList<SubStageWrapper>();
	public List<PetDisSpecWrapper> petdisspecws = new ArrayList<PetDisSpecWrapper>();
	public List<TaskSpecWrapper> taskspecws = new ArrayList<TaskSpecWrapper>();

	public CropDetailsWrapper(Crop crop) {
		this(crop, null);
	}
	
	public CropDetailsWrapper(Crop crop, Long subStageId) {
		this.crop = new CropWrapper(crop);
		
		Set<SubStage> subStages = crop.getSubStages();
		if (subStages != null && subStages.size() > 0) {
			Long currentStageId = subStageId;
			for (SubStage ss : subStages) {
				// By default, it's the first stage
				if (currentStageId == null) {
					currentStageId = ss.getSubStageId();
				}
				
				substagews.add(new SubStageWrapper(ss));
				if (ss.getSubStageId() == currentStageId) {
					// Populate petdisspec
					if (ss.getPetDisSpecs() != null) {
						List<PetDisSpec> pdlist = new ArrayList<PetDisSpec>();
						for (PetDisSpec pds : ss.getPetDisSpecs()) {
							pdlist.add(pds);
						}
						Collections.sort(pdlist);
						int count = 0;
						for (PetDisSpec pds : pdlist) {
							PetDisSpecWrapper pdsw = new PetDisSpecWrapper(pds);
							petdisspecws.add(pdsw);
							count++;
							if (count >= 4) 
								break;
						}
					}
					
					// Populate taskspec
					if (ss.getTaskSpecs() != null) {
						List<TaskSpec> tslist = new ArrayList<TaskSpec>();
						for (TaskSpec ts : ss.getTaskSpecs()) {
							tslist.add(ts);
						}
						for (TaskSpec ts : tslist) {
							TaskSpecWrapper tsw = new TaskSpecWrapper(ts);
							taskspecws.add(tsw);
						}
					}
				}
			}
		}
	}
}
