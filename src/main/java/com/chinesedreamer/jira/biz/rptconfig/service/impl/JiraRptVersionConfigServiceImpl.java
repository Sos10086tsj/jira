package com.chinesedreamer.jira.biz.rptconfig.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.biz.core.project.logic.JiraProjectLogic;
import com.chinesedreamer.jira.biz.core.version.logic.JiraVersionLogic;
import com.chinesedreamer.jira.biz.rptconfig.logic.JiraRptVersionConfigLogic;
import com.chinesedreamer.jira.biz.rptconfig.model.JiraRptVersionConfig;
import com.chinesedreamer.jira.biz.rptconfig.service.JiraRptVersionConfigService;
import com.chinesedreamer.jira.biz.vo.JiraRptVersionConfigVo;

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
	@Resource
	private JiraProjectLogic jiraProjectLogic;
	@Resource
	private JiraVersionLogic jiraVersionLogic;

	@Override
	public List<JiraRptVersionConfig> findByProjectJiraId(String projectJiraId) {
		return this.logic.findByProjectJiraId(projectJiraId);
	}

	@Override
	public List<JiraRptVersionConfig> getAll() {
		return this.logic.findAll();
	}

	@Override
	public void addConfig(String projectJiraId, String versionJiraId) {
		JiraRptVersionConfig jiraRptVersionConfig = new JiraRptVersionConfig();
		jiraRptVersionConfig.setProjectJiraId(projectJiraId);
		jiraRptVersionConfig.setVersionJiraId(versionJiraId);
		this.logic.save(jiraRptVersionConfig);
	}

	@Override
	public void deleteConfig(String projectJiraId, String versionJiraId) {
		JiraRptVersionConfig jiraRptVersionConfig = this.logic.findByProjectJiraIdAndVersionJiraId(projectJiraId, versionJiraId);
		this.logic.delete(jiraRptVersionConfig);
	}

	@Override
	public List<JiraRptVersionConfigVo> getAllVos() {
		List<JiraRptVersionConfigVo> vos = new ArrayList<JiraRptVersionConfigVo>();
		List<JiraRptVersionConfig> configs = this.logic.findAll();
		for (JiraRptVersionConfig config : configs) {
			vos.add(this.convert2Vo(config));
		}
		return vos;
	}
	
	private JiraRptVersionConfigVo convert2Vo(JiraRptVersionConfig config) {
		JiraRptVersionConfigVo vo = new JiraRptVersionConfigVo();
		vo.setProjectJiraId(config.getProjectJiraId());
		vo.setVersionJiraId(config.getVersionJiraId());
		vo.setProjectJiraName(this.jiraProjectLogic.findByJiraId(config.getProjectJiraId()).getName());
		vo.setVersionJiraName(this.jiraVersionLogic.findByJiraId(config.getVersionJiraId()).getName());
		return vo;
	}
}
