package com.chinesedreamer.jira.biz;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.core.BasicCredentials;
import com.chinesedreamer.jira.core.Issue;
import com.chinesedreamer.jira.core.IssueType;
import com.chinesedreamer.jira.core.JiraClient;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.Project;
import com.chinesedreamer.jira.core.Version;
import com.chinesedreamer.jira.reader.PropertiesReader;
import com.chinesedreamer.jira.user.dto.JiraUser;

/**
 * Description:
 * 
 * @author Paris
 * @date Jan 13, 20151:00:05 PM
 * @version beta
 */
@Service
public class JiraServiceImpl implements JiraService {
	private JiraClient jiraClient;
	

	private JiraClient getJiraClient() {
		if (null == this.jiraClient) {
			Properties prop = PropertiesReader.loadProperties();
			String url = prop.getProperty("jira.connection.url");
			String username = prop.getProperty("jira.connection.username");
			String password = prop.getProperty("jira.connection.password");
			jiraClient = new JiraClient(url, new BasicCredentials(username, password));
		}
		return jiraClient;
	}

	@Override
	public List<Project> loadProjects() throws JiraException {
		return this.getJiraClient().getProjects();
	}

	@Override
	public List<Version> loadProjectVersions(String projectKey) throws JiraException {
		return this.getJiraClient().getProject(projectKey).getVersions();
	}

	@Override
	public List<IssueType> loadIssueTypes() throws JiraException {
		return this.getJiraClient().getIssueTypes();
	}

	@Override
	public Map<String, String> loadProjectRoles(String projectKey)
			throws JiraException {
		return this.getJiraClient().getProject(projectKey).getRoles();
	}
}
