package com.chinesedreamer.jira.biz.core.user.logic.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.base.logic.impl.BaseLogicImpl;
import com.chinesedreamer.jira.biz.core.user.logic.JiraUserLogic;
import com.chinesedreamer.jira.biz.core.user.model.JiraUser;
import com.chinesedreamer.jira.biz.core.user.repository.JiraUserRepository;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:07:18 PM
 * @version beta
 */
@Service
public class JiraUserLogicImpl extends BaseLogicImpl<JiraUser, Long> implements JiraUserLogic{
	@Resource
	private JiraUserRepository repository;
	@Override
	public JiraUser findByUsername(String username) {
		return this.repository.findByUsername(username);
	}

}
