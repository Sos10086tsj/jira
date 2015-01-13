package com.chinesedreamer.jira.user.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.chinesedreamer.jira.base.SpringTest;
import com.chinesedreamer.jira.user.dto.JiraUser;

/**
 * Description: 
 * @author Paris
 * @date Jan 13, 201511:38:22 AM
 * @version beta
 */
public class JiraUserServiceImplTest extends SpringTest{

	@Resource
	private JiraUserService jiraUserService;
	@Test
	public void testGetAllUsers() {
		List<JiraUser> jiraUsers = this.jiraUserService.getAllUsers();
		assertNotNull(jiraUsers);
		for (JiraUser jiraUser : jiraUsers) {
			System.out.println(jiraUser);
		}
	}

	@Test
	public void testGetUser() {
		JiraUser jiraUser = this.jiraUserService.getUser("taosj");
		assertNotNull(jiraUser);
		System.out.println(jiraUser);
	}

}
