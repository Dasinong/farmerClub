package com.dasinong.farmerClub.contentLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.context.ContextLoader;

import au.com.bytecode.opencsv.CSVReader;

import com.dasinong.farmerClub.dao.IPetDisSpecDao;
import com.dasinong.farmerClub.dao.IPetSoluDao;
import com.dasinong.farmerClub.model.PetDisSpec;
import com.dasinong.farmerClub.model.PetSolu;

public class LoadDiseaseAndSolution {
	public final static String BLOCK_SEPARATOR = "--------------------";
	public final File FILEFOLDER = new File(new File("C:/Users/Jason Wu/workspace/PloughHelper").getAbsoluteFile(),
			"sourcefiles");
	public final File FILE_PEST = new File(FILEFOLDER, "bingchonghai_all.txt");
	public final File FILE_WEED = new File(FILEFOLDER, "bingchonghai3.txt");
	public final File FILE_DISUPDATE = new File(FILEFOLDER, "petDisSpec.csv");
	public final File FILE_SOLUUPDATE = new File(FILEFOLDER, "petSolu.csv");

	public Map<String, PetDisSpec> petDisSpecMap = new LinkedHashMap<String, PetDisSpec>();
	public Map<String, PetDisSpec> updateMap = new HashMap<String, PetDisSpec>();

