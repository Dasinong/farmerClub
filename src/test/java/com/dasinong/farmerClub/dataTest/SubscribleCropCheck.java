package com.dasinong.farmerClub.dataTest;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dasinong.farmerClub.dao.ICropDao;
import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.model.Step;
import com.dasinong.farmerClub.model.SubStage;
import com.dasinong.farmerClub.model.TaskSpec;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
		"file:./src/main/webapp/WEB-INF/spring/database/TestDataSource.xml",
		"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml" })

@Transactional
public class SubscribleCropCheck {

	@Autowired
	private ICropDao cropDao;
	

	@Test
	public void checkHuangGua() {
		Crop crop = cropDao.findById(19L);
		Assert.assertEquals("黄瓜", crop.getCropName());
		Assert.assertEquals(5, crop.getSubStages().size());
		Set<SubStage> subStages = crop.getSubStages();
        SubStage substage = subStages.iterator().next();
        Assert.assertEquals(112L, (long) substage.getSubStageId());
        Assert.assertEquals("秧苗期", substage.getSubStageName());
        Assert.assertEquals(2, substage.getTaskSpecs().size());
        Set<TaskSpec> taskSpecs = substage.getTaskSpecs();
        TaskSpec taskSpec = taskSpecs.iterator().next();
        Assert.assertEquals(262L, (long) taskSpec.getTaskSpecId());
        Assert.assertEquals("土壤消毒", taskSpec.getTaskSpecName());
        Assert.assertEquals(1, taskSpec.getSteps().size());
        List<Step> steps = taskSpec.getSteps();
        Step step = steps.iterator().next();
        Assert.assertEquals(432L, (long) step.getStepId());
        Assert.assertEquals("土壤消毒", step.getStepName());
	}
	
	@Test
	public void checkXiangjiao() {
		Crop crop = cropDao.findById(34L);
		Assert.assertEquals("香蕉", crop.getCropName());
		Assert.assertEquals(6, crop.getSubStages().size());
		Set<SubStage> subStages = crop.getSubStages();
        SubStage substage = subStages.iterator().next();
        Assert.assertEquals(100L, (long) substage.getSubStageId());
        Assert.assertEquals("组培苗期", substage.getSubStageName());
        Assert.assertEquals(3, substage.getTaskSpecs().size());
        Set<TaskSpec> taskSpecs = substage.getTaskSpecs();
        TaskSpec taskSpec = taskSpecs.iterator().next();
        Assert.assertEquals(220L, (long) taskSpec.getTaskSpecId());
        Assert.assertEquals("炼苗", taskSpec.getTaskSpecName());
        Assert.assertEquals(1, taskSpec.getSteps().size());
        List<Step> steps = taskSpec.getSteps();
        Step step = steps.iterator().next();
        Assert.assertEquals(390L, (long) step.getStepId());
        Assert.assertEquals("炼苗", step.getStepName());
	}
	
	@Test
	public void checkCaomei() {
		Crop crop = cropDao.findById(48L);
		Assert.assertEquals("草莓", crop.getCropName());
		Assert.assertEquals(4, crop.getSubStages().size());
		Set<SubStage> subStages = crop.getSubStages();
        SubStage substage = subStages.iterator().next();
        Assert.assertEquals(122L, (long) substage.getSubStageId());
        Assert.assertEquals("育苗期", substage.getSubStageName());
        Assert.assertEquals(6, substage.getTaskSpecs().size());
        Set<TaskSpec> taskSpecs = substage.getTaskSpecs();
        TaskSpec taskSpec = taskSpecs.iterator().next();
        Assert.assertEquals(288L, (long) taskSpec.getTaskSpecId());
        Assert.assertEquals("育苗地的选择和准备", taskSpec.getTaskSpecName());
        Assert.assertEquals(1, taskSpec.getSteps().size());
        List<Step> steps = taskSpec.getSteps();
        Step step = steps.iterator().next();
        Assert.assertEquals(458L, (long) step.getStepId());
        Assert.assertEquals("育苗地的选择和准备", step.getStepName());
	}
	
