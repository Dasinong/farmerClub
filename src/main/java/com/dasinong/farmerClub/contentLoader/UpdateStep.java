package com.dasinong.farmerClub.contentLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import au.com.bytecode.opencsv.CSVReader;

import com.dasinong.farmerClub.dao.ITaskSpecDao;
import com.dasinong.farmerClub.model.Step;
import com.dasinong.farmerClub.model.TaskSpec;

public class UpdateStep implements IUpdateStep {
	public final static File FILE = new File("/Users/jiachengwu/Documents/sourcefiles/step.csv");
	Map<Long, TaskSpec> taskSpecMap = new LinkedHashMap<Long, TaskSpec>();

	@Override
	@Transactional
	public void update() {
		ITaskSpecDao taskSpecDao = (ITaskSpecDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("taskSpecDao");
		TaskSpec taskSpec = taskSpecDao.findById(40L);
		Step step = new Step("test", taskSpec);
		System.out.println(taskSpec.getSteps().size());
		taskSpec.getSteps().remove(1);
		taskSpecDao.update(taskSpec);
		// taskSpecDao.update(taskSpec);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dasinong.farmerClub.contentLoader.IUpdateStep#run()
	 */
	@Override
	@Transactional
	public void run() {
		ITaskSpecDao taskSpecDao = (ITaskSpecDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("taskSpecDao");
		Map<Long, TaskSpec> taskSpecMap = new LinkedHashMap<Long, TaskSpec>();
		try {
			FileInputStream fr = new FileInputStream(FILE);
			CSVReader reader = new CSVReader(new InputStreamReader(fr, "UTF-8"), ',', '\"', 1);
			List entries = reader.readAll();
			for (int i = 0; i < entries.size(); i++) {
				// create a subStage object for each entry
				String items[] = (String[]) entries.get(i);
				Long taskSpecId = Long.parseLong(items[2]);
				String stepName = items[1];
				String fitRegion = items[3];
				String description = items[4];
				String picture = items[5];
				if (!taskSpecMap.containsKey(taskSpecId)) {
					TaskSpec taskSpec = taskSpecDao.findById(taskSpecId);
					Step step = new Step(stepName, taskSpec);
					step.setDescription(description);
					step.setFitRegion(fitRegion);
					step.setPicture(picture);
					taskSpec.getSteps().add(step);
					taskSpecMap.put(taskSpecId, taskSpec);
				} else {
					TaskSpec taskSpec = taskSpecMap.get(taskSpecId);
					Step step = new Step(stepName, taskSpec);
					step.setDescription(description);
					step.setFitRegion(fitRegion);
					step.setPicture(picture);
					taskSpec.getSteps().add(step);
				}
			}

			for (Long id : taskSpecMap.keySet()) {
				System.out.println(id);
				TaskSpec taskSpec = taskSpecMap.get(id);
				taskSpecDao.update(taskSpec);
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
