package com.dasinong.farmerClub.contentLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javassist.expr.NewArray;

import org.junit.internal.matchers.SubstringMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import au.com.bytecode.opencsv.CSVReader;

import com.dasinong.farmerClub.dao.ICPProductDao;
import com.dasinong.farmerClub.dao.IPetDisSpecDao;
import com.dasinong.farmerClub.dao.IPetSoluDao;
import com.dasinong.farmerClub.dao.ISubStageDao;
import com.dasinong.farmerClub.model.CPProduct;
import com.dasinong.farmerClub.model.PetDisSpec;
import com.dasinong.farmerClub.model.PetSolu;
import com.dasinong.farmerClub.model.SubStage;

public class UpdateDisease implements IUpdateDisease {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dasinong.farmerClub.contentLoader.IUpdateDisease#run()
	 */
	@Override
	@Transactional
	public void run() {
		IPetDisSpecDao petDisSpecDao = (IPetDisSpecDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("petDisSpecDao");
		IPetSoluDao petSoluDao = (IPetSoluDao) ContextLoader.getCurrentWebApplicationContext().getBean("petSoluDao");
		ISubStageDao subStageDao = (ISubStageDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subStageDao");
		ArrayList<SubStage> subStages = new ArrayList<SubStage>();
		for (int i = 0; i < 25; i++) {
			SubStage subStage = subStageDao.findById((long) (i + 35));
			subStages.add(i, subStage);
		}
		try {
			FileInputStream fr = new FileInputStream(FILE);
			CSVReader reader = new CSVReader(new InputStreamReader(fr, "UTF-8"), ',', '\"', 1);
			List entries = reader.readAll();
			for (int i = 0; i < entries.size(); i++) {
				// create a subStage object for each entry
				String items[] = (String[]) entries.get(i);
				String petDisSpecName = items[2].trim();
				PetDisSpec petDisSpec = petDisSpecDao.findByNameAndCrop(petDisSpecName, "水稻");
				if (petDisSpec != null) {
					String locationString = items[4].trim();
					petDisSpec.setRegion(locationString);
					String subStageString = items[6].trim();
					Set<Integer> subStageIdSet = processSubStageIds(subStageString);
					if (!subStageIdSet.isEmpty()) {
						for (Integer subStageId : subStageIdSet) {
							SubStage subStage = subStages.get(subStageId - 1);
							subStage.getPetDisSpecs().add(petDisSpec);
						}
					}
					petDisSpecDao.update(petDisSpec);
				} else {
					System.out.println(petDisSpecName);
				}
			}

			for (int i = 0; i < subStages.size(); i++) {
				SubStage subStage = subStages.get(i);
				subStageDao.update(subStage);
			}

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.contentLoader.IUpdateDisease#processSubStageIds
	 * (java.lang.String)
	 */
	@Override
	public Set<Integer> processSubStageIds(String subStageString) {
		Set<Integer> subStageIdSet = new HashSet<Integer>();
		if (!subStageString.trim().isEmpty()) {
			String[] items = subStageString.split("，");
			for (int i = 0; i < items.length; i++) {
				String[] ends = items[i].split("~");
				if (ends.length == 1) {
					int id = Integer.parseInt(ends[0]);
					subStageIdSet.add(id);
				} else {
					int idStart = Integer.parseInt(ends[0]);
					int idEnd = Integer.parseInt(ends[1]);
					for (int j = idStart; j < idEnd + 1; j++) {
						subStageIdSet.add(j);
					}
				}
			}

		}

		return subStageIdSet;
	}
}
