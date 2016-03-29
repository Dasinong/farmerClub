package com.dasinong.farmerClub.script;

import java.io.IOException;
import java.util.HashMap;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import com.dasinong.farmerClub.util.WinsafeUtil;

public class ImportBVAAccount {
	public static void main(String args[]) throws JsonProcessingException, IOException{
		WinsafeUtil winsafe = new WinsafeUtil();
		String winSafeResult = winsafe.getCustInfo("");
		int totalcount=0;
		int wrongNumbercount=0;
		int noNumbercount=0;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(winSafeResult);
		JsonNode returnCode = root.get("returnCode");
		if (returnCode.getIntValue() == 0){
			
			JsonNode returnData = root.get("returnData");
			for( JsonNode jn : returnData){
				
				String cellphone = jn.get("phone").getTextValue();
				if (cellphone.length()!=11 || cellphone.charAt(0)!='1'){
					System.out.print(jn.get("contactname").getTextValue()+": ");
					wrongNumbercount++;
					System.out.println(cellphone);
				}
				if (cellphone.length()==0){
					noNumbercount++;
				}
				totalcount++;
			};
			System.out.println(totalcount);
			System.out.println(wrongNumbercount);
			System.out.println(noNumbercount);
		}
	}
}
