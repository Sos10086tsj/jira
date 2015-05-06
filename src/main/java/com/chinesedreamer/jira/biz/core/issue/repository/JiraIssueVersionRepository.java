package com.chinesedreamer.jira.biz.core.issue.repository;

import java.util.List;

import com.chinesedreamer.jira.base.repository.BaseRepository;
import com.chinesedreamer.jira.biz.core.issue.model.JiraIssueVersion;

/**
 * Description: 
 * @author Paris
 * @date May 6, 201510:20:54 AM
 * @version beta
 */
public interface JiraIssueVersionRepository extends BaseRepository<JiraIssueVersion, Long>{
	public List<JiraIssueVersion> findByProjectJiraIdAndVersionJiraId(String projectJiraId, String versionJiraId);
	public List<JiraIssueVersion> findByProjectJiraIdAndIssueJiraId(String projectJiraId, String issueJiraId);
	public JiraIssueVersion findByProjectJiraIdAndVersionJiraIdAndIssueJiraId(String projectJiraId, String versionJiraId, String issueJiraId);
}
