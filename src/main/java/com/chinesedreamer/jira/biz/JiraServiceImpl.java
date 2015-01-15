package com.chinesedreamer.jira.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.core.BasicCredentials;
import com.chinesedreamer.jira.core.Field;
import com.chinesedreamer.jira.core.Issue;
import com.chinesedreamer.jira.core.IssueType;
import com.chinesedreamer.jira.core.JiraClient;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.Project;
import com.chinesedreamer.jira.core.Version;
import com.chinesedreamer.jira.curd.vo.CrudVo;
import com.chinesedreamer.jira.reader.PropertiesReader;

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
	
	private final static Map<String, String> devMap = new HashMap<String, String>();
	static{
		devMap.put("yanghui", "【开发任务】");
		devMap.put("laiting", "【开发任务】");
		devMap.put("xiexp", "【android】");
		devMap.put("fanyn", "【ios】");
		devMap.put("taosj", "【开发任务】");
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
	public List<Issue> createIssues(String project, String issueType, String version, List<CrudVo> vos) throws JiraException {
		List<Issue> newIssues = new ArrayList<Issue>();

		List<String> versions = new ArrayList< String>();
		versions.add(version);
		
		for (CrudVo vo : vos) {
			//1. get parent issue
			Issue parentIssue = this.getJiraClient().getIssue(vo.getParentIssueKey());
			//2. create develop task
			if (null != vo.getDevelopers() && vo.getDevelopers().length != 0) {
				for (String dev : vo.getDevelopers()) {
					Issue newIssue = this.getJiraClient().createIssue(project, issueType)
							.field(Field.SUMMARY, devMap.get(dev) + parentIssue.getSummary())
							.field(Field.PRIORITY, Field.valueById("3"))
							.field(Field.ASSIGNEE, dev)
							.field(Field.FIX_VERSIONS, versions)
							.execute();
					parentIssue.link(newIssue.getKey(), "包含");
					newIssues.add(newIssue);
				}
			}
			//3. create qa task
			if (null != vo.getQas() && vo.getQas().length != 0) {
				for (String qa : vo.getQas()) {
					Issue newIssue = this.getJiraClient().createIssue(project, issueType)
							.field(Field.SUMMARY, "【测试任务】" + parentIssue.getSummary())
							.field(Field.PRIORITY, Field.valueById("3"))
							.field(Field.ASSIGNEE, qa)
							.field(Field.FIX_VERSIONS, versions)
							.execute();
					parentIssue.link(newIssue.getKey(), "包含");
					newIssues.add(newIssue);
				}
			}
		}

		return newIssues;
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

	/*
	@Override
	public Map<String, String> loadProjectRoles(String projectKey) throws JiraException {
		return this.getJiraClient().getProject(projectKey).getRoles();
	}*/
}
