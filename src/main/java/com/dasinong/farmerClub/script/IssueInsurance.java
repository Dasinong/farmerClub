package com.dasinong.farmerClub.script;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dasinong.farmerClub.model.User;

public class IssueInsurance {
	public List<User> sendMessage(Double lat, Double lon){
		List<User> users = new ArrayList<User>();
		return users;
	}
	
	public List<User> sendMessage(String city) throws ClassNotFoundException, SQLException{
		List<User> users = new ArrayList<User>();
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:MySQL://120.26.208.198:3306/farmer_club";
		
		String user="root";
		String password="weather123";
		
		try {
			Connection con= DriverManager.getConnection(url,user,password);
			String sql = "{call getInsuranceUserList (?,?)}";
			CallableStatement statement=con.prepareCall(sql);
			statement.setLong(1, 3L);
			statement.setString(2, city);
			ResultSet rs = statement.executeQuery();
		    
		    while (rs.next()){
		    	System.out.println(rs.getString("ownerId"));
		   }
		}
		catch (SQLException e) {
		   int i=1;
		}
		finally {
		  
		}
		
	
		return users;
	}
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException{
		IssueInsurance ii = new IssueInsurance();
		ii.sendMessage("上海");
	}
	
	
}
