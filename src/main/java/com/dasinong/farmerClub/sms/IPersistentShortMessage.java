package com.dasinong.farmerClub.sms;

import java.util.HashMap;

/**
 * 
 * @author xiahonggao
 *
 * PersistentShortMessage和ShortMessage区别在于，PersistentShortMessage会
 * 把短信内容记录到数据库里，方便追踪／后续重发等功能
 *
 */
public interface IPersistentShortMessage extends IShortMessage {

	public HashMap<String, Object> getPersistentData();

	public String getSerializedPersistentData() throws Exception;
}