	@Test
	public void checkPingguo() {
		Crop crop = cropDao.findById(78L);
		Assert.assertEquals("苹果", crop.getCropName());
		Assert.assertEquals(6, crop.getSubStages().size());
		Set<SubStage> subStages = crop.getSubStages();
        SubStage substage = subStages.iterator().next();
        Assert.assertEquals(106L, (long) substage.getSubStageId());
        Assert.assertEquals("休眠期", substage.getSubStageName());
        Assert.assertEquals(2, substage.getTaskSpecs().size());
        Set<TaskSpec> taskSpecs = substage.getTaskSpecs();
        TaskSpec taskSpec = taskSpecs.iterator().next();
        Assert.assertEquals(244L, (long) taskSpec.getTaskSpecId());
        Assert.assertEquals("清园、保叶", taskSpec.getTaskSpecName());
        Assert.assertEquals(1, taskSpec.getSteps().size());
        List<Step> steps = taskSpec.getSteps();
        Step step = steps.iterator().next();
        Assert.assertEquals(414L, (long) step.getStepId());
        Assert.assertEquals("清园、保叶", step.getStepName());
	}
	
	@Test
	public void checkDongxiaomai() {
		Crop crop = cropDao.findById(127L);
		Assert.assertEquals("小麦-冬小麦", crop.getCropName());
		Assert.assertEquals(14, crop.getSubStages().size());
		Set<SubStage> subStages = crop.getSubStages();
        SubStage substage = subStages.iterator().next();
        Assert.assertEquals(60L, (long) substage.getSubStageId());
        Assert.assertEquals("播种前", substage.getSubStageName());
        Assert.assertEquals(7, substage.getTaskSpecs().size());
        Set<TaskSpec> taskSpecs = substage.getTaskSpecs();
        TaskSpec taskSpec = taskSpecs.iterator().next();
        Assert.assertEquals(79L, (long) taskSpec.getTaskSpecId());
        Assert.assertEquals("大田准备", taskSpec.getTaskSpecName());
        Assert.assertEquals(1, taskSpec.getSteps().size());
        List<Step> steps = taskSpec.getSteps();
        Step step = steps.iterator().next();
        Assert.assertEquals(135L, (long) step.getStepId());
        Assert.assertEquals("选地", step.getStepName());
	}
	
	@Test
	public void checkFanqie() {
		Crop crop = cropDao.findById(148L);
		Assert.assertEquals("番茄", crop.getCropName());
		Assert.assertEquals(5, crop.getSubStages().size());
		Set<SubStage> subStages = crop.getSubStages();
        SubStage substage = subStages.iterator().next();
        Assert.assertEquals(117L, (long) substage.getSubStageId());
        Assert.assertEquals("育苗期", substage.getSubStageName());
        Assert.assertEquals(2, substage.getTaskSpecs().size());
        Set<TaskSpec> taskSpecs = substage.getTaskSpecs();
        TaskSpec taskSpec = taskSpecs.iterator().next();
        Assert.assertEquals(275L, (long) taskSpec.getTaskSpecId());
        Assert.assertEquals("苗床管理", taskSpec.getTaskSpecName());
        Assert.assertEquals(1, taskSpec.getSteps().size());
        List<Step> steps = taskSpec.getSteps();
        Step step = steps.iterator().next();
        Assert.assertEquals(445L, (long) step.getStepId());
        Assert.assertEquals("苗床管理", step.getStepName());
	}
	
	@Test
	public void checkXiaomai() {
		Crop crop = cropDao.findById(173L);
		Assert.assertEquals("小麦", crop.getCropName());
		Assert.assertEquals(14, crop.getSubStages().size());
		Set<SubStage> subStages = crop.getSubStages();
        SubStage substage = subStages.iterator().next();
        Assert.assertEquals(60L, (long) substage.getSubStageId());
        Assert.assertEquals("播种前", substage.getSubStageName());
        Assert.assertEquals(7, substage.getTaskSpecs().size());
        Set<TaskSpec> taskSpecs = substage.getTaskSpecs();
        TaskSpec taskSpec = taskSpecs.iterator().next();
        Assert.assertEquals(79L, (long) taskSpec.getTaskSpecId());
        Assert.assertEquals("大田准备", taskSpec.getTaskSpecName());
        Assert.assertEquals(1, taskSpec.getSteps().size());
        List<Step> steps = taskSpec.getSteps();
        Step step = steps.iterator().next();
        Assert.assertEquals(135L, (long) step.getStepId());
        Assert.assertEquals("选地", step.getStepName());
	}
	
	
	@Test
	public void checkMangguo() {
		Crop crop = cropDao.findById(202L);
		Assert.assertEquals("芒果", crop.getCropName());
		Assert.assertEquals(18, crop.getSubStages().size());
		Set<SubStage> subStages = crop.getSubStages();
        SubStage substage = subStages.iterator().next();
        Assert.assertEquals(74L, (long) substage.getSubStageId());
        Assert.assertEquals("苗木培育", substage.getSubStageName());
        Assert.assertEquals(6, substage.getTaskSpecs().size());
        Set<TaskSpec> taskSpecs = substage.getTaskSpecs();
        TaskSpec taskSpec = taskSpecs.iterator().next();
        Assert.assertEquals(156L, (long) taskSpec.getTaskSpecId());
        Assert.assertEquals("芒果对环境条件要求", taskSpec.getTaskSpecName());
        Assert.assertEquals(6, taskSpec.getSteps().size());
        List<Step> steps = taskSpec.getSteps();
        Step step = steps.iterator().next();
        Assert.assertEquals(291L, (long) step.getStepId());
        Assert.assertEquals("芒果对环境条件要求 ", step.getStepName());
	}
	
