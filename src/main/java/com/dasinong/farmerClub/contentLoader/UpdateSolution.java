package com.dasinong.farmerClub.contentLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.context.ContextLoader;

import au.com.bytecode.opencsv.CSVReader;

import com.dasinong.farmerClub.dao.ICPProductDao;
import com.dasinong.farmerClub.dao.IPetDisSpecDao;
import com.dasinong.farmerClub.dao.IPetSoluDao;
import com.dasinong.farmerClub.model.CPProduct;
import com.dasinong.farmerClub.model.PetDisSpec;
import com.dasinong.farmerClub.model.PetSolu;

public class UpdateSolution {
	public final static File FILE = new File("/Users/jiachengwu/Documents/sourcefiles/petSolu.csv");

	public void run() {
		IPetDisSpecDao petDisSpecDao = (IPetDisSpecDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("petDisSpecDao");
		IPetSoluDao petSoluDao = (IPetSoluDao) ContextLoader.getCurrentWebApplicationContext().getBean("petSoluDao");
		ICPProductDao cPProductDao = (ICPProductDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("cPProductDao");
		try {
			FileInputStream fr = new FileInputStream(FILE);
			CSVReader reader = new CSVReader(new InputStreamReader(fr, "UTF-8"), ',', '\"', 1);
			List entries = reader.readAll();
			for (int i = 0; i < entries.size(); i++) {
				// create a subStage object for each entry
				String items[] = (String[]) entries.get(i);
				String petDisSpecName = items[5];
				String petSoluDes = items[1] + "：" + items[3];
				PetDisSpec petDisSpec = petDisSpecDao.findByNameAndCrop(petDisSpecName, "水稻");
				if (petDisSpec != null) {
					Set<PetSolu> petSolus = petDisSpec.getPetSolus();
					for (PetSolu petSolu : petSolus) {
						if (petSolu.getPetSoluDes().equals(petSoluDes)) {
							Set<CPProduct> cPProducts = new HashSet<CPProduct>();
							String subStageId = items[6];
							petSolu.setSubStageId(subStageId);
							Set<String> cPProductSet = new HashSet<String>();
							for (int j = 9; j < items.length; j++) {
								String registerationId = items[j].trim();
								if (!cPProductSet.contains(registerationId)) {
									if (!registerationId.isEmpty()) {
										CPProduct cPProduct = cPProductDao.findByRegisterationId(registerationId);
										if (cPProduct != null) {
											cPProducts.add(cPProduct);
										} else {
											System.out.println(petSolu.getPetSoluId() + "," + registerationId);
										}
									}
									cPProductSet.add(registerationId);
								}
							}
							petSolu.setcPProducts(cPProducts);
							petSoluDao.update(petSolu);
						}
					}
				} else {
					System.out.println(petDisSpecName);
				}
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void test() {
		IPetDisSpecDao petDisSpecDao = (IPetDisSpecDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("petDisSpecDao");
		IPetSoluDao petSoluDao = (IPetSoluDao) ContextLoader.getCurrentWebApplicationContext().getBean("petSoluDao");

		// test on 稻瘟病
		// find petDisSpec whose name is 稻瘟病
		PetDisSpec petDisSpec = petDisSpecDao.findByNameAndCrop("稻瘟病", "水稻");
		Set<PetSolu> petSolus = petDisSpec.getPetSolus();
		// 种子处理：播种前用40％多菌灵可湿性粉剂、70％甲基硫菌灵可湿性粉剂、40％异稻瘟净乳油浸种，早稻用1000倍药液浸种48～72小时，晚稻用500倍药液浸种24小时。

		String petSoluDes = "种子处理：播种前用40％多菌灵可湿性粉剂、70％甲基硫菌灵可湿性粉剂、40％异稻瘟净乳油浸种，早稻用1000倍药液浸种48～72小时，晚稻用500倍药液浸种24小时。";
		for (PetSolu petSolu : petSolus) {
			if (petSolu.getPetSoluDes().equals(petSoluDes)) {
				System.out.println(petSolu.getPetSoluId());
			}
		}
	}

}
