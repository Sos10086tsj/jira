package com.chinesedreamer.jira.biz.core.issuetype.logic;

import com.chinesedreamer.jira.base.logic.BaseLogic;
import com.chinesedreamer.jira.biz.core.issuetype.model.JiraIssueType;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:36:04 PM
 * @version beta
 */
public interface JiraIssueTypeLogic extends BaseLogic<JiraIssueType, Long>{
	public JiraIssueType findByJiraId(String jiraId);
}
