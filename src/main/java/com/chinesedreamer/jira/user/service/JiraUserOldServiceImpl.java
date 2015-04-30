package com.chinesedreamer.jira.user.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chinesedreamer.jira.reader.JsonReader;
import com.chinesedreamer.jira.user.dto.JiraUserOld;

/**
 * Description: 
 * @author Paris
 * @date Jan 13, 201510:27:12 AM
 * @version beta
 */
@Service
public class JiraUserOldServiceImpl implements JiraUserOldService{

	@SuppressWarnings("unchecked")
	@Override
	public List<JiraUserOld> getAllUsers() {
		JSONObject jsonUsers = JsonReader.readJsonFile("datasource/jira-users.json");
		List<JiraUserOld> users = null;
		try {
			users = (ArrayList<JiraUserOld>)JsonReader.readJsonObject2Object(jsonUsers, JiraUserOld.class, "jiraUsers");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public JiraUserOld getUser(String username) {
		List<JiraUserOld> users = this.getAllUsers();
		for (JiraUserOld jiraUser : users) {
			if (username.equals(jiraUser.getUsername())) {
				return jiraUser;
			}
		}
		return null;
	}

}
