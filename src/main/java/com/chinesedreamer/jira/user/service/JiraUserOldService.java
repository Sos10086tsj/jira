package com.chinesedreamer.jira.user.service;

import java.util.List;

import com.chinesedreamer.jira.user.dto.JiraUserOld;

/**
 * Description: 
 * @author Paris
 * @date Jan 13, 201510:13:38 AM
 * @version beta
 */
public interface JiraUserOldService {
	public List<JiraUserOld> getAllUsers();
	public JiraUserOld getUser(String username);
}
