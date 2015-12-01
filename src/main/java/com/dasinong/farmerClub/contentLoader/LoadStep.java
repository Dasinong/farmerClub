package com.dasinong.farmerClub.contentLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.ICropDao;
import com.dasinong.farmerClub.dao.ISubStageDao;
import com.dasinong.farmerClub.dao.ITaskSpecDao;
import com.dasinong.farmerClub.dao.IVarietyDao;
import com.dasinong.farmerClub.dao.SubStageDao;
import com.dasinong.farmerClub.dao.VarietyDao;
import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.model.Step;
import com.dasinong.farmerClub.model.SubStage;
import com.dasinong.farmerClub.model.TaskSpec;
import com.dasinong.farmerClub.model.Variety;

import au.com.bytecode.opencsv.CSVReader;

public class LoadStep {
	public final File FILEFOLDER = new File(new File("E:/git/PloughHelper/").getAbsoluteFile(), "sourcefiles");
	public final File FILE_STAGE = new File(FILEFOLDER, "stage.csv");
	public final File FILE_STEP = new File(FILEFOLDER, "task_step.csv");
	public final File FILE_LINK = new File(FILEFOLDER, "variety_subStage.csv");
	public Map<Long, SubStage> subStageMap = new HashMap<Long, SubStage>();
	public Map<Long, TaskSpec> taskSpecMap = new HashMap<Long, TaskSpec>();
	// get Datasource
	DataSource dataSource = (DataSource) ContextLoader.getCurrentWebApplicationContext().getBean("dataSource");
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

	public void test() {
		// SubStageDao subStageDao = (SubStageDao)
		// ContextLoader.getCurrentWebApplicationContext().getBean("subStageDao");
		// System.out.println(subStageDao.findBySubStageName("播种").getVarieties().size());
		IVarietyDao varietyDao = (IVarietyDao) ContextLoader.getCurrentWebApplicationContext().getBean("varietyDao");
		Variety variety = varietyDao.findById(8849L);
		System.out.println(variety.getVarietyName());
		System.out.println(variety.getSubStages().size());
	}

	public void linkVarietySubstage() throws IOException {
		// get cropId for 稻
		ICropDao cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		Crop crop = cropDao.findByCropName("稻");
		Long cropId = crop.getCropId();

		// get all varieties of 稻
		VarietyDao varietyDao = (VarietyDao) ContextLoader.getCurrentWebApplicationContext().getBean("varietyDao");
		List list = varietyDao.getHibernateTemplate().find("FROM Variety WHERE cropId=?", cropId);
		ArrayList<Long> varietyIds = new ArrayList<Long>();
		for (int i = 0; i < list.size(); i++) {
			Variety variety = (Variety) list.get(i);
			varietyIds.add(variety.getVarietyId());
		}

		// get all subStageIds
		SubStageDao subStageDao = (SubStageDao) ContextLoader.getCurrentWebApplicationContext().getBean("subStageDao");
		String hql = "from SubStage";
		list = subStageDao.getHibernateTemplate().find(hql);
		ArrayList<Long> subStageIds = new ArrayList<Long>();
		for (int i = 0; i < list.size(); i++) {
			SubStage subStage = (SubStage) list.get(i);
			subStageIds.add(subStage.getSubStageId());
		}

		System.out.println(varietyIds.size());
		System.out.println(subStageIds.size());

		FileWriter fw = new FileWriter(FILE_LINK);
		BufferedWriter bw = new BufferedWriter(fw);

		for (int i = 0; i < subStageIds.size(); i++) {
			Long subStageId = subStageIds.get(i);
			for (int j = 0; j < varietyIds.size(); j++) {
				Long varietyId = varietyIds.get(j);
				if ((i == subStageIds.size() - 1) && (j == varietyIds.size() - 1)) {
					bw.write(varietyId + "," + subStageId);
				} else {
					bw.write(varietyId + "," + subStageId + "\n");
				}
			}

			System.out.println(i);
		}
		bw.flush();
		bw.close();
		fw.close();

		// Execute LinkVarietySubstage.sql file

	}

	public void insertBatch(final Long subStageId, final List<Long> varietyIds) {
		String sql = "INSERT INTO variety_subStage" + "(varietyId,subStageId) VALUES (?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Long varietyId = varietyIds.get(i);
				ps.setLong(1, varietyId);
				ps.setLong(2, subStageId);
			}

