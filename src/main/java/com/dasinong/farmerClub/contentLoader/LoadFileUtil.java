package com.dasinong.farmerClub.contentLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoadFileUtil {
	public static ArrayList<ArrayList<String>> generateBlocks(File file, String BLOCK_SEPARATOR) {
		ArrayList<ArrayList<String>> blocks = new ArrayList<ArrayList<String>>();
		try {
			// FileReader fr = new FileReader(file);
			FileInputStream fr = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fr, "UTF-8"));
			ArrayList<String> block = new ArrayList<String>();
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.contains("exception")) {
					block.add(line);
				}
				if (line.equals(BLOCK_SEPARATOR)) {
					blocks.add(block);
					block = new ArrayList<String>();
				}
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return blocks;
	}

	public static void printBlock(ArrayList<String> lines) {
		for (int i = 0; i < lines.size(); i++) {
			System.out.println(lines.get(i));
		}
	}

	public static void generateSampleFile(File sourceFile, File sampleFile, String BLOCK_SEPARATOR) {
		try {
			FileReader fr = new FileReader(sourceFile);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(sampleFile);
			BufferedWriter bw = new BufferedWriter(fw);
			int blockCount = 0;
			int sampleSize = 1;

			String line;
			while ((line = br.readLine()) != null) {
				bw.write(line + "\n");
				if (line.equals(BLOCK_SEPARATOR)) {
					blockCount++;
					if (blockCount == sampleSize) {
						break;
					}
				}
			}
			bw.flush();
			bw.close();
			fw.close();
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
