package com.chinesedreamer.jira.biz.core.version.logic.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.base.logic.impl.BaseLogicImpl;
import com.chinesedreamer.jira.biz.core.version.logic.JiraVersionLogic;
import com.chinesedreamer.jira.biz.core.version.model.JiraVersion;
import com.chinesedreamer.jira.biz.core.version.repository.JiraVersionRepository;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20158:58:33 AM
 * @version beta
 */
@Service
public class JiraVersionLogicImpl extends BaseLogicImpl<JiraVersion, Long> implements JiraVersionLogic{
	@Resource
	private JiraVersionRepository repository;

	@Override
	public JiraVersion findByJiraId(String jiraId) {
		return this.repository.findByJiraId(jiraId);
	}

}
