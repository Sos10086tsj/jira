package com.chinesedreamer.jira.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: 
 * @author Paris
 * @date Jan 13, 201510:12:24 AM
 * @version beta
 */
public @Getter @Setter @ToString class JiraUser {
	private String username;
	private String password;
}
