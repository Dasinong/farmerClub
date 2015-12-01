package com.dasinong.farmerClub.contentLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.contentLoader.LoadFileUtil;
import com.dasinong.farmerClub.dao.ICropDao;
import com.dasinong.farmerClub.dao.IVarietyDao;
import com.dasinong.farmerClub.dao.VarietyDao;
import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.model.Variety;

public class LoadVariety {
	public final static String BLOCK_SEPARATOR = "--------------------";
	public final static String SECTIONNAME_SEPARATOR = ":|：";
	public ClassLoader classLoader = getClass().getClassLoader();
	// public final File FILEFOLDER = new
	// File(classLoader.getResource("sourcefiles").getFile());
	public final File FILEFOLDER = new File(new File("E:/git/PloughHelper/").getAbsoluteFile(), "sourcefiles");
	public final String FILENAME = "种子库.txt";
	public final File FILE = new File(FILEFOLDER, FILENAME);
	public final String SAMPLE_FILENAME = "种子库_sample.txt";
	public final File SAMPLE_FILE = new File(FILEFOLDER, SAMPLE_FILENAME);
	public final String LOG_FILENAME = "种子库_log.txt";
	public final File LOG_FILE = new File(FILEFOLDER, LOG_FILENAME);
	// public Set<String> cropNames = new HashSet<String>();
	public Map<String, Crop> cropMap = new HashMap<String, Crop>();

	public void testOnSample() throws IOException {
		boolean onSample = false;
		File file;
		if (onSample) {
			file = SAMPLE_FILE;
		} else {
			file = FILE;
		}
		FileWriter fw = new FileWriter(LOG_FILE);
		BufferedWriter bw = new BufferedWriter(fw);
		ArrayList<ArrayList<String>> blocks = LoadFileUtil.generateBlocks(file, BLOCK_SEPARATOR);
		for (ArrayList<String> block : blocks) {
			Variety variety = processBlock(block);
			// bw.write(variety.getVarietyName()+" "+variety.getSubId()+"\n");
			// if (variety.getCropName().length()>30) {
			// System.out.println(1);
			// System.out.println(variety.getCropName());
			// }
		}
		for (String cropName : cropMap.keySet()) {
			Crop crop = cropMap.get(cropName);
			bw.write("cropName: " + cropName + "\n");
		}
		bw.flush();
		bw.close();
		fw.close();
	}

	public void test() {
		updateFullCycleDuration();
	}

	public void updateFullCycleDuration() {
		// query from the database to get all varieties, and for each variety,
		// extract fullCycleDuration information from the characteristics column
		VarietyDao varietyDao = (VarietyDao) ContextLoader.getCurrentWebApplicationContext().getBean("varietyDao");

		String hql = "from Variety";
		List list = varietyDao.getHibernateTemplate().find(hql);

		for (int i = 0; i < list.size(); i++) {
			Variety variety = (Variety) list.get(i);
			String characteristics = variety.getCharacteristics();
			variety.setFullCycleDuration(parseFullCycleDuration(characteristics));
			varietyDao.update(variety);
		}

	}

	public double parseFullCycleDuration(String characteristics) {
		double fullCycleDuration = 0;
		String regex1 = "(全?生[\\D]{1,10}(\\d[^天]{1,10})天)";
		String regex2 = "(播.{0,10}(\\d[^天]{1,10})天)";
		String regex = regex1 + "|" + regex2;
		Pattern p1 = Pattern.compile(regex);
		Matcher m1 = p1.matcher(characteristics);
		String output = "";
		if (m1.find()) {

			// Extract all numbers from the output
			ArrayList<Double> days = new ArrayList<Double>();
			output = m1.group(0);
			regex = "\\d+(\\.\\d*)?";
			Pattern p2 = Pattern.compile(regex);
			Matcher m2 = p2.matcher(output);
			while (m2.find()) {
				days.add(Double.parseDouble(m2.group()));
			}
			if (days.size() == 1) {
				fullCycleDuration = days.get(0);
			} else if (days.size() == 2) {
				if (Math.abs((days.get(0) - days.get(1))) / days.get(0) < 0.35) {
					fullCycleDuration = (days.get(0) + days.get(1)) / 2;
				} else {
					fullCycleDuration = Math.max(days.get(0), days.get(1));
				}
			}
		}
		return fullCycleDuration;
	}

	public void readFile(File file) throws IOException {
		IVarietyDao varietyDao = (IVarietyDao) ContextLoader.getCurrentWebApplicationContext().getBean("varietyDao");
		ICropDao cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");

		ArrayList<Integer> errorIndex = new ArrayList<Integer>();
		ArrayList<ArrayList<String>> blocks = LoadFileUtil.generateBlocks(file, BLOCK_SEPARATOR);
		ArrayList<Variety> varieties = new ArrayList<Variety>();
		cropMap = new HashMap<String, Crop>();

		for (int i = 0; i < blocks.size(); i++) {
			ArrayList<String> block = blocks.get(i);
			varieties.add(processBlock(block));
		}

		FileWriter fw = new FileWriter(LOG_FILE);
		BufferedWriter bw = new BufferedWriter(fw);

		// cropMap 映射完毕，往crop表写入crop值
		for (String cropName : cropMap.keySet()) {
			Crop crop = cropMap.get(cropName);
			try {
				cropDao.save(crop);
			} catch (Exception e) {
				bw.write("写入Crop表错误，cropName为： " + cropName + "\n");
			}
		}

		for (int i = 0; i < varieties.size(); i++) {
			Variety variety = varieties.get(i);
			Crop crop = cropMap.get(variety.getCropName());
			variety.setCrop(crop);
			try {
				varietyDao.save(variety);
			} catch (Exception e) {
				bw.write("写入Crop表错误，variety序号为： " + i + "   注册号为: " + variety.getRegisterationId() + "\n");
			}
		}
		bw.flush();
		bw.close();
		fw.close();
	}

