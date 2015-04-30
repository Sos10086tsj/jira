package com.chinesedreamer.jira.biz.service;

import java.util.List;

import com.chinesedreamer.jira.core.Issue;
import com.chinesedreamer.jira.core.JiraClient;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.curd.vo.CrudOldVo;

/**
 * Description: 
 * @author Paris
 * @date Jan 16, 20153:07:14 PM
 * @version beta
 */
public abstract class JiraCreatorOldService {
	protected JiraClient jiraClient;
	public JiraCreatorOldService(JiraClient jiraClient){
		this.jiraClient = jiraClient;
	}
	public abstract List<Issue> createIssues(String project, String issueType, String version, List<CrudOldVo> vos) throws JiraException;
}
