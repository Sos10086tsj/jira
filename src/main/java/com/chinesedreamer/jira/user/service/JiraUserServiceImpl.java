package com.chinesedreamer.jira.user.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chinesedreamer.jira.reader.JsonReader;
import com.chinesedreamer.jira.user.dto.JiraUser;

/**
 * Description: 
 * @author Paris
 * @date Jan 13, 201510:27:12 AM
 * @version beta
 */
@Service
public class JiraUserServiceImpl implements JiraUserService{

	@SuppressWarnings("unchecked")
	@Override
	public List<JiraUser> getAllUsers() {
		JSONObject jsonUsers = JsonReader.readJsonFile("datasource/jira-users.json");
		List<JiraUser> users = null;
		try {
			users = (ArrayList<JiraUser>)JsonReader.readJsonObject2Object(jsonUsers, JiraUser.class, "jiraUsers");
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
	public JiraUser getUser(String username) {
		List<JiraUser> users = this.getAllUsers();
		for (JiraUser jiraUser : users) {
			if (username.equals(jiraUser.getUsername())) {
				return jiraUser;
			}
		}
		return null;
	}

}
