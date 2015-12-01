/**
 * 
 */
package com.dasinong.farmerClub.ruleEngine.domain;

import java.util.Date;

/**
 * @author caichao
 *
 */
public class Stage {
	private StageType type;
	private Date startDate;

	public StageType getType() {
		return type;
	}

	public void setType(StageType type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
