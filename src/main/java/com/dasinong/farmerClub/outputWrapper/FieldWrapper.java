package com.dasinong.farmerClub.outputWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.PetDisSpec;
import com.dasinong.farmerClub.model.SubStage;
import com.dasinong.farmerClub.model.TaskSpec;

public class FieldWrapper implements Serializable {
	private static final long serialVersionUID = 1L;

	public Long fieldId;
	public String fieldName;
	public CropWrapper crop;
	public long userId;
	public long locationId;
	public long monitorLocationId;
	public double area;
	public List<SubStageWrapper> stagelist = new ArrayList<SubStageWrapper>();
	public List<PetDisSpecWrapper> petdisspecws = new ArrayList<PetDisSpecWrapper>();
	public List<TaskSpecWrapper> taskspecws = new ArrayList<TaskSpecWrapper>();

	public long currentStageID;

	public FieldWrapper(Field field) {
		this.crop = new CropWrapper(field.getCrop());
		this.area = field.getArea();
		this.fieldId = field.getFieldId();
		this.fieldName = (field.getFieldName() == null) ? "" : field.getFieldName();
		this.userId = field.getUser().getUserId();
		this.locationId = field.getLocation().getLocationId();
		this.monitorLocationId = field.getMonitorLocationId();
		this.currentStageID = field.getCurrentStageID();

		// For crops with detailed stage
		Set<SubStage> subStages = field.getCrop().getSubStages();
		if (subStages != null && subStages.size() > 0) {
			for (SubStage ss : subStages) {
				stagelist.add(new SubStageWrapper(ss));
				
				if ((long) ss.getSubStageId() == (long) field.getCurrentStageID()) {
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
		
		// Some crops are not ready. By not ready, it means
		// - subStages are not ready
		// - petdisspecs are not categorized according to subStage
		// - taskspecs are not categorized according to subStage
	}

}
