package com.chinesedreamer.jira.biz.rptconfig.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.biz.rptconfig.logic.JiraRptVersionConfigLogic;
import com.chinesedreamer.jira.biz.rptconfig.model.JiraRptVersionConfig;
import com.chinesedreamer.jira.biz.rptconfig.service.JiraRptVersionConfigService;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20154:14:10 PM
 * @version beta
 */
@Service
public class JiraRptVersionConfigServiceImpl implements JiraRptVersionConfigService{
	@Resource
	private JiraRptVersionConfigLogic logic;

	@Override
	public List<JiraRptVersionConfig> findByProjectJiraId(String projectJiraId) {
		return this.logic.findByProjectJiraId(projectJiraId);
	}

	@Override
	public List<JiraRptVersionConfig> getAll() {
		return this.logic.findAll();
	}

}