	@Test
	public void checkShuidao() {
		Crop crop = cropDao.findById(223L);
		Assert.assertEquals("水稻", crop.getCropName());
		Assert.assertEquals(25, crop.getSubStages().size());
		Set<SubStage> subStages = crop.getSubStages();
        SubStage substage = subStages.iterator().next();
        Assert.assertEquals(35L, (long) substage.getSubStageId());
        Assert.assertEquals("播种前", substage.getSubStageName());
        Assert.assertEquals(21, substage.getTaskSpecs().size());
        Set<TaskSpec> taskSpecs = substage.getTaskSpecs();
        TaskSpec taskSpec = taskSpecs.iterator().next();
        Assert.assertEquals(10L, (long) taskSpec.getTaskSpecId());
        Assert.assertEquals("教您怎样选好苗床", taskSpec.getTaskSpecName());
        Assert.assertEquals(1, taskSpec.getSteps().size());
        List<Step> steps = taskSpec.getSteps();
        Step step = steps.iterator().next();
        Assert.assertEquals(10L, (long) step.getStepId());
        Assert.assertEquals("选择苗床", step.getStepName());
	}
	
	@Test
	public void checkChunxiaomai() {
		Crop crop = cropDao.findById(230L);
		Assert.assertEquals("小麦-春小麦", crop.getCropName());
		Assert.assertEquals(14, crop.getSubStages().size());
		Set<SubStage> subStages = crop.getSubStages();
        SubStage substage = subStages.iterator().next();
        Assert.assertEquals(60L, (long) substage.getSubStageId());
        Assert.assertEquals("播种前", substage.getSubStageName());
        Assert.assertEquals(7, substage.getTaskSpecs().size());
        Set<TaskSpec> taskSpecs = substage.getTaskSpecs();
        TaskSpec taskSpec = taskSpecs.iterator().next();
        Assert.assertEquals(79L, (long) taskSpec.getTaskSpecId());
        Assert.assertEquals("大田准备", taskSpec.getTaskSpecName());
        Assert.assertEquals(1, taskSpec.getSteps().size());
        List<Step> steps = taskSpec.getSteps();
        Step step = steps.iterator().next();
        Assert.assertEquals(135L, (long) step.getStepId());
        Assert.assertEquals("选地", step.getStepName());
	}
	
	@Test
	public void checkPutao() {
		Crop crop = cropDao.findById(231L);
		Assert.assertEquals("葡萄", crop.getCropName());
		Assert.assertEquals(8, crop.getSubStages().size());
		Set<SubStage> subStages = crop.getSubStages();
        SubStage substage = subStages.iterator().next();
        Assert.assertEquals(92L, (long) substage.getSubStageId());
        Assert.assertEquals("萌芽前", substage.getSubStageName());
        Assert.assertEquals(3, substage.getTaskSpecs().size());
        Set<TaskSpec> taskSpecs = substage.getTaskSpecs();
        TaskSpec taskSpec = taskSpecs.iterator().next();
        Assert.assertEquals(201L, (long) taskSpec.getTaskSpecId());
        Assert.assertEquals("一般农事", taskSpec.getTaskSpecName());
        Assert.assertEquals(1, taskSpec.getSteps().size());
        List<Step> steps = taskSpec.getSteps();
        Step step = steps.iterator().next();
        Assert.assertEquals(371L, (long) step.getStepId());
        Assert.assertEquals("一般农事", step.getStepName());
	}

}
