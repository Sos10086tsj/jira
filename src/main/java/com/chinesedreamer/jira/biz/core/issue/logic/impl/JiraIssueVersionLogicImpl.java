package com.chinesedreamer.jira.biz.core.issue.logic.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.base.logic.impl.BaseLogicImpl;
import com.chinesedreamer.jira.biz.core.issue.logic.JiraIssueVersionLogic;
import com.chinesedreamer.jira.biz.core.issue.model.JiraIssueVersion;
import com.chinesedreamer.jira.biz.core.issue.repository.JiraIssueVersionRepository;

/**
 * Description: 
 * @author Paris
 * @date May 6, 201510:21:30 AM
 * @version beta
 */
@Service
public class JiraIssueVersionLogicImpl extends BaseLogicImpl<JiraIssueVersion, Long> implements JiraIssueVersionLogic{
	@Resource
	private JiraIssueVersionRepository repository;

	@Override
	public List<JiraIssueVersion> findByProjectJiraIdAndVersionJiraId(
			String projectJiraId, String versionJiraId) {
		return this.repository.findByProjectJiraIdAndVersionJiraId(projectJiraId, versionJiraId);
	}

	@Override
	public JiraIssueVersion findByProjectJiraIdAndVersionJiraIdAndIssueJiraId(
			String projectJiraId, String versionJiraId, String issueJiraId) {
		return this.repository.findByProjectJiraIdAndVersionJiraIdAndIssueJiraId(projectJiraId, versionJiraId, issueJiraId);
	}

	@Override
	public List<JiraIssueVersion> findByProjectJiraIdAndIssueJiraId(
			String projectJiraId, String issueJiraId) {
		return this.repository.findByProjectJiraIdAndIssueJiraId(projectJiraId, issueJiraId);
	}
	
}
