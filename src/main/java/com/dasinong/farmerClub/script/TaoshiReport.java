package com.dasinong.farmerClub.script;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class TaoshiReport {
	public static String generateReport(int year, int month, int institutionId) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:MySQL://120.26.208.198:3306/";
		
		String user="root";
		String password="weather123";

		Connection con= DriverManager.getConnection(url,user,password);
		Statement statement=con.createStatement();
		
		StringBuilder result = new StringBuilder();
		
		//Dows新用户数
		String query="SELECT count(*) FROM farmer_club.user where (Month(createAt)="+(month-1)+" and Year(createAt)="+year+" and Day(createAt)>20)"+
				"||(Month(createAt)="+month+" and Year(createAt)="+year+" and Day(createAt)<=20) and institutionId="+institutionId;
		ResultSet rs = statement.executeQuery(query);
		if (rs.next()){
			try{
				int i = rs.getInt("count(*)");
				result.append("机构"+institutionId+"新增人数: "+i+"\r\n");
			}catch (Exception e){
				System.out.println("获取新增人数失败");
			}
		}
		rs.close();
		
		
		//Dows总用户数
		query="SELECT count(*) FROM farmer_club.user where institutionId="+institutionId;
		rs = statement.executeQuery(query);
		if (rs.next()){
			try{
				int i = rs.getInt("count(*)");
				result.append("机构总人数: "+i+"\r\n");
			}catch (Exception e){
				System.out.println("获取新增人数失败");
			}
		}
		rs.close();
	
	
		
		//巴斯夫新下载人数
		//巴斯夫总下载人数
		
		//巴斯夫分地区店铺激活数
		String[] provinces  = new String[]{"黑龙江","江苏"};
		
		//巴斯夫分地区店铺激活数／店铺总数
		for(int c=0;c<provinces.length;c++){
			query = "SELECT count(*) FROM farmer_club.user,farmer_club.store,farmer_club.location where userType=\"retailer\" and institutionId=1 "
					+ " and store.ownerId=user.userId"
                    + " and store.locationId = location.locationId and location.province=\""+provinces[c]+"\""
                    + " and createAt!=lastLogin";
			
			rs = statement.executeQuery(query);
			if (rs.next()){
					try{
						int i = rs.getInt("count(*)");
						result.append(provinces[c] + "零售店激活数: "+i+"\r\n");
					}catch (Exception e){
						
					}
			}
			rs.close();
			
			
			query = "SELECT count(*) FROM farmer_club.user,farmer_club.store,farmer_club.location where userType=\"retailer\" and institutionId=1 "
					+ " and store.ownerId=user.userId"
                    + " and store.locationId = location.locationId and location.province=\""+provinces[c]+"\"";
			
			rs = statement.executeQuery(query);
			if (rs.next()){
					try{
						int i = rs.getInt("count(*)");
						result.append(provinces[c] + "零售店总数: "+i+"\r\n");
					}catch (Exception e){
						
					}
			}
			rs.close();
		}
		
		
		
		for(int c=0;c<provinces.length;c++){
			query = "select count(*) from farmer_club.user where refuid in (SELECT userId FROM farmer_club.user,farmer_club.store,farmer_club.location where"
					+ " userType=\"farmer\" and store.ownerId=user.refuid"
					+ " and store.locationId = location.locationId and location.province=\""+ provinces[c]+"\")";
			
			rs = statement.executeQuery(query);
			if (rs.next()){
					try{
						int i = rs.getInt("count(*)");
						result.append(provinces[c] + "电子圈领取: "+i+"\r\n");
					}catch (Exception e){
						
					}
			}
			rs.close();
		}
		
		return result.toString();
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		String content = generateReport(2016,5,1);
		System.out.println(content);
		

		//MailHelper mh = new MailHelper();
		//mh.sendMail("coordinate6467@hotmail.com", "大户俱乐部2016年1月月报", content);


	}
}
