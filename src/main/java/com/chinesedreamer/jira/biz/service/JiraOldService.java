package com.chinesedreamer.jira.biz.service;

import java.util.List;

import com.chinesedreamer.jira.core.Issue;
import com.chinesedreamer.jira.core.IssueType;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.Project;
import com.chinesedreamer.jira.core.Version;
import com.chinesedreamer.jira.curd.vo.CrudOldVo;

/**
 * Description: 
 * @author Paris
 * @date Jan 13, 201510:08:51 AM
 * @version beta
 */
public interface JiraOldService {
	public List<Project> loadProjects() throws JiraException;
	public List<IssueType> loadIssueTypes() throws JiraException;
	public List<Version> loadProjectVersions(String projectKey) throws JiraException;
	//public Map<String, String> loadProjectRoles(String projectKey) throws JiraException;
	
	public List<Issue> createIssues(String project, String issueType, String version, List<CrudOldVo> vos, String templateCode) throws JiraException;
	public List<Issue> showIssues(String[] issueKeys) throws JiraException;
}
