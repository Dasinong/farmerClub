package com.dasinong.farmerClub.contentLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import au.com.bytecode.opencsv.CSVReader;

import com.dasinong.farmerClub.dao.IProverbDao;
import com.dasinong.farmerClub.model.Proverb;
import com.dasinong.farmerClub.util.Env;

public class LoadProverb {
	public final File FILE = new File(Env.getEnv().WorkingDir, "sourcefiles/proverb.csv");

	public void readFile() {
		IProverbDao proverbDao = (IProverbDao) ContextLoader.getCurrentWebApplicationContext().getBean("proverbDao");
		try {
			FileInputStream fr = new FileInputStream(FILE);
			CSVReader reader = new CSVReader(new InputStreamReader(fr, "UTF-8"), ',', '\"', 1);
			List entries = reader.readAll();
			for (int i = 0; i < entries.size(); i++) {
				String items[] = (String[]) entries.get(i);
				String necessaryCondition = items[1];
				String content = items[2];
				Proverb proverb = new Proverb();
				// proverb.setNecessaryCondition(necessaryCondition);
				proverb.setContent(content);
				proverbDao.save(proverb);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
