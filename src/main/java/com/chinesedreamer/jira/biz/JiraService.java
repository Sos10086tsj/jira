package com.chinesedreamer.jira.biz;

import java.util.List;
import java.util.Map;

import com.chinesedreamer.jira.core.IssueType;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.Project;
import com.chinesedreamer.jira.core.Version;
import com.chinesedreamer.jira.user.dto.JiraUser;

/**
 * Description: 
 * @author Paris
 * @date Jan 13, 201510:08:51 AM
 * @version beta
 */
public interface JiraService {
	public List<Project> loadProjects() throws JiraException;
	public List<IssueType> loadIssueTypes() throws JiraException;
	public List<Version> loadProjectVersions(String projectKey) throws JiraException;
	public Map<String, String> loadProjectRoles(String projectKey) throws JiraException;
}
