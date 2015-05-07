package com.chinesedreamer.jira.biz.rptconfig.logic.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.base.logic.impl.BaseLogicImpl;
import com.chinesedreamer.jira.biz.rptconfig.logic.JiraRptVersionConfigLogic;
import com.chinesedreamer.jira.biz.rptconfig.model.JiraRptVersionConfig;
import com.chinesedreamer.jira.biz.rptconfig.repository.JiraRptVersionConfigRepository;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20154:12:01 PM
 * @version beta
 */
@Service
public class JiraRptVersionConfigLogicImpl extends BaseLogicImpl<JiraRptVersionConfig, Long> implements JiraRptVersionConfigLogic{
	@Resource
	private JiraRptVersionConfigRepository repository;

	@Override
	public List<JiraRptVersionConfig> findByProjectJiraId(String projectJiraId) {
		return this.repository.findByProjectJiraId(projectJiraId);
	}

	@Override
	public List<JiraRptVersionConfig> findAll() {
		return this.repository.findAll();
	}

	@Override
	public JiraRptVersionConfig findByProjectJiraIdAndVersionJiraId(
			String projectJiraId, String versionJiraId) {
		return this.repository.findByProjectJiraIdAndVersionJiraId(projectJiraId, versionJiraId);
	}

}
