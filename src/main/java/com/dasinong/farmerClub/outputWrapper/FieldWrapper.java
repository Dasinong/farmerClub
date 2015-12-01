package com.dasinong.farmerClub.outputWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.dasinong.farmerClub.dao.ITaskSpecDao;
import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.NatDis;
import com.dasinong.farmerClub.model.PetDis;
import com.dasinong.farmerClub.model.PetDisSpec;
import com.dasinong.farmerClub.model.SubStage;
import com.dasinong.farmerClub.model.Task;

public class FieldWrapper implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long fieldId;
	private String fieldName;
	private boolean isActive;
	private long varietyId;
	private long userId;
	private long locationId;
	private long monitorLocationId;
	private List<SubStageWrapper> stagelist = new ArrayList<SubStageWrapper>();
	private List<TaskWrapper> taskws = new ArrayList<TaskWrapper>();
	private List<PetDisWrapper> petdisws = new ArrayList<PetDisWrapper>();
	private List<NatDisWrapper> natdisws = new ArrayList<NatDisWrapper>();
	private List<PetDisSpecWrapper> petdisspecws = new ArrayList<PetDisSpecWrapper>();

	private long currentStageID;
	private Date startDate;
	private Date endDate;
	private long yield;
	private boolean workable;
	private boolean sprayable;
	private int dayToHarvest;

	private String cropName;

	public FieldWrapper(Field field, ITaskSpecDao taskSpecDao, int taskLoadType) {
		this.setFieldId(field.getFieldId());
		this.fieldName = (field.getFieldName() == null) ? "" : field.getFieldName();
		this.setActive(field.getIsActive());
		this.setVarietyId(field.getVariety().getVarietyId());
		this.setUserId(field.getUser().getUserId());
		this.setLocationId(field.getLocation().getLocationId());
		this.setMonitorLocationId(field.getMonitorLocationId());
		if (taskLoadType == 1) {
			if (field.getTasks() != null) {
				for (Task t : field.getTasks().values()) {
					taskws.add(new TaskWrapper(t, taskSpecDao));
				}
			}
		}
		if (taskLoadType == 2) {
			if (field.getTasks() != null) {
				for (Task t : field.getTasks().values()) {
					TaskWrapper tw = new TaskWrapper(t, taskSpecDao);
					if (tw.getSubStageId() == field.getCurrentStageID()) {
						taskws.add(tw);
					}
				}
			}
		}
		if (field.getNatDiss() != null) {
			for (NatDis nd : field.getNatDiss().values()) {
				natdisws.add(new NatDisWrapper(nd));
			}
		}
		if (field.getPetDiss() != null) {
			for (PetDis pd : field.getPetDiss().values()) {
				petdisws.add(new PetDisWrapper(pd));
			}
		}
		this.setCurrentStageID(field.getCurrentStageID());
		this.startDate = (field.getStartDate() == null) ? null : field.getStartDate();
		this.endDate = (field.getEndDate() == null) ? null : field.getEndDate();
		this.setYield(field.getYield());

		this.setWorkable(true);
		this.setSprayable(true);

		// Compute day to Harvest here
		if (field.getVariety().getFullCycleDuration() != 0) {
			// Check whether the date make sense.
			Date date = new Date();
			int dayTH = (int) Math.floor(field.getVariety().getFullCycleDuration()
					- (date.getTime() - field.getStartDate().getTime()) / 24 / 60 / 60 / 1000 + field.getDayOffset());
			if (dayTH > 0) {
				field.setDayToHarvest(dayTH);
			} else {
				field.setDayToHarvest(0);
			}
		}
		this.setDayToHarvest(field.getDayToHarvest());

		// For crops with detailed stage
		if (field.getVariety().getSubStages() != null && field.getVariety().getSubStages().size() > 0) {
			for (SubStage ss : field.getVariety().getSubStages()) {
				stagelist.add(new SubStageWrapper(ss));
				if (ss.getSubStageId() == field.getCurrentStageID()) {
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
				}
			}
		} else {
			List<PetDisSpec> pdlist = new ArrayList<PetDisSpec>();
			for (PetDisSpec pds : field.getVariety().getCrop().getPetDisSpecs()) {
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

		// For the case when substage and crop channel not ready.
		if (petdisspecws.size() == 0) {
			List<PetDisSpec> pdlist = new ArrayList<PetDisSpec>();
			for (PetDisSpec pds : field.getVariety().getCrop().getPetDisSpecs()) {
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
		this.setCropName(field.getVariety().getCrop().getCropName());
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public long getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(long varietyId) {
		this.varietyId = varietyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public long getCurrentStageID() {
		return currentStageID;
	}

	public void setCurrentStageID(long currentStageID) {
		this.currentStageID = currentStageID;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getYield() {
		return yield;
	}

	public void setYield(long yield) {
		this.yield = yield;
	}

	public List<TaskWrapper> getTaskws() {
		return taskws;
	}

	public void setTaskws(List<TaskWrapper> taskws) {
		this.taskws = taskws;
	}

	public List<PetDisWrapper> getPetdisws() {
		return petdisws;
	}

	public void setPetdisws(List<PetDisWrapper> petdisws) {
		this.petdisws = petdisws;
	}

	public List<NatDisWrapper> getNatdisws() {
		return natdisws;
	}

	public void setNatdisws(List<NatDisWrapper> natdisws) {
		this.natdisws = natdisws;
	}

	public Long getMonitorLocationId() {
		return monitorLocationId;
	}

	public void setMonitorLocationId(Long monitorLocationId) {
		this.monitorLocationId = monitorLocationId;
	}

	public int getDayToHarvest() {
		return dayToHarvest;
	}

	public void setDayToHarvest(int dayToHarvest) {
		this.dayToHarvest = dayToHarvest;
	}

	public List<PetDisSpecWrapper> getPetdisspecws() {
		return petdisspecws;
	}

	public void setPetdisspecws(List<PetDisSpecWrapper> petdisspecws) {
		this.petdisspecws = petdisspecws;
	}

	public boolean getWorkable() {
		return workable;
	}

	public void setWorkable(boolean workable) {
		this.workable = workable;
	}

	public boolean getSprayable() {
		return sprayable;
	}

	public void setSprayable(boolean sprayable) {
		this.sprayable = sprayable;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public List<SubStageWrapper> getStagelist() {
		return stagelist;
	}

	public void setStagelist(List<SubStageWrapper> stagelist) {
		this.stagelist = stagelist;
	}

}
