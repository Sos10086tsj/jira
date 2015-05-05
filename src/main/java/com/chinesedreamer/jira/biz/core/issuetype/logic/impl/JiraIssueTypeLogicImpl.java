package com.chinesedreamer.jira.biz.core.issuetype.logic.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.base.logic.impl.BaseLogicImpl;
import com.chinesedreamer.jira.biz.core.issuetype.logic.JiraIssueTypeLogic;
import com.chinesedreamer.jira.biz.core.issuetype.model.JiraIssueType;
import com.chinesedreamer.jira.biz.core.issuetype.repository.JiraIssueTypeRepository;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:36:31 PM
 * @version beta
 */
@Service
public class JiraIssueTypeLogicImpl extends BaseLogicImpl<JiraIssueType, Long> implements JiraIssueTypeLogic{
	@Resource
	private JiraIssueTypeRepository repository;

	@Override
	public JiraIssueType findByJiraId(String jiraId) {
		return this.repository.findByJiraId(jiraId);
	}

}
