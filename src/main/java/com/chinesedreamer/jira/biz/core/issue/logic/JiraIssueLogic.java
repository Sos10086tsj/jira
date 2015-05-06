package com.chinesedreamer.jira.biz.core.issue.logic;

import com.chinesedreamer.jira.base.logic.BaseLogic;
import com.chinesedreamer.jira.biz.core.issue.model.JiraIssue;

/**
 * Description: 
 * @author Paris
 * @date May 4, 20154:44:01 PM
 * @version beta
 */
public interface JiraIssueLogic extends BaseLogic<JiraIssue, Long>{
	public JiraIssue findByJiraId(String jiraId);
}
