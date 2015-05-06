package com.chinesedreamer.jira.biz.core.issue.logic;

import java.util.List;

import com.chinesedreamer.jira.base.logic.BaseLogic;
import com.chinesedreamer.jira.biz.core.issue.model.JiraIssueVersion;

/**
 * Description: 
 * @author Paris
 * @date May 6, 201510:21:09 AM
 * @version beta
 */
public interface JiraIssueVersionLogic extends BaseLogic<JiraIssueVersion, Long>{
	public List<JiraIssueVersion> findByProjectJiraIdAndVersionJiraId(String projectJiraId, String versionJiraId);
	public List<JiraIssueVersion> findByProjectJiraIdAndIssueJiraId(String projectJiraId, String issueJiraId);
	public JiraIssueVersion findByProjectJiraIdAndVersionJiraIdAndIssueJiraId(String projectJiraId, String versionJiraId, String issueJiraId);
}
