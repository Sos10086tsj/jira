package com.chinesedreamer.jira.biz.jsonvo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinesedreamer.jira.reader.JsonReader;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: 
 * @author Paris
 * @date Jan 16, 20152:59:55 PM
 * @version beta
 */
public @Getter @Setter @ToString class TemplateCode {
	private String name;
	
	public static List<TemplateCode> loadDataSource(){
		List<TemplateCode> templateCodes = new ArrayList<TemplateCode>();
		String path = "datasource/template-code.json";
		JSONObject jsonObject = JsonReader.readJsonFile(path);
		JSONArray jsonArray = jsonObject.getJSONArray("templateCode");
		for (Object object : jsonArray) {
			JSONObject obj = (JSONObject) object;
			TemplateCode templateCode = new TemplateCode();
			templateCode.setName(obj.getString("name"));
			templateCodes.add(templateCode);
		}
		return templateCodes;
	}
}
