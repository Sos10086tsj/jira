package com.chinesedreamer.jira.biz.service;

import java.util.List;

import com.chinesedreamer.jira.core.Issue;
import com.chinesedreamer.jira.core.JiraClient;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.curd.vo.CrudVo;

/**
 * Description: 
 * @author Paris
 * @date Jan 16, 20153:07:14 PM
 * @version beta
 */
public abstract class JiraCreatorService {
	protected JiraClient jiraClient;
	public JiraCreatorService(JiraClient jiraClient){
		this.jiraClient = jiraClient;
	}
	public abstract List<Issue> createIssues(String project, String issueType, String version, List<CrudVo> vos) throws JiraException;
}
