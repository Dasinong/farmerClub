package com.dasinong.farmerClub.outputWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dasinong.farmerClub.model.Step;
import com.dasinong.farmerClub.model.TaskSpec;

public class TaskSpecWrapper implements Serializable {
	private static final long serialVersionUID = 1L;

	public Long taskSpecId;
	public String taskSpecName;
	public Long subStageId;
	public List<StepWrapper> steps = new ArrayList<StepWrapper>();
	public String type;
	
	public TaskSpecWrapper(TaskSpec ts) {
		this.taskSpecId = ts.getTaskSpecId();
		this.taskSpecName = ts.getTaskSpecName();
		this.subStageId = ts.getSubStage().getSubStageId();
		if (ts.getSteps() != null) {
			for (Step s : ts.getSteps()) {
				steps.add(new StepWrapper(s));
			}
		}
		this.type = ts.getType();
	}
}