			@Override
			public int getBatchSize() {
				return varietyIds.size();
			}
		});

	}

	public void readFile() throws IOException {
		ISubStageDao subStageDao = (ISubStageDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subStageDao");
		ITaskSpecDao taskSpecDao = (ITaskSpecDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("taskSpecDao");
				// read in stage file

		// get cropId for 稻
		// CropDao cropDao = (CropDao)
		// ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		// Crop crop = cropDao.findByCropName("稻");
		// Long cropId = crop.getCropId();

		// get all varieties of 稻
		// VarietyDao varietyDao = (VarietyDao)
		// ContextLoader.getCurrentWebApplicationContext().getBean("varietyDao");
		// List list = varietyDao.getHibernateTemplate().find(
		// "FROM Variety WHERE cropId=?",43);
		// Set<Variety> varieties = new HashSet<Variety>(list);

		// CSVReader reader = new CSVReader(new FileReader(FILE_STAGE), ',',
		// '\"',1); // first line is title
		FileInputStream fr = new FileInputStream(FILEFOLDER + "/stage.csv");
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(fr,"UTF-8"));
		CSVReader reader = new CSVReader(new InputStreamReader(fr, "UTF-8"), ',', '\"', 1);
		List entries = reader.readAll();
		for (int i = 0; i < entries.size(); i++) {
			// create a subStage object for each entry
			String items[] = (String[]) entries.get(i);
			SubStage subStage = generateSubStage(items);
			subStageDao.save(subStage);
		}

		// subStageMap 构造完成
		// All subStages have been saved to database, correctly linked to their
		// corresponding steps

		// SubStage subStage = subStageDao.findBySubStageName("播种");
		// subStage.setVarieties(varieties);
		// subStageDao.update(subStage);
		//
		// System.out.println(subStage.getVarieties().size());

		fr = new FileInputStream(FILE_STEP);
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(fr,"UTF-8"));
		reader = new CSVReader(new InputStreamReader(fr, "UTF-8"), ',', '\"', 1);
		// reader = new CSVReader(new FileReader(FILE_STEP), ',', '\"',1); //
		// first line is title
		entries = reader.readAll();
		for (int i = 0; i < entries.size(); i++) {
			// create a subStage object for each entry
			String items[] = (String[]) entries.get(i);
			TaskSpec taskSpec = generateTaskSpec(items);
			if (!taskSpecMap.containsKey(taskSpec.getTaskSpecId())) {
				taskSpecMap.put(taskSpec.getTaskSpecId(), taskSpec); // add to
																		// taskSpecMap
																		// for
																		// constructing
																		// steps
			}
			generateStep(items);
		}
		for (Long key : taskSpecMap.keySet()) {
			TaskSpec taskSpec = taskSpecMap.get(key);
			List<Step> steps = taskSpec.getSteps();
			taskSpec.setSteps(steps);
			taskSpecDao.save(taskSpec);
		}
	}

	public TaskSpec generateTaskSpec(String[] items) {
		TaskSpec taskSpec = null;
		Long taskSpecId = Long.parseLong(items[0]);
		Long subStageId = Long.parseLong(items[3]);

		SubStage subStage = subStageMap.get(subStageId);
		taskSpec = new TaskSpec(items[1], subStage, items[2]);
		taskSpec.setTaskSpecId(taskSpecId);

		return taskSpec;
	}

	public void generateStep(String[] items) {
		Long taskSpecId = Long.parseLong(items[0]);
		TaskSpec taskSpec = taskSpecMap.get(taskSpecId);
		String regions[] = items[4].split("，");

		for (int i = 0; i < regions.length; i++) {
			Step step = new Step(items[6], taskSpec);
			step.setFitRegion(regions[i].trim());
			step.setDescription(items[7]);
			step.setPicture(items[8]);

			taskSpec.getSteps().add(step);
		}
	}

	public SubStage generateSubStage(String[] items) {
		SubStage subStage = new SubStage(items[1], items[2]);
		subStage.setSubStageId(Long.parseLong(items[0]));
		if (!items[3].trim().equals("")) {
			subStage.setReqMinTemp(Integer.parseInt(items[3].trim()));
		}
		if (!items[4].trim().equals("")) {
			subStage.setReqAvgTemp(Integer.parseInt(items[4].trim()));
		}
		if (!items[8].trim().equals("")) {
			String duration = items[8].trim();
			String durations[] = duration.split("，");
			double durationLow = (new Double(durations[0].trim().replace("%", "")));
			subStage.setDurationLow(durationLow);
			double durationMid = (new Double(durations[1].trim().replace("%", "")));
			subStage.setDurationMid(durationMid);
			double durationHigh = (new Double(durations[2].trim().replace("%", "")));
			subStage.setDurationHigh(durationHigh);
		}
		subStageMap.put(subStage.getSubStageId(), subStage); // add to
																// subStageMap
																// for future
																// use
		return subStage;
	}
}
