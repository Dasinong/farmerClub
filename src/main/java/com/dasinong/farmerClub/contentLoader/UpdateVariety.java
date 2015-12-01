package com.dasinong.farmerClub.contentLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.ICropDao;
import com.dasinong.farmerClub.dao.ISubStageDao;
import com.dasinong.farmerClub.dao.IVarietyDao;
import com.dasinong.farmerClub.dao.SubStageDao;
import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.model.SubStage;
import com.dasinong.farmerClub.model.Variety;

import au.com.bytecode.opencsv.CSVReader;

public class UpdateVariety {
	public final static File FILE = new File("/Users/jiachengwu/Documents/sourcefiles/crop.csv");
	public final static File SOURCEFILE = new File("/Users/jiachengwu/Documents/sourcefiles/种子库.txt");
	public final static String BLOCK_SEPARATOR = "--------------------";
	public final static String SECTIONNAME_SEPARATOR = ":|：";
	public Map<String, String> variety_cropMap = new HashMap<String, String>();

	public void update() {
		ICropDao cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		IVarietyDao varietyDao = (IVarietyDao) ContextLoader.getCurrentWebApplicationContext().getBean("varietyDao");
		ArrayList<ArrayList<String>> blocks = LoadFileUtil.generateBlocks(SOURCEFILE, BLOCK_SEPARATOR);
		for (int i = 0; i < blocks.size(); i++) {
			processBlock(blocks.get(i));
			// variety_cropMap constructed
		}

	}

	public void run() {
		ICropDao cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		IVarietyDao varietyDao = (IVarietyDao) ContextLoader.getCurrentWebApplicationContext().getBean("varietyDao");

		try {
			FileInputStream fr = new FileInputStream(FILE);
			CSVReader reader = new CSVReader(new InputStreamReader(fr, "UTF-8"), ',', '\"', 1);
			List entries = reader.readAll();
			for (int i = 0; i < entries.size(); i++) {
				// create a subStage object for each entry
				String items[] = (String[]) entries.get(i);
				String cropName = items[1];
				Crop crop = cropDao.findByCropName(cropName);
				String varietyName = "通用" + cropName;
				Variety variety = new Variety(varietyName, crop);
				variety.setSubId("");
				variety.setRegisterationId(varietyName);
				varietyDao.save(variety);
				if (cropName.equals("水稻")) {
					// get all subStageIds
					SubStageDao subStageDao = (SubStageDao) ContextLoader.getCurrentWebApplicationContext()
							.getBean("subStageDao");
					String hql = "from SubStage";
					List list = subStageDao.getHibernateTemplate().find(hql);
					for (int j = 0; j < list.size(); j++) {
						SubStage subStage = (SubStage) list.get(j);
						subStage.getVarieties().add(variety);
						subStageDao.update(subStage);
					}
				}

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

	public void processBlock(ArrayList<String> block) {
		// LINE 1: cropName and generate crop
		String lineSegments[] = block.get(0).split(SECTIONNAME_SEPARATOR, 2);
		String cropName = lineSegments[lineSegments.length - 1].trim();
		// LINE 3: registerationID
		lineSegments = block.get(2).split(SECTIONNAME_SEPARATOR, 2);
		String registerationID = lineSegments[lineSegments.length - 1].trim();
		variety_cropMap.put(registerationID, cropName);
	}

}
