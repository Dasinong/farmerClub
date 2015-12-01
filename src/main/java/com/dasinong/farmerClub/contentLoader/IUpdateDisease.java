package com.dasinong.farmerClub.contentLoader;

import java.io.File;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

public interface IUpdateDisease {

	public final static File FILE = new File("/Users/jiachengwu/Documents/sourcefiles/petDisSpec.csv");

	public abstract void run();

	public abstract Set<Integer> processSubStageIds(String subStageString);

}