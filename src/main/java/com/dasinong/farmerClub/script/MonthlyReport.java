package com.dasinong.farmerClub.script;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.dasinong.farmerClub.util.MailHelper;
import com.mysql.jdbc.Driver;

//Monthly table al
public class MonthlyReport {
	
	public static String generateReport(int appId, int year, int month) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:MySQL://120.26.208.198:3306/";
		switch (appId){
			case 1: url=url+"ploughHelper"; break;
			case 2: url=url+"farmer_club"; break;
		}
		String user="root";
		String password="weather123";

		Connection con= DriverManager.getConnection(url,user,password);
		Statement statement=con.createStatement();
		String query="select * from user where Month(createAt)="+ month +" and Year(createAt)="+year;
		ResultSet rs = statement.executeQuery(query);
		Hashtable<Integer,List<Integer>> reftrace = new Hashtable<Integer,List<Integer>>();
		int newUser=0;
		while (rs.next()){
			try{
				int i = rs.getInt("refuid");
				if (reftrace.containsKey(i)){
					reftrace.get(i).add(rs.getInt("userId"));
				}
				else{
				   reftrace.put(i,new ArrayList<Integer>());
				   reftrace.get(i).add(rs.getInt("userId"));
				}
			}catch (Exception e){
				//for the case refuid==null
			}
			newUser++;
		}
		rs.close();
		
		StringBuilder content = new StringBuilder();
		switch (appId){
			case 1:content.append("今日农事"); break;
			case 2:content.append("大户俱乐部"); break;
		}
		content.append(year+"年"+month+"月月报\r\n");
		content.append("本月新增用户数："+newUser+"\r\n");
		for(Integer es : reftrace.keySet()){
			rs = statement.executeQuery("select userName,cellPhone,userType,institutionId from user where userId = "+es);
			if (rs.next()){
				switch (rs.getInt("institutionId")){
					case 1: content.append("陶氏"); break;
					case 2: content.append("燕化"); break;
					case 3: content.append("巴士甫"); break;
				}
				if (rs.getString("userType")!=null)
				{
					switch (rs.getString("userType")){
					 case "sales" :content.append("销售");
					 	break;
					 case "farmer" : content.append("农户");
					    break;
					 default:
						 content.append("其他");
					}
				}
				else
				{
					content.append("其他");
				}
				//content.append(rs.getString("userType") == null?"others":rs.getString("userType"));
				content.append(":"+rs.getString("userName")+"(");
				content.append(rs.getString("cellPhone")==null?"其他":rs.getString("cellPhone"));
				content.append(") 推荐了"+reftrace.get(es).size()+"人。\r\n");
				//if (re.getString("institutionId")institutionId==3)
			}
		}
		return content.toString();
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		String content = generateReport(2,2016,1);
		System.out.println(content);
		
		content = generateReport(1,2016,1);
		System.out.println(content);
		//MailHelper mh = new MailHelper();
		//mh.sendMail("coordinate6467@hotmail.com", "大户俱乐部2016年1月月报", content);


	}
}
