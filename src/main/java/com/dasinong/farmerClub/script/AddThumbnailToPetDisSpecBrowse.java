package com.dasinong.farmerClub.script;

import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IPetDisSpecBrowseDao;
import com.dasinong.farmerClub.dao.IPetDisSpecDao;
import com.dasinong.farmerClub.model.PetDisSpec;
import com.dasinong.farmerClub.model.PetDisSpecBrowse;

public class AddThumbnailToPetDisSpecBrowse {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");

		IPetDisSpecBrowseDao browseDao = (IPetDisSpecBrowseDao) applicationContext.getBean("petDisSpecBrowseDao");
		IPetDisSpecDao dao = (IPetDisSpecDao) applicationContext.getBean("petDisSpecDao");

		List<PetDisSpecBrowse> browses = browseDao.findAll();
		for (PetDisSpecBrowse browse : browses) {
			// if we already generated thumbnailId, skip it!
			if (browse.getThumbnailId() != null) {
				continue;
			}

			// Get picture Ids
			PetDisSpec dis = dao.findById(browse.getPetDisSpecId());
			String pictureIdsStr = dis.getPictureIds();
			if (pictureIdsStr == null || pictureIdsStr.equals("")) {
				continue;
			}
			String[] pictureIds = pictureIdsStr.split("\n");
			if (pictureIds.length == 0) {
				continue;
			}

			// generate thumbnail Id
			// For example, if pictureId is 2011-09-20-14-20.jpg, by convention
			// the thumbnail Id is thumb_2011-09-20-14-20.jpg.
			String[] pathTokens = pictureIds[0].split("/");
			pathTokens[pathTokens.length - 1] = "thumb_" + pathTokens[pathTokens.length - 1];
			String thumbnailId = String.join("/", pathTokens);

			// update browse row
			browse.setThumbnailId(thumbnailId);
			browseDao.update(browse);
		}
	}
}
