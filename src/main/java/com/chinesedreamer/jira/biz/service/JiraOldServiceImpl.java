package com.chinesedreamer.jira.biz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.core.BasicCredentials;
import com.chinesedreamer.jira.core.Issue;
import com.chinesedreamer.jira.core.IssueType;
import com.chinesedreamer.jira.core.JiraClient;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.Project;
import com.chinesedreamer.jira.core.Version;
import com.chinesedreamer.jira.curd.vo.CrudOldVo;
import com.chinesedreamer.jira.reader.PropertiesReader;

/**
 * Description:
 * 
 * @author Paris
 * @date Jan 13, 20151:00:05 PM
 * @version beta
 */
@Service
public class JiraOldServiceImpl implements JiraOldService {
	private JiraClient jiraClient;
	
	private final static Map<String, String> devMap = new HashMap<String, String>();
	static{
		devMap.put("yanghui", "【开发任务】");
		devMap.put("laiting", "【开发任务】");
		devMap.put("xiexp", "【android】");
		devMap.put("fanyn", "【ios】");
		devMap.put("taosj", "【开发任务】");
		devMap.put("chenliang", "【开发任务】");
	}

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
	public List<Issue> createIssues(String project, String issueType, String version, List<CrudOldVo> vos, String templateCode) throws JiraException {
		return JiraOldTemplateFactory.generateCreator(templateCode, this.getJiraClient()).createIssues(project, issueType, version, vos);
	}

	@Override
	public List<Issue> showIssues(String[] issueKeys) throws JiraException {
		List<Issue> issues = new ArrayList<Issue>();
		for (String key : issueKeys) {
			if (StringUtils.isNotEmpty(key)) {
				Issue issue = this.getJiraClient().getIssue(key.trim());
				issues.add(issue);
			}
		}
		return issues;
	}
}