	public void run() {

		readPestFile();
		readWeedFile();
		// petDisSpecMap construction DONE! Next, try to update 水稻 entries
		readUpdateFile();
		// updateMap construction DONE!
		for (String key : updateMap.keySet()) {
			PetDisSpec updatedPetDisSpec = updateMap.get(key);
			if (petDisSpecMap.containsKey(key)) {
				PetDisSpec originalPetDisSpec = petDisSpecMap.get(key);
				originalPetDisSpec.setSeverity(updatedPetDisSpec.getSeverity());
				originalPetDisSpec.setCommonImpactonYield(updatedPetDisSpec.getCommonImpactonYield());
				originalPetDisSpec.setMaxImpactonYield(updatedPetDisSpec.getMaxImpactonYield());
				originalPetDisSpec.setPreventionorRemedy(updatedPetDisSpec.getPreventionorRemedy());
				originalPetDisSpec.setIndvidualorGroup(updatedPetDisSpec.getIndvidualorGroup());
				originalPetDisSpec.setImpactedArea(updatedPetDisSpec.getImpactedArea());
				originalPetDisSpec.setColor(updatedPetDisSpec.getColor());
				originalPetDisSpec.setShape(updatedPetDisSpec.getShape());
				originalPetDisSpec.setDescription(updatedPetDisSpec.getDescription());
				if (originalPetDisSpec.getType().equals("病害") || originalPetDisSpec.getType().equals("虫害")) {
					Set<PetSolu> petSolus = updatedPetDisSpec.getPetSolus();
					for (PetSolu petSolu : petSolus) {
						petSolu.setPetDisSpec(originalPetDisSpec);
					}
					originalPetDisSpec.setPetSolus(petSolus);
				} else {
					Set<PetSolu> petSolus = updatedPetDisSpec.getPetSolus();
					for (PetSolu petSolu : petSolus) {
						petSolu.setPetDisSpec(originalPetDisSpec);
					}
					originalPetDisSpec.getPetSolus().addAll(petSolus);
				}
			} else {
				petDisSpecMap.put(key, updatedPetDisSpec);
			}
		}

		// updates DONE! Next, write into database
		IPetDisSpecDao petDisSpecDao = (IPetDisSpecDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("petDisSpecDao");
		// IPetSoluDao petSoluDao = (IPetSoluDao)
		// ContextLoader.getCurrentWebApplicationContext().getBean("petSoluDao");

		Iterator<PetDisSpec> it = petDisSpecMap.values().iterator();
		while (it.hasNext()) {
			PetDisSpec petDisSpec = (PetDisSpec) it.next();
			petDisSpecDao.save(petDisSpec);
		}
	}

	public void readUpdateFile() {
		try {
			FileInputStream fr = new FileInputStream(FILE_DISUPDATE);
			CSVReader reader = new CSVReader(new InputStreamReader(fr, "UTF-8"), ',', '\"', 1);
			List entries = reader.readAll();
			for (int i = 0; i < entries.size(); i++) {
				// create a subStage object for each entry
				String items[] = (String[]) entries.get(i);
				String key = items[2].trim() + "," + "水稻";
				PetDisSpec petDisSpec = new PetDisSpec(items[2]);
				petDisSpec.setSeverity(items[7].trim().length());
				petDisSpec.setCommonImpactonYield(items[8].trim());
				petDisSpec.setMaxImpactonYield(items[9].trim());
				petDisSpec.setPreventionorRemedy(items[10].trim());
				petDisSpec.setIndvidualorGroup(items[11].trim());
				petDisSpec.setImpactedArea(items[12].trim());
				petDisSpec.setColor(items[13].trim());
				petDisSpec.setShape(items[14].trim());
				petDisSpec.setDescription(items[15].trim());
				updateMap.put(key, petDisSpec);
			}
			reader.close();

			fr = new FileInputStream(FILE_SOLUUPDATE);
			reader = new CSVReader(new InputStreamReader(fr, "UTF-8"), ',', '\"', 1);
			entries = reader.readAll();
			for (int i = 0; i < entries.size(); i++) {
				// create a subStage object for each entry
				String items[] = (String[]) entries.get(i);
				String key = items[5].trim() + "," + "水稻";
				if (updateMap.containsKey(key)) {
					PetDisSpec petDisSpec = updateMap.get(key);
					PetSolu petSolu = new PetSolu(items[1] + "：" + items[3], petDisSpec, true, true);
					petSolu.setProvidedBy(items[2].trim());
					petSolu.setIsCPSolu(items[7].trim().equals("1"));
					petSolu.setRank(Integer.parseInt(items[8].trim()));
					petDisSpec.getPetSolus().add(petSolu);
				}
			}
			reader.close();

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static enum Categories {
		description, sympton, form, habbits, rule, solution
	}

	public void readWeedFile() {
		readWeedFile(FILE_WEED);
	}

	public void readWeedFile(File file) {
		ArrayList<ArrayList<String>> blocks = LoadFileUtil.generateBlocks(file, BLOCK_SEPARATOR);

		for (int i = 0; i < blocks.size(); i++) {
			ArrayList<String> block = blocks.get(i);
			ArrayList<PetDisSpec> petDisSpecs = processBlock(block);
			for (int j = 0; j < petDisSpecs.size(); j++) {
				PetDisSpec petDisSpec = petDisSpecs.get(j);
				String petDisSpecKey = petDisSpec.getPetDisSpecName() + "," + petDisSpec.getCropName();
				if (!petDisSpecMap.containsKey(petDisSpecKey)) {
					petDisSpecMap.put(petDisSpecKey, petDisSpec);
				}
			}
		}
	}

	public ArrayList<PetDisSpec> processBlock(ArrayList<String> lines) {
		ArrayList<PetDisSpec> petDisSpecs = new ArrayList<PetDisSpec>();
		// For the next block of text, get cropNames
		String cropNames[] = processCropNames(lines).split(",");

		for (int j = 0; j < cropNames.length; j++) {
			PetDisSpec petDisSpec = new PetDisSpec();
			Set<PetSolu> petSolus = new HashSet<PetSolu>();
			petDisSpec.setCropName(cropNames[j]);

			// Get name from line 1
			petDisSpec.setPetDisSpecName(lines.get(0).trim());
			// Base type is automatic 草害
			petDisSpec.setType("草害");
			// TODO: 抓取属xx科 Y年生...植物

			// Get 防治农药 from line 2
			String fzny[] = lines.get(2).split(":", 2);
			String fznyContent = fzny[1].trim();

			if (!fznyContent.equals("")) { // 若非空 加一条防治方法
				String words[] = fznyContent.split("\\s");
				String cPSolution = "使用含以下成分的农药: ";
				for (String string : words)
					cPSolution += string + ",";
				// 之后生成一个solution叫做 使用一下成分农药: fznyContent. 注意 fznyContent可能为空
				petSolus.add(new PetSolu(cPSolution.replaceAll(",$", ""), petDisSpec, true, true));
			}

			// process the bottomBlock of text
			ArrayList<String> bottomBlock = generateBottomBlock(lines);

			for (String line : bottomBlock) {
				Set<Categories> categories = keywordCatgorization(generateSectionWords(line));

				for (Categories category : categories) {
					switch (category) {
					case description:
						petDisSpec.setDescription(line);
						// analyse description for subType
						String subType = analyseSubtype(line);
						if (!subType.trim().equals(""))
							petDisSpec.setType(petDisSpec.getType() + " " + subType);
						break;
					case sympton:
						petDisSpec.setSympthon(line);
						break;
					case form:
						petDisSpec.setForms(line);
						break;
					case habbits:
						petDisSpec.setHabits(line);
						break;
					case rule:
						petDisSpec.setRules(line);
						break;
					case solution:
						// 生成一条或者多条petSolu
						ArrayList<String> solutions = generationSoluDes(line);
						for (int i = 0; i < solutions.size(); i++) {
							PetSolu petSolu = new PetSolu(solutions.get(i), petDisSpec, true, true);
							petSolu.setIsCPSolu(isCPSolu(petSolu));
							petSolus.add(petSolu);
						}
						break;
					default:
						break;
					}
				}
			}
			petDisSpec.setPetSolus(petSolus);
			petDisSpecs.add(petDisSpec);
		}

		return petDisSpecs;
	}

	public String analyseSubtype(String line) {
		// 提取字段"属xxxx。"
		String subType = "";
		String regex = "(属([^。]+))。";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(line);
		if (m.find()) {
			subType = m.group(1);
		} else {
			subType = "";
		}
		// 提取 "生物学特性xxxxx，|。"
		regex = "生物学特性([^，。]+)[，。]";
		p = Pattern.compile(regex);
		m = p.matcher(line);
		if (m.find()) {
			subType = m.group(1);
		}
		return subType;
	}

	public ArrayList<String> generationSoluDes(String line) {
		ArrayList<String> petSoluDes = new ArrayList<String>();
		String keyword = generateSectionWords(line);
		String content = generateSectionContent(line);
		if (keyword.equals("防治方法")) {
			String regex = "\\(\\d\\)|\\d、|（\\d）|\\d\\.[\\D]{1}";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(content);
			ArrayList<String> items = new ArrayList<String>();
			ArrayList<Integer> startingPosi = new ArrayList<Integer>();
			while (m.find()) {
				startingPosi.add(m.start());
			}
			if (startingPosi.size() > 0) {
				for (int i = 0; i < startingPosi.size() - 1; i++) {
					String string = content.substring(startingPosi.get(i), startingPosi.get(i + 1));
					items.add(string.replaceAll("^。", ""));
				}
				items.add(content.substring(startingPosi.get(startingPosi.size() - 1)).replaceAll("^。", ""));
				petSoluDes.addAll(items);
			} else { // 如果上面只split出来一条记录，则进一步split
				regex = "。?([^。]+?)：";
				p = Pattern.compile(regex);
				m = p.matcher(content);
				items = new ArrayList<String>();
				startingPosi = new ArrayList<Integer>();
				while (m.find()) {
					startingPosi.add(m.start());
				}
				if (startingPosi.size() > 0) {
					for (int i = 0; i < startingPosi.size() - 1; i++) {
						String string = content.substring(startingPosi.get(i), startingPosi.get(i + 1) + 1);
						items.add(string.replaceAll("^。", ""));
					}
					items.add(content.substring(startingPosi.get(startingPosi.size() - 1)).replaceAll("^。", ""));
					petSoluDes.addAll(items);
				} else {
					petSoluDes.add(line);
				}

			}
		} else if (keyword.equals("防治措施")) {
			String regex = "\\d、";
			String solutions[] = content.replaceFirst("^" + regex, "").split(regex);
			petSoluDes.addAll(Arrays.asList(solutions));
		} else if (keyword.equals("综合治理策略")) {
			String regex = "（.?）";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(content);
			ArrayList<String> items = new ArrayList<String>();
			ArrayList<Integer> startingPosi = new ArrayList<Integer>();
			while (m.find()) {
				startingPosi.add(m.start());
			}

			if (startingPosi.size() > 0) {
				for (int i = 0; i < startingPosi.size() - 1; i++) {
					String string = content.substring(startingPosi.get(i), startingPosi.get(i + 1));
					items.add(string.replaceAll("^。", ""));
				}
				items.add(content.substring(startingPosi.get(startingPosi.size() - 1)).replaceAll("^。", ""));
				petSoluDes.addAll(items);
			} else {
				petSoluDes.add(line);
			}
		} else {
			petSoluDes.add(line);
		}

		return petSoluDes;
	}

	public Set<Categories> keywordCatgorization(String keyword) {
		Set<Categories> categories = new HashSet<Categories>();

		String descriptionKeywords[] = { "", "学名", "病原" };
		Set<String> descriptionSet = new HashSet<String>(Arrays.asList(descriptionKeywords));
		if (descriptionSet.contains(keyword)) {
			categories.add(Categories.description);
		}

		String symptonKeywords[] = { "症状", "危害", "主要危害" };
		Set<String> symptonSet = new HashSet<String>(Arrays.asList(symptonKeywords));
		if (symptonSet.contains(keyword)) {
			categories.add(Categories.sympton);
		}

		String ruleKeywords[] = { "生境、危害与分布", "形态特征与分布", "生态特点", "传播途径和发病条件" };
		Set<String> ruleSet = new HashSet<String>(Arrays.asList(ruleKeywords));
		if (ruleSet.contains(keyword)) {
			categories.add(Categories.habbits);
			categories.add(Categories.rule);
		}

		String formKeywords[] = { "形态特征与分布", "病原", "形态特征" };
		Set<String> formSet = new HashSet<String>(Arrays.asList(formKeywords));
		if (formSet.contains(keyword)) {
			categories.add(Categories.form);
		}

		// 如果keyword里面含有关键字"治"或"防",则属于防治类
		if (keyword.contains("治") || keyword.contains("防")) {
			categories.add(Categories.solution);
		}

		return categories;
	}

	public String generateSectionWords(String line) {
		String segments[] = line.split(":");
		String keyword = segments[0].trim();
		return keyword;
	}

	public String generateSectionContent(String line) {
		String segments[] = line.split(":");
		String content = segments[segments.length - 1].trim();
		return content;
	}

	public String processCropNames(ArrayList<String> lines) {
		String cropNames = "";
		for (int i = 3; i < lines.size(); i++) {
			String line = lines.get(i);
			if (line.trim().equals("")) {
				break;
			}
			String items[] = line.split("---");
			String words[] = items[1].trim().split("\\s");
			for (String string : words) {
				cropNames += string + ",";
			}
		}
		// get rid of the last ","
		return cropNames.replaceAll(",$", "");
	}

	public ArrayList<String> generateBottomBlock(ArrayList<String> lines) {
		ArrayList<String> bottomBlock = new ArrayList<String>();
		int startingRow = lines.size();
		for (int i = 3; i < lines.size(); i++) {
			String line = lines.get(i);

			if (line.trim().equals("")) {
				startingRow = i + 1;
				break;
			}
		}
		for (int i = startingRow; i < lines.size(); i++) {
			String line = lines.get(i);
			if (!line.trim().equals("")) { // get rid of empty lines
				bottomBlock.add(line);
			}
		}

		return bottomBlock;
	}

	public void readPestFile() {
		readPestFile(FILE_PEST);
	}

	public void readPestFile(File file) {
		IPetDisSpecDao petDisSpecDao = (IPetDisSpecDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("petDisSpecDao");
		// IPetSoluDao petSoluDao = (IPetSoluDao)
		// ContextLoader.getCurrentWebApplicationContext().getBean("petSoluDao");

		ArrayList<ArrayList<String>> blocks = LoadFileUtil.generateBlocks(file, BLOCK_SEPARATOR);

		for (int i = 0; i < blocks.size(); i++) {
			ArrayList<String> block = blocks.get(i);
			PetDisSpec petDisSpec = createPetDisSpec(block);
			String petDisSpecKey = petDisSpec.getPetDisSpecName() + "," + petDisSpec.getCropName();
			if (!petDisSpecMap.containsKey(petDisSpecKey)) {
				String[] solutions = createPetSoluText(block);
				for (int j = 0; j < solutions.length; j++) {
					PetSolu petSolu = new PetSolu(solutions[j], petDisSpec, true, true);
					petSolu.setIsCPSolu(isCPSolu(petSolu)); // 用isCPSolu方法查看是否和农药相关
					petDisSpec.getPetSolus().add(petSolu); // 创建 PetSolu类
				}
				petDisSpecMap.put(petDisSpecKey, petDisSpec);
			}
		}
	}

	public static PetDisSpec createPetDisSpec(ArrayList<String> lines) {
		PetDisSpec petDisSpec = new PetDisSpec();

		// 从第一行得到，petDisSpecName 名称; crops 作物; 注意:病害和草害一种disaster对应一种crop
		String title[] = lines.get(0).split(">>");
		petDisSpec.setCropName(title[1].trim());
		petDisSpec.setPetDisSpecName(title[2].trim());

		// 从第三行得到, alias 异名
		String yiming[] = lines.get(2).split(":", 2);
		petDisSpec.setAlias(yiming[1].trim());

		// read in the line for 形态特征, and check if it is 虫害 or 病害, if 虫害, set
		// color = 形态特征
		String xttz[] = lines.get(lines.size() - 4).split(":", 2);
		String xttzContent = xttz[1].trim();
		if (!xttzContent.equals("")) { // 形态特征非空，代表是虫害
			petDisSpec.setType("虫害");
		} else { // 形态特征若空，代表是病害
			petDisSpec.setType("病害");
		}

		// check whether isException, 若异常，下面都为空，若正常，则继续读取数据
		if (xttzContent.length() > 7000) {
		} else {
			String whtz[] = lines.get(lines.size() - 8).split(":", 2); // 读取"危害症状"
			String whtzContent = whtz[1].trim();
			String qrxh[] = lines.get(lines.size() - 6).split(":", 2); // 读取"侵染循环"
			String qrxhContent = qrxh[1].trim();
			String fsyy[] = lines.get(lines.size() - 5).split(":", 2); // 读取"发生原因"
			String fsyyContent = fsyy[1].trim();
			String shxx[] = lines.get(lines.size() - 3).split(":", 2); // 读取"生活习性"
			String shxxContent = shxx[1].trim();

			petDisSpec.setSympthon(whtzContent);
			petDisSpec.setRules(fsyyContent);

			// 开始按照 病害/虫害 填表
			if (petDisSpec.getType().equals("病害")) {
				petDisSpec.setForms(qrxhContent);
				petDisSpec.setHabits(qrxhContent);
			} else if (petDisSpec.getType().equals("虫害")) {
				petDisSpec.setForms(xttzContent);
				petDisSpec.setHabits(shxxContent);
			}

			// get pictureIds
			String pictures = "";
			for (int i = 4; i < lines.size() - 8; i++) {
				String line = lines.get(i);
				String items[] = line.split(":", 2);
				pictures += items[1].trim() + "\n";
			}
			petDisSpec.setPictureIds(pictures);
		}
		return petDisSpec;
	}

	public static String[] createPetSoluText(ArrayList<String> lines) {
		String fzff[] = lines.get(lines.size() - 2).split(":", 2); // 读取"防治方法"
		String fzffContent = fzff[1].trim();
		String[] solutions;
		if (fzffContent.length() < 7000) {
			String regex = "\\[防治\\]"; // 去除开头可能有的 [防治]
			Pattern p = Pattern.compile(regex);
			String items[] = p.split(fzffContent);
			fzffContent = items[items.length - 1].trim();
			// 用(1),(2),(3)来split字段；作为solution将用来Construct solutions ArrayList
			regex = "\\(\\d\\)";
			solutions = fzffContent.replaceFirst("^" + regex, "").split(regex);
		} else {
			solutions = new String[0];
		}
		return solutions;
	}

	public static boolean isCPSolu(PetSolu petSolu) {
		// 下面来判断PetSolu是不是属于CP范畴，暂时方案：如果PetSolu内容含有关键字"液|化学|喷雾|剂"，则判定为CP方案
		String petSoluDes = petSolu.getPetSoluDes();
		String regex = "液|化学|喷雾|剂";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(petSoluDes);

		return m.find();
	}
}
