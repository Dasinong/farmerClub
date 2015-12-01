package com.dasinong.farmerClub.weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class WeatherAlert implements Comparable<WeatherAlert> {

	public String province;
	public String city;
	public String country;
	public String typeId;
	public String typeName;
	public String level;
	public String levelName;
	public String time;
	public String content;
	public String urlTag;
	public String areaId;
	public Date timeStamp;
	public String desc;// description of alert and its level
	public static Map<String, String> descriptionMap = new HashMap<String, String>();
	public String trace;

	// Initialize from webapi
	public WeatherAlert(String areaId) {
		this.province = "";
		this.city = "";
		this.country = "";
		this.typeId = "";
		this.typeName = "";
		this.level = "";
		this.levelName = "";
		this.time = "";
		this.urlTag = "weatherAlert";
		this.content = "";
		this.desc = "";
		this.areaId = areaId;
		this.timeStamp = new Date();
		this.timeStamp.setTime(100000); // Time set on create routine not
										// guarantee content is good.
		this.trace = "";
	}

	public static List<WeatherAlert> parseHTTPResult(String areaId, String result)
			throws JsonParseException, JsonProcessingException, IOException {
		// areaId is monitorLocationId in interface
		ObjectMapper mapper = new ObjectMapper();
		List<WeatherAlert> waList = new ArrayList<WeatherAlert>();

		if (descriptionMap.isEmpty()) {
			String desArray[] = { "24小时内可能或者已经受热带气旋影响,沿海或者陆地平均风力达6级以上，或者阵风8级以上并可能持续。",
					"24小时内可能或者已经受热带气旋影响,沿海或者陆地平均风力达8级以上，或者阵风10级以上并可能持续。",
					"12小时内可能或者已经受热带气旋影响,沿海或者陆地平均风力达10级以上，或者阵风12级以上并可能持续。",
					"6小时内可能或者已经受热带气旋影响，沿海或者陆地平均风力达12级以上，或者阵风达14级以上并可能持续。", "12小时内降雨量将达50毫米以上，或者已达50毫米以上且降雨可能持续。",
					"6小时内降雨量将达50毫米以上，或者已达50毫米以上且降雨可能持续。", "3小时内降雨量将达50毫米以上，或者已达50毫米以上且降雨可能持续。",
					"3小时内降雨量将达100毫米以上，或者已达100毫米以上且降雨可能持续。", "12小时内降雪量将达4毫米以上，或者已达4毫米以上且降雪持续，可能对交通或者农牧业有影响。",
					"12小时内降雪量将达6毫米以上，或者已达6毫米以上且降雪持续，可能对交通或者农牧业有影响。",
					"6小时内降雪量将达10毫米以上，或者已达10毫米以上且降雪持续，可能或者已经对交通或者农牧业有较大影响。",
					"6小时内降雪量将达15毫米以上，或者已达15毫米以上且降雪持续，可能或者已经对交通或者农牧业有较大影响。",
					"48小时内最低气温将要下降8℃以上，最低气温小于等于4℃，陆地平均风力可达5级以上；或者已经下降8℃以上，最低气温小于等于4℃，平均风力达5级以上，并可能持续。",
					"24小时内最低气温将要下降10℃以上，最低气温小于等于4℃，陆地平均风力可达6级以上；或者已经下降10℃以上，最低气温小于等于4℃，平均风力达6级以上，并可能持续。",
					"24小时内最低气温将要下降12℃以上，最低气温小于等于0℃，陆地平均风力可达6级以上；或者已经下降12℃以上，最低气温小于等于0℃，平均风力达6级以上，并可能持续。",
					"24小时内最低气温将要下降16℃以上，最低气温小于等于0℃，陆地平均风力可达6级以上；或者已经下降16℃以上，最低气温小于等于0℃，平均风力达6级以上，并可能持续。",
					"24小时内可能受大风影响,平均风力可达6级以上，或者阵风7级以上；或者已经受大风影响,平均风力为6～7级，或者阵风7～8级并可能持续。",
					"12小时内可能受大风影响,平均风力可达8级以上，或者阵风9级以上；或者已经受大风影响,平均风力为8～9级，或者阵风9～10级并可能持续。",
					"6小时内可能受大风影响,平均风力可达10级以上，或者阵风11级以上；或者已经受大风影响,平均风力为10～11级，或者阵风11～12级并可能持续。",
					"6小时内可能受大风影响，平均风力可达12级以上，或者阵风13级以上；或者已经受大风影响，平均风力为12级以上，或者阵风13级以上并可能持续。", "",
					"12小时内可能出现沙尘暴天气（能见度小于1000米），或者已经出现沙尘暴天气并可能持续。", "6小时内可能出现强沙尘暴天气（能见度小于500米），或者已经出现强沙尘暴天气并可能持续。",
					"6小时内可能出现特强沙尘暴天气（能见度小于50米），或者已经出现特强沙尘暴天气并可能持续。", "", "连续三天日最高气温将在35℃以上。", "24小时内最高气温将升至37℃以上。",
					"24小时内最高气温将升至40℃以上。", "", "", "预计未来一周综合气象干旱指数达到重旱(气象干旱为25～50年一遇)，或者某一县（区）有40%以上的农作物受旱。",
					"预计未来一周综合气象干旱指数达到特旱(气象干旱为50年以上一遇)，或者某一县（区）有60%以上的农作物受旱。", "", "6小时内可能发生雷电活动，可能会造成雷电灾害事故。",
					"2小时内发生雷电活动的可能性很大，或者已经受雷电活动影响，且可能持续，出现雷电灾害事故的可能性比较大。",
					"2小时内发生雷电活动的可能性非常大，或者已经有强烈的雷电活动发生，且可能持续，出现雷电灾害事故的可能性非常大。", "", "", "6小时内可能出现冰雹天气，并可能造成雹灾。",
					"2小时内出现冰雹可能性极大，并可能造成重雹灾。", "48小时内地面最低温度将要下降到0℃以下，对农业将产生影响，或者已经降到0℃以下，对农业已经产生影响，并可能持续。",
					"24小时内地面最低温度将要下降到零下3℃以下，对农业将产生严重影响，或者已经降到零下3℃以下，对农业已经产生严重影响，并可能持续。",
					"24小时内地面最低温度将要下降到零下5℃以下，对农业将产生严重影响，或者已经降到零下5℃以下，对农业已经产生严重影响，并将持续。", "", "",
					"12小时内可能出现能见度小于500米的雾，或者已经出现能见度小于500米、大于等于200米的雾并将持续。",
					"6小时内可能出现能见度小于200米的雾，或者已经出现能见度小于200米、大于等于50米的雾并将持续。", "2小时内可能出现能见度小于50米的雾，或者已经出现能见度小于50米的雾并将持续。",
					"", "12小时内可能出现能见度小于3000米的霾，或者已经出现能见度小于3000米的霾且可能持续。",
					"6小时内可能出现能见度小于2000米的霾，或者已经出现能见度小于2000米的霾且可能持续。", "", "", "当路表温度低于0℃，出现降水，12小时内可能出现对交通有影响的道路结冰。",
					"当路表温度低于0℃，出现降水，6小时内可能出现对交通有较大影响的道路结冰。", "当路表温度低于0℃，出现降水，2小时内可能出现或者已经出现对交通有很大影响的道路结冰。" };
			String tag = "0101";
			for (int i = 0; i < 14; i++)
				for (int j = 0; j < 4; j++)
					descriptionMap.put(i >= 9 ? "" + (101 + i * 100 + j) : "0" + (101 + i * 100 + j),
							desArray[i * 4 + j]);
		}

		try {
			JsonNode node = mapper.readTree(result);
			JsonNode alarm = node.get("alarm");
			JsonNode areaAlarm = alarm.get(areaId);
			JsonNode warnings = areaAlarm.get("w");
			if (warnings == null)
				return null;
			Iterator<JsonNode> warningNodes = warnings.getElements();
			while (warningNodes.hasNext()) {
				WeatherAlert wa = new WeatherAlert("0");
				JsonNode w = warningNodes.next();
				wa.province = w.get("w1").toString().replace('\"', ' ').trim();
				wa.city = w.get("w2").toString().replace('\"', ' ').trim();
				wa.country = w.get("w3").toString().replace('\"', ' ').trim();
				wa.typeId = w.get("w4").toString().replace('\"', ' ').trim();
				wa.typeName = w.get("w5").toString().replace('\"', ' ').trim();
				wa.level = w.get("w6").toString().replace('\"', ' ').trim();
				wa.levelName = w.get("w7").toString().replace('\"', ' ').trim();
				wa.time = w.get("w8").toString().replace('\"', ' ').trim();
				wa.content = w.get("w9").toString().replace('\"', ' ').trim();
				wa.trace = w.get("w10").getTextValue();
				wa.urlTag = "/weatherAlert?monitorLocationId=" + areaId + "&trace=" + wa.trace;
				wa.timeStamp = new Date();
				wa.areaId = areaId;
				wa.desc = descriptionMap.get(wa.typeId + wa.level);
				waList.add(wa);
			}

			Collections.sort(waList);

		} catch (Exception e) {
			System.out.println("Error happened when processing json live weather data!");
			e.printStackTrace();
		}
		if (waList.size() > 0)
			// TODO 默认是升序，所以排序后返回第一个元素，是预警编号最小的值，可能是最严重的灾害
			// return waList.get(0);
			return waList;
		else
			return null;
	}

	public String shortDescription() {
		String des = "";
		des = "气象台 " + this.time + "消息 ：" + this.province + this.city + this.country + "将有" + this.typeName
				+ this.levelName + "预警。请防范。";
		return des;
	}

	public String fullDescription() {
		return this.content;
	}

	public String urlTag() {
		return this.urlTag;
	}

	@Override
	public int compareTo(WeatherAlert o) {
		return Integer.parseInt(o.level) - Integer.parseInt(this.level);
	}

}
