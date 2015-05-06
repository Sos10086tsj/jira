package com.chinesedreamer.jira.biz.core.status.logic.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.base.logic.impl.BaseLogicImpl;
import com.chinesedreamer.jira.biz.core.status.logic.JiraStatusLogic;
import com.chinesedreamer.jira.biz.core.status.model.JiraStatus;
import com.chinesedreamer.jira.biz.core.status.repository.JiraStatusRepository;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:48:18 PM
 * @version beta
 */
@Service
public class JiraStatusLogicImpl extends BaseLogicImpl<JiraStatus, Long> implements JiraStatusLogic{
	@Resource
	private JiraStatusRepository repository;

	@Override
	public JiraStatus findByJiraId(String jiraId) {
		return this.repository.findByJiraId(jiraId);
	}

}
