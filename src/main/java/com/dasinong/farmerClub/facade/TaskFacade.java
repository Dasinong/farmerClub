package com.dasinong.farmerClub.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IFieldDao;
import com.dasinong.farmerClub.dao.ITaskSpecDao;
import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.Task;
import com.dasinong.farmerClub.outputWrapper.TaskWrapper;

@Transactional
public class TaskFacade implements ITaskFacade {

	private ITaskSpecDao taskSpecDao;
	private IFieldDao fieldDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.ITaskFacade#getAllTask(java.lang.Long)
	 */
	@Override
	public Object getAllTask(Long fieldId) {
		Map<String, Object> result = new HashMap<String, Object>();
		fieldDao = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		taskSpecDao = (ITaskSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("taskSpecDao");

		try {

			Field fd = fieldDao.findById(fieldId);
			if (fd == null) {
				result.put("respCode", 301);
				result.put("message", "找不到田地");
				return result;
			}
			/*
			 * List<TaskWrapper> data = new ArrayList<TaskWrapper>(); for (Task
			 * t : fd.getTasks().values()){ TaskWrapper tw = new TaskWrapper(t);
			 * data.add(tw); }
			 */

			Map<Long, List<TaskWrapper>> data = new HashMap<Long, List<TaskWrapper>>();
			for (Task t : fd.getTasks().values()) {
				TaskWrapper tw = new TaskWrapper(t, taskSpecDao);
				if (data.get(tw.getSubStageId()) == null) {
					List<TaskWrapper> tl = new ArrayList<TaskWrapper>();
					tl.add(tw);
					data.put(tw.getSubStageId(), tl);
				} else {
					data.get(tw.getSubStageId()).add(tw);
				}
			}

			result.put("respCode", 200);
			result.put("message", "获取任务成功");
			result.put("data", data);
			return result;
		} catch (Exception e) {
			result.put("respCode", 500);
			result.put("message", e.getCause());
			return result;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.ITaskFacade#getCurrentTask(java.lang.
	 * Long, java.lang.Long)
	 */
	@Override
	public Object getCurrentTask(Long fieldId, Long currentStageId) {
		Map<String, Object> result = new HashMap<String, Object>();
		fieldDao = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		taskSpecDao = (ITaskSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("taskSpecDao");
		try {

			Field fd = fieldDao.findById(fieldId);
			if (fd == null) {
				result.put("respCode", 301);
				result.put("message", "找不到田地");
				return result;
			}

			List<TaskWrapper> data = new ArrayList<TaskWrapper>();
			for (Task t : fd.getTasks().values()) {
				TaskWrapper tw = new TaskWrapper(t, taskSpecDao);
				data.add(tw);
			}
			result.put("respCode", 200);
			result.put("message", "获取任务成功");
			result.put("data", data);
			return result;
		} catch (Exception e) {
			result.put("respCode", 500);
			result.put("message", e.getCause());
			return result;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.ITaskFacade#updateTasks(java.lang.Long,
	 * java.util.HashMap)
	 */
	@Override
	public Object updateTasks(Long fieldId, HashMap<Long, Boolean> tasks) {
		Map<String, Object> result = new HashMap<String, Object>();
		fieldDao = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");

		Field field = fieldDao.findById(fieldId);
		if (field == null) {
			result.put("respCode", 302);
			result.put("message", "fieldId指定农地不存在");
			return result;
		}

		try {
			for (Entry<Long, Boolean> e : tasks.entrySet()) {
				Task task = field.getTasks().get(e.getKey());
				task.setTaskStatus(e.getValue());
			}
			fieldDao.update(field);
			result.put("respCode", 200);
			result.put("message", "更新任务成功");
			return result;
		} catch (Exception e) {
			result.put("respCode", 500);
			result.put("message", e.getCause() + " 请检测任务编号是否正确");
			return result;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.ITaskFacade#updateTask(java.lang.Long,
	 * java.lang.Long, boolean)
	 */
	@Override
	public Object updateTask(Long fieldId, Long taskid, boolean taskStatus) {
		Map<String, Object> result = new HashMap<String, Object>();
		fieldDao = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		Field field = fieldDao.findById(fieldId);
		if (field == null) {
			result.put("respCode", 302);
			result.put("message", "fieldId指定农地不存在");
			return result;
		}

		try {
			Task task = field.getTasks().get(taskid);
			task.setTaskStatus(taskStatus);
			fieldDao.update(field);
			result.put("respCode", 200);
			result.put("message", "更新任务成功");
			return result;
		} catch (Exception e) {
			result.put("respCode", 500);
			result.put("message", e.getCause() + " 请检测任务编号是否正确");
			return result;
		}

	}

}
