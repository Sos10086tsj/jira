package com.chinesedreamer.jira.biz.core.priority.logic.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.base.logic.impl.BaseLogicImpl;
import com.chinesedreamer.jira.biz.core.priority.logic.JiraPriorityLogic;
import com.chinesedreamer.jira.biz.core.priority.model.JiraPriority;
import com.chinesedreamer.jira.biz.core.priority.repository.JiraPriorityRepository;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:43:44 PM
 * @version beta
 */
@Service
public class JiraPriorityLogicImpl extends BaseLogicImpl<JiraPriority, Long> implements JiraPriorityLogic{
	@Resource
	private JiraPriorityRepository repository;
	@Override
	public JiraPriority findByJiraId(String jiraId) {
		return this.repository.findByJiraId(jiraId);
	}

}
