package com.chinesedreamer.jira.biz.core.project.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.biz.core.project.logic.JiraProjectLogic;
import com.chinesedreamer.jira.biz.core.project.model.JiraProject;
import com.chinesedreamer.jira.biz.core.project.service.JiraProjectService;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20159:08:48 AM
 * @version beta
 */
@Service
public class JiraProjectServiceImpl implements JiraProjectService{
	@Resource
	private JiraProjectLogic logic;

	@Override
	public List<JiraProject> getAllProjects() {
		return this.logic.findAll();
	}

}
