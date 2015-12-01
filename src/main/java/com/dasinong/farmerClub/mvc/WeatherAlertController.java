package com.dasinong.farmerClub.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.dasinong.farmerClub.BaseController;
import com.dasinong.farmerClub.weather.GetWeatherAlert;
import com.dasinong.farmerClub.weather.WeatherAlert;

public class WeatherAlertController extends BaseController implements Controller {
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1、收集参数、验证参数
		// 2、绑定参数到命令对象
		// 3、将命令对象传入业务对象进行业务处理
		// 4、选择下一个页面
		ModelAndView mv = new ModelAndView();
		try {
			String areaId = request.getParameter("monitorLocationId");
			String trace = request.getParameter("trace");
			GetWeatherAlert gwa = new GetWeatherAlert(areaId);
			List<WeatherAlert> walist;
			walist = gwa.getWeatherAlert();
			if (!"".equals(trace) && walist != null) {
				for (WeatherAlert wa : walist) {
					if (wa.trace.equals(trace)) {
						// 添加模型数据 可以是任意的POJO对象
						mv.addObject("AlertTitle", wa.typeName + wa.levelName + "预警");
						mv.addObject("AlertContent", wa.content);
						mv.addObject("AlertIcon", "p" + wa.typeId + wa.level + ".png");
						mv.addObject("LevelId", "level" + wa.level + ".png");
						mv.addObject("AlertTimeDate", wa.time.split(" ")[0]);
						mv.addObject("AlertTimeClock", wa.time.split(" ")[1]);
						mv.addObject("AlertDesc", wa.desc);
						// 设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
						mv.setViewName("WeatherAlert");
					}
				}
			} else {
				// 添加模型数据 可以是任意的POJO对象
				if (walist != null) {
					WeatherAlert wa = walist.get(0);
					mv.addObject("AlertTitle", wa.typeName + wa.levelName + "预警");
					mv.addObject("AlertContent", wa.content);
					mv.addObject("AlertIcon", "p" + wa.typeId + wa.level + ".png");
					mv.addObject("LevelId", "level" + wa.level + ".png");
					mv.addObject("AlertTimeDate", wa.time.split(" ")[0]);
					mv.addObject("AlertTimeClock", wa.time.split(" ")[1]);
					mv.addObject("AlertDesc", wa.desc);
					// 设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
					mv.setViewName("WeatherAlert");
				} else {
					mv.addObject("AlertTitle", "台风蓝色预警");
					mv.addObject("AlertContent", "预警内容");
					mv.addObject("AlertTimeDate", "2015-07-03 04:36".split(" ")[0]);
					mv.addObject("AlertTimeClock", "2015-07-03 04:36".split(" ")[1]);
					mv.addObject("LevelId", "level01.png");
					mv.addObject("AlertIcon", "p0101.png");
					mv.addObject("AlertDesc", "24小时内可能或者已经受热带气旋影响,沿海或者陆地平均风力达6级以上，或者阵风8级以上并可能持续。");
					// 设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
					mv.setViewName("WeatherAlert");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}