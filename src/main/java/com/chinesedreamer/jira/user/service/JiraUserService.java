package com.chinesedreamer.jira.user.service;

import java.util.List;

import com.chinesedreamer.jira.user.dto.JiraUser;

/**
 * Description: 
 * @author Paris
 * @date Jan 13, 201510:13:38 AM
 * @version beta
 */
public interface JiraUserService {
	public List<JiraUser> getAllUsers();
	public JiraUser getUser(String username);
}
