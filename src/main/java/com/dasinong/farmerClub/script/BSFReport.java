package com.dasinong.farmerClub.script;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class BSFReport {
	public static String generateReport(int year, int month, int institutionId) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:MySQL://120.26.208.198:3306/";
		
		String user="root";
		String password="weather123";

		Connection con= DriverManager.getConnection(url,user,password);
		Statement statement=con.createStatement();
		
		StringBuilder result = new StringBuilder();
		
		//巴斯夫新用户数
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
		
		
		//巴斯夫版本总用户数
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
		
		//巴斯夫分地区店铺激活数／达人总数
		String[] provinces  = new String[]{"上海","浙江","山东","辽宁","河北","广东","广西"};
		for(int c=0;c<provinces.length;c++){
			query = "SELECT count(*) FROM farmer_club.user,farmer_club.store,farmer_club.location where userType=\"jiandadaren\""
					+ " and store.ownerId=user.refuid" +
					 " and store.locationId = location.locationId and location.province=\""+provinces[c]+"\"";
			
			rs = statement.executeQuery(query);
			if (rs.next()){
					try{
						int i = rs.getInt("count(*)");
						result.append(provinces[c] + "达人数: "+i+"\r\n");
					}catch (Exception e){
						
					}
			}
			rs.close();
			
			
			query = "SELECT count(*) FROM farmer_club.user,farmer_club.store,farmer_club.location where userType=\"jiandadaren\""
					+ " and store.ownerId=user.refuid" +
					 " and store.locationId = location.locationId and location.province=\""+provinces[c]+"\""
			        +" and createAt!=lastLogin"; 
			
			rs = statement.executeQuery(query);
			if (rs.next()){
					try{
						int i = rs.getInt("count(*)");
						result.append(provinces[c] + "激活达人数: "+i+"\r\n");
					}catch (Exception e){
						
					}
			}
			rs.close();
		}
		
		//巴斯夫分地区店铺激活数／店铺总数
		for(int c=0;c<provinces.length;c++){
			query = "SELECT count(*) FROM farmer_club.user,farmer_club.store,farmer_club.location where userType=\"retailer\" and institutionId=3 "
					+ " and store.ownerId=user.userId"
                    + " and store.locationId = location.locationId and location.province=\""+provinces[c]+"\""
                    + " and createAt!=lastLogin";
			
			rs = statement.executeQuery(query);
			if (rs.next()){
					try{
						int i = rs.getInt("count(*)");
						result.append(provinces[c] + "零售店(达人)激活数: "+i+"\r\n");
					}catch (Exception e){
						
					}
			}
			rs.close();
			
			
			query = "SELECT count(*) FROM farmer_club.user,farmer_club.store,farmer_club.location where userType=\"retailer\" and institutionId=3 "
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
		
		
		//巴斯夫分地区达人卷领取
		for(int c=0;c<provinces.length;c++){
			query = "SELECT count(*) FROM farmer_club.user,farmer_club.store,farmer_club.location,farmer_club.coupon where userType=\"jiandadaren\""
					+ " and store.ownerId=user.refuid"
					+ " and store.locationId = location.locationId and"
					+ " location.province=\""+ provinces[c] +"\" and coupon.ownerId=userId";
			
			rs = statement.executeQuery(query);
			if (rs.next()){
					try{
						int i = rs.getInt("count(*)");
						result.append(provinces[c] + "达人圈领取: "+i+"\r\n");
					}catch (Exception e){
						
					}
			}
			rs.close();
			
			
			query = "select count(*) from farmer_club.user where refuid in (SELECT userId FROM farmer_club.user,farmer_club.store,farmer_club.location where"
					+ " userType=\"jiandadaren\" and store.ownerId=user.refuid"
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
		
		
		
		
		
		
		
		
		//巴斯夫BVA扫码入库
		query = "SELECT count(*),userid FROM farmer_club.prostock where id>=24 group by userid";
		rs = statement.executeQuery(query);
		while (rs.next()){
			try{
				int userId = rs.getInt("userid");
				int i = rs.getInt("count(*)");
				result.append("用户"+ userId + "扫码: "+i+"\r\n");
			}catch (Exception e){
					
			}
		}
		rs.close();
		//巴斯夫活动情况

		
		query = "SELECT count(*) FROM farmer_club.user where not winsafeid is null";
		rs = statement.executeQuery(query);
		if (rs.next()){
			try{
				int i = rs.getInt("count(*)");
				result.append("BVA商户总数: "+i+"\r\n");
			}catch (Exception e){
					
			}
		}
		rs.close();
		
		
		query = "SELECT count(*) FROM farmer_club.user where not winsafeid is null and createAt!=lastLogin";
		rs = statement.executeQuery(query);
		if (rs.next()){
			try{
				int i = rs.getInt("count(*)");
				result.append("激活BVA商户总数: "+i+"\r\n");
			}catch (Exception e){
					
			}
		}
		rs.close();
		//巴斯夫活动情况
		return result.toString();
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		String content = generateReport(2016,6,3);
		System.out.println(content);
		

		//MailHelper mh = new MailHelper();
		//mh.sendMail("coordinate6467@hotmail.com", "大户俱乐部2016年1月月报", content);


	}
}
