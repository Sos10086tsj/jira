package com.chinesedreamer.jira.biz.jsonvo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
 * @date Jan 16, 20153:39:04 PM
 * @version beta
 */
public @Getter @Setter @ToString class AssigneeOld {
	private String username;
	private String name;
	
	private final static String[] types = {"ba","product","developer","qa"};
	
	public static AssigneesOld loadDataSource(String templateCode){
		AssigneesOld assignees = new AssigneesOld();
		String path = "datasource/assignee-" + templateCode + ".json";
		JSONObject jsonObject = JsonReader.readJsonFile(path);
		for (String type : types) {
			JSONArray typeJsonArray = jsonObject.getJSONObject("assignee").getJSONArray(type);
			if (null != typeJsonArray && !typeJsonArray.isEmpty()) {
				List<AssigneeOld> assigneeList = new ArrayList<AssigneeOld>();
				for (Object object : typeJsonArray) {
					JSONObject obj = (JSONObject)object;
					AssigneeOld assignee = new AssigneeOld();
					assignee.setName(obj.getString("name"));
					assignee.setUsername(obj.getString("username"));
					assigneeList.add(assignee);
				}
				for (Method method : AssigneesOld.class.getDeclaredMethods()) {
					String methodName = method.getName();
					int length = "set".length();
					if (methodName.startsWith("set") && methodName.length() > (length + type.length()) && methodName.substring(length, length + type.length()).toLowerCase().equals(type)) {
						try {
							method.invoke(assignees, assigneeList);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return assignees;
	}
}
