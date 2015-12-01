package com.dasinong.farmerClub.ruleEngine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;

import java.util.Date;

import com.dasinong.farmerClub.ruleEngine.domain.Event;
import com.dasinong.farmerClub.ruleEngine.domain.EventType;
import com.dasinong.farmerClub.ruleEngine.domain.Field;
import com.dasinong.farmerClub.ruleEngine.domain.Recommendation;
import com.dasinong.farmerClub.ruleEngine.domain.Reminder;
import com.dasinong.farmerClub.ruleEngine.domain.Stage;
import com.dasinong.farmerClub.ruleEngine.domain.StageType;
import com.dasinong.farmerClub.ruleEngine.domain.Weather;
import com.dasinong.farmerClub.ruleEngine.domain.WeatherPhenomena;
import com.dasinong.farmerClub.ruleEngine.engine.RuleEngine;

public class TestRule {
	public long DAY_TIME = 24 * 3600 * 1000;
	RuleEngine engine = new RuleEngine("com/dasinong/farmerClub/ruleEngine/rules/rule.drl");

	@Test
	public void testWhenEverythingIsOk() {

		StatelessKieSession session = engine.getStatelessSession();
		Stage stage = new Stage();
		stage.setType(StageType.plant);
		Date startDate = new Date();
		Date recommendedDate = new Date(startDate.getTime() + 10 * DAY_TIME);
		Date nextDate1 = new Date(recommendedDate.getTime() + DAY_TIME);
		Date nextDate2 = new Date(recommendedDate.getTime() + 2 * DAY_TIME);
		Weather recommendedDateW = new Weather(10, recommendedDate);
		recommendedDateW.setMinTemperature(5);
		Weather nextDate1W = new Weather(10, nextDate1);
		nextDate1W.setMinTemperature(5);
		Weather nextDate2W = new Weather(10, nextDate2);
		nextDate2W.setMinTemperature(5);
		Event event = new Event(EventType.CompletedVaritySelection);
		stage.setStartDate(startDate);
		Field field = new Field();
		field.setGreenHouse(true);
		List<Object> facts = new ArrayList<>();
		facts.add(event);
		facts.add(stage);
		facts.add(field);
		facts.add(nextDate1W);
		facts.add(nextDate2W);
		List<Command> cmds = new ArrayList<>();
		cmds.add(CommandFactory.newInsertElements(facts));
		List<Recommendation> recommendations = new ArrayList<>();
		cmds.add(CommandFactory.newSetGlobal("recommends", recommendations));
		List<Reminder> reminders = new ArrayList<>();
		cmds.add(CommandFactory.newSetGlobal("reminders", reminders));
		cmds.add(CommandFactory.newSetGlobal("recommendedDate", recommendedDateW));
		ExecutionResults rets = session.execute(CommandFactory.newBatchExecution(cmds));
		System.out.println(recommendations);
		assertEquals(1, recommendations.size());
	}

	@Test
	public void testWhenHardRainIsComing() {

		StatelessKieSession session = engine.getStatelessSession();
		Stage stage = new Stage();
		stage.setType(StageType.plant);
		Date startDate = new Date();
		Date recommendedDate = new Date(startDate.getTime() + 10 * DAY_TIME);
		Date nextDate1 = new Date(recommendedDate.getTime() + DAY_TIME);
		Date nextDate2 = new Date(recommendedDate.getTime() + 2 * DAY_TIME);
		Weather recommendedDateW = new Weather(10, recommendedDate);
		recommendedDateW.setMinTemperature(5);
		recommendedDateW.addPhenomenon(WeatherPhenomena.HardRain);
		Weather nextDate1W = new Weather(10, nextDate1);
		nextDate1W.setMinTemperature(5);
		Weather nextDate2W = new Weather(10, nextDate2);
		nextDate2W.setMinTemperature(5);
		Event event = new Event(EventType.CompletedVaritySelection);
		stage.setStartDate(startDate);
		Field field = new Field();
		field.setGreenHouse(true);
		List<Object> facts = new ArrayList<>();
		facts.add(event);
		facts.add(stage);
		facts.add(field);
		// facts.add(recommendedDateW);
		facts.add(nextDate1W);
		facts.add(nextDate2W);
		List<Command> cmds = new ArrayList<>();
		cmds.add(CommandFactory.newInsertElements(facts));
		List<Recommendation> recommendations = new ArrayList<>();
		cmds.add(CommandFactory.newSetGlobal("recommends", recommendations));
		List<Reminder> reminders = new ArrayList<>();
		cmds.add(CommandFactory.newSetGlobal("reminders", reminders));
		cmds.add(CommandFactory.newSetGlobal("recommendedDate", recommendedDateW));
		ExecutionResults rets = session.execute(CommandFactory.newBatchExecution(cmds));
		System.out.println(recommendations);
		assertEquals(0, recommendations.size());

	}

	@Test
	public void testInstruction1Fired() throws Exception {
		StatelessKieSession session = engine.getStatelessSession();
		Stage stage = new Stage();
		stage.setType(StageType.seedling);
		Date now = new Date();
		Weather weather = new Weather(36, now);
		Event event = new Event(EventType.LoginHomePage);
		stage.setStartDate(now);
		List<Object> facts = new ArrayList<>();
		facts.add(event);
		facts.add(stage);
		facts.add(weather);
		List<Command> cmds = new ArrayList<>();
		cmds.add(CommandFactory.newInsertElements(facts));
		List<Recommendation> recommendations = new ArrayList<>();
		List<Reminder> reminders = new ArrayList<>();
		cmds.add(CommandFactory.newSetGlobal("recommends", recommendations));
		cmds.add(CommandFactory.newSetGlobal("reminders", reminders));
		ExecutionResults rets = session.execute(CommandFactory.newBatchExecution(cmds));
		System.out.println(recommendations);
		System.out.println(reminders);
		assertEquals(1, recommendations.size());
		assertEquals(1, reminders.size());
	}

	@Test
	public void testInstruction2Fired() throws Exception {
		StatelessKieSession session = engine.getStatelessSession();

		Date now = new Date();
		Weather weather = new Weather(36, now);
		Event event = new Event(EventType.LoginHomePage);
		List<Object> facts = new ArrayList<>();
		facts.add(event);
		facts.add(weather);
		List<Command> cmds = new ArrayList<>();
		cmds.add(CommandFactory.newInsertElements(facts));
		List<Recommendation> recommendations = new ArrayList<>();
		List<Reminder> reminders = new ArrayList<>();
		cmds.add(CommandFactory.newSetGlobal("recommends", recommendations));
		cmds.add(CommandFactory.newSetGlobal("reminders", reminders));
		ExecutionResults rets = session.execute(CommandFactory.newBatchExecution(cmds));
		System.out.println(recommendations);
		System.out.println(reminders);
		assertEquals(1, recommendations.size());
	}
}
