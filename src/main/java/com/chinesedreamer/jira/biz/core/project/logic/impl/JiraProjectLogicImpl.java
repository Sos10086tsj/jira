package com.chinesedreamer.jira.biz.core.project.logic.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.base.logic.impl.BaseLogicImpl;
import com.chinesedreamer.jira.biz.core.project.logic.JiraProjectLogic;
import com.chinesedreamer.jira.biz.core.project.model.JiraProject;
import com.chinesedreamer.jira.biz.core.project.repository.JiraProjectRepository;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:12:23 PM
 * @version beta
 */
@Service
public class JiraProjectLogicImpl extends BaseLogicImpl<JiraProject, Long> implements JiraProjectLogic{
	@Resource
	private JiraProjectRepository repository;
	@Override
	public JiraProject findByKey(String key) {
		return this.repository.findByKey(key);
	}

}