	public void readFile() throws IOException {
		readFile(FILE);
	}

	public Variety processBlock(ArrayList<String> block) {
		Variety variety = new Variety();
		// ########################### Write in information from source file
		// first, lossless transfer #################################
		// LINE 1: cropName and generate crop
		String lineSegments[] = block.get(0).split(SECTIONNAME_SEPARATOR, 2);
		String cropName = lineSegments[lineSegments.length - 1].trim();
		variety.setCropName(cropName);
		Crop crop = new Crop(cropName);
		cropMap.put(cropName, crop); // 加入到cropMap 并没有ref给variety
		// LINE 2: varietyName
		lineSegments = block.get(1).split(SECTIONNAME_SEPARATOR, 2);
		String varietyName = lineSegments[lineSegments.length - 1].trim();
		String varietyNames[] = processVarietyName(varietyName);
		variety.setVarietyName(varietyNames[0]);
		variety.setSubId(varietyNames[1]);
		// LINE 3: registerationID
		lineSegments = block.get(2).split(SECTIONNAME_SEPARATOR, 2);
		String registerationID = lineSegments[lineSegments.length - 1].trim();
		variety.setRegisterationId(registerationID);
		// LINE 4: yearOfRegisteration
		lineSegments = block.get(3).split(SECTIONNAME_SEPARATOR, 2);
		String yearOfRegisteration = lineSegments[lineSegments.length - 1].trim();
		if (!yearOfRegisteration.equals("")) {
			variety.setYearofReg(Integer.parseInt(yearOfRegisteration));
		}
		// LINE 5: issuedBy
		lineSegments = block.get(4).split(SECTIONNAME_SEPARATOR, 2);
		String issuedBy = lineSegments[lineSegments.length - 1].trim();
		variety.setIssuedBy(issuedBy);
		// LINE 6: createdBy
		lineSegments = block.get(5).split(SECTIONNAME_SEPARATOR, 2);
		String owner = lineSegments[lineSegments.length - 1].trim();
		variety.setOwner(owner);
		// LINE 7: varietySource
		lineSegments = block.get(6).split(SECTIONNAME_SEPARATOR, 2);
		String varietySource = lineSegments[lineSegments.length - 1].trim();
		variety.setVarietySource(varietySource);
		// LINE 8: isTransgenic
		lineSegments = block.get(7).split(SECTIONNAME_SEPARATOR, 2);
		String transgenic = lineSegments[lineSegments.length - 1].trim();
		variety.setIsTransgenic(transgenic.equals("是转基因"));
		// LINE 9: characteristics
		lineSegments = block.get(8).split(SECTIONNAME_SEPARATOR, 2);
		String characteristics = lineSegments[lineSegments.length - 1].trim();
		variety.setCharacteristics(characteristics);
		// LINE 10: yieldPerformance
		lineSegments = block.get(9).split(SECTIONNAME_SEPARATOR, 2);
		String yieldPerformance = lineSegments[lineSegments.length - 1].trim();
		variety.setYieldPerformance(yieldPerformance);
		// LINE 11: suitableArea
		lineSegments = block.get(10).split(SECTIONNAME_SEPARATOR, 2);
		String suitableArea = lineSegments[lineSegments.length - 1].trim();
		variety.setSuitableArea(suitableArea);

		// ######################################## Begin to derive entries
		// based on the source #######################################

		variety.setFullCycleDuration(parseFullCycleDuration(characteristics));

		return variety;
	}

	public String[] processVarietyName(String varietyName) {
		// get rid of the spaces
		varietyName.replaceAll("\\s", "");
		String varietyNames[] = new String[2];
		final String CHINESE_CHARACTERS = "[\\u4e00-\\u9fa5]";
		final String CHINESE_NUMBERS = "((一|二|三|四|五|六|七|八|九|十)+号)";
		// final String CHINESE_NON_NUMBERS =
		// "(["+CHINESE_CHARACTERS+"&&[^"+CHINESE_NUMBERS+"]])"; // 用中文非数字来分割
		String regex = "^(\\w*" + CHINESE_CHARACTERS + "+)(\\w|" + CHINESE_NUMBERS + ")";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(varietyName);
		if (!m.find()) {
			varietyNames[0] = varietyName;
			varietyNames[1] = varietyName;
		} else {
			varietyNames[0] = m.group(1);
			varietyNames[1] = varietyName.substring(m.end(1));
		}
		return varietyNames;
	}

}
