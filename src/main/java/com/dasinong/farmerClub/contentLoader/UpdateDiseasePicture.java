package com.dasinong.farmerClub.contentLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.PetDisSpecDao;
import com.dasinong.farmerClub.dao.SubStageDao;
import com.dasinong.farmerClub.model.PetDisSpec;
import com.dasinong.farmerClub.model.SubStage;

public class UpdateDiseasePicture {
	public final File FILE = new File("/Users/jiachengwu/Documents/sourcefiles/UserFiles/bingchonghai_pic.txt");
	public final File FOLDER = new File("/Users/jiachengwu/Documents/sourcefiles/病虫草害补图/");
	public final String BLOCKSEPARATOR = "--------------------";
	public Map<String, String> pictureList = new HashMap<String, String>();

	public void run() {
		// update1();
		// update2();
		clean2();
	}

	@Test
	public void test() {
		String string = "UserFiles/FckFiles/20111125/2011-11-25-10-46-32-5842.jpg\n"
				+ "UserFiles/FckFiles/20111125/2011-11-25-10-46-34-2251.jpg\n"
				+ "UserFiles/FckFiles/20111125/2011-11-25-10-46-34-6627.jpg\n"
				+ "UserFiles/FckFiles/20111125/2011-11-25-10-46-34-9908.jpg";
		Boolean flag = string.contains("UserFiles");
		if (string.contains("UserFiles")) {
			string = string.replaceAll("UserFiles/FckFiles", "allpet1");
			System.out.println(string);
		}
	}

	public void clean2() {
		// get all petDisSpec
		PetDisSpecDao petDisSpecDao = (PetDisSpecDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("petDisSpecDao");
		String hql = "from PetDisSpec";
		List list = petDisSpecDao.getHibernateTemplate().find(hql);
		for (int j = 0; j < list.size(); j++) {
			PetDisSpec petDisSpec = (PetDisSpec) list.get(j);
			String picture = petDisSpec.getPictureIds();

			if (picture.contains("UserFiles")) {
				picture = picture.replaceAll("UserFiles/FckFiles", "allpet1");
				petDisSpec.setPictureIds(picture);
			}

			petDisSpecDao.update(petDisSpec);

		}
	}

	public void clean() {
		// get all petDisSpec
		PetDisSpecDao petDisSpecDao = (PetDisSpecDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("petDisSpecDao");
		String hql = "from PetDisSpec";
		List list = petDisSpecDao.getHibernateTemplate().find(hql);
		for (int j = 0; j < list.size(); j++) {
			PetDisSpec petDisSpec = (PetDisSpec) list.get(j);
			String picture = petDisSpec.getPictureIds();
			if (picture.equals("")) {
				String type = petDisSpec.getType();
				if (type.contains("病害")) {
					petDisSpec.setPictureIds("defaultDisease.jpg");
				} else if (type.contains("虫害")) {
					petDisSpec.setPictureIds("defaultPest.jpg");
				} else {
					petDisSpec.setPictureIds("defaultWeeds.jpg");
				}
			}

			if (picture.contains("UploadFiles")) {
				ArrayList<String> pictureIds = new ArrayList<String>();
				String lines[] = picture.split("\n");
				for (int i = 0; i < lines.length; i++) {
					String line = lines[i];
					if (!line.trim().equals("")) {
						String items[] = line.split("\\s");
						pictureIds.add(items[0]);
					}
				}
				picture = "";
				for (int i = 0; i < pictureIds.size() - 1; i++) {
					picture = picture + pictureIds.get(i) + "\n";
				}
				picture = picture + pictureIds.get(pictureIds.size() - 1);
				petDisSpec.setPictureIds(picture);
			} else if (picture.contains(",")) {
				picture.replace(",", "\n");
				petDisSpec.setPictureIds(picture);
			}

			petDisSpecDao.update(petDisSpec);

		}
	}

	public void update2() {
		File[] files = FOLDER.listFiles();
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			String[] items = fileName.split("\\d*\\..*");
			String cropName = items[0];
			if (pictureList.containsKey(cropName)) {
				String picture = pictureList.get(cropName);
				picture = picture + "\n" + "病虫草害补图/" + fileName;
				pictureList.put(cropName, picture);
			} else {
				String picture = "病虫草害补图/" + fileName;
				pictureList.put(cropName, picture);
			}
		}
		PetDisSpecDao petDisSpecDao = (PetDisSpecDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("petDisSpecDao");
		for (String keyName : pictureList.keySet()) {
			PetDisSpec petDisSpec = petDisSpecDao.findByNameAndCrop(keyName, "水稻");
			petDisSpec.setPictureIds(pictureList.get(keyName));
			petDisSpecDao.update(petDisSpec);
		}
	}

	public void update1() {
		ArrayList<ArrayList<String>> blocks = LoadFileUtil.generateBlocks(FILE, BLOCKSEPARATOR);
		for (int i = 0; i < blocks.size(); i++) {
			processBlock(blocks.get(i));
		}
		// pictureList map constructed

		// get all petDisSpec
		PetDisSpecDao petDisSpecDao = (PetDisSpecDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("petDisSpecDao");
		String hql = "from PetDisSpec";
		List list = petDisSpecDao.getHibernateTemplate().find(hql);
		for (int j = 0; j < list.size(); j++) {
			PetDisSpec petDisSpec = (PetDisSpec) list.get(j);
			String keyName = match(petDisSpec.getPetDisSpecName());
			if (keyName != null) {
				String picture = pictureList.get(keyName);
				if (!picture.trim().equals("")) {
					petDisSpec.setPictureIds(picture);
					petDisSpecDao.update(petDisSpec);
				}
			}
		}

	}

	public String match(String petDisSpecName) {
		String name = null;
		for (String pictureName : pictureList.keySet()) {
			if (pictureName.contains(petDisSpecName))
				return pictureName;
		}

		return name;
	}

	public void processBlock(ArrayList<String> block) {
		String name = block.get(0).trim();
		String picture = "";
		for (int i = 1; i < block.size() - 1; i++) {
			String line = block.get(i);
			String items[] = line.split("http://www.agropages.com/", 2);
			if (items.length == 2) {
				if (picture.trim().isEmpty()) {
					picture = picture + items[1];
				} else {
					picture += "\n" + items[1];
				}
			}
		}
		pictureList.put(name, picture);
	}
}
