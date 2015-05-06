package com.chinesedreamer.jira.biz.core.issue.logic.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.base.logic.impl.BaseLogicImpl;
import com.chinesedreamer.jira.biz.core.issue.logic.JiraIssueLogic;
import com.chinesedreamer.jira.biz.core.issue.model.JiraIssue;
import com.chinesedreamer.jira.biz.core.issue.repository.JiraIssueRepository;

/**
 * Description: 
 * @author Paris
 * @date May 4, 20154:44:31 PM
 * @version beta
 */
@Service
public class JiraIssueLogicImpl extends BaseLogicImpl<JiraIssue, Long> implements JiraIssueLogic{
	@Resource
	private JiraIssueRepository repository;

	@Override
	public JiraIssue findByJiraId(String jiraId) {
		return this.repository.findByJiraId(jiraId);
	}

}
