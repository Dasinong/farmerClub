/**
 * 
 */
package com.dasinong.farmerClub.ruleEngine.engine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.io.KieResources;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

/**
 * @author caichao
 *
 */
public class RuleEngine {

	KieContainer kieContainer;
	private String ruleFile;

	public RuleEngine(String ruleFile) {
		this.ruleFile = ruleFile;
		buildRule();
	}

	public void buildRule() {
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		KieResources kieResources = kieServices.getResources();
		KieRepository kieRepository = kieServices.getRepository();
		System.out.println(System.getProperty("user.dir"));
		File directory = new File("");
		try {
			System.out.println(directory.getCanonicalPath());
			System.out.println(directory.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Resource resource = kieResources.newClassPathResource(ruleFile);
		kieFileSystem.write(resource);
		KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
		kb.buildAll();
		System.out.println(kb.getResults().getMessages());

		kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
	}

	public synchronized KieSession getNewSession() {
		return this.kieContainer.newKieSession();
	}

	public StatelessKieSession getStatelessSession() {
		return this.kieContainer.newStatelessKieSession();
	}
}
