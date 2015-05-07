package com.chinesedreamer.jira.biz.rptconfig.service;

import java.util.List;

import com.chinesedreamer.jira.biz.rptconfig.model.JiraRptVersionConfig;
import com.chinesedreamer.jira.biz.vo.JiraRptVersionConfigVo;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20154:13:57 PM
 * @version beta
 */
public interface JiraRptVersionConfigService {
	public List<JiraRptVersionConfig> findByProjectJiraId(String projectJiraId);
	public List<JiraRptVersionConfig> getAll();
	public void addConfig(String projectJiraId,String versionJiraId);
	public void deleteConfig(String projectJiraId,String versionJiraId);
	public List<JiraRptVersionConfigVo> getAllVos();
}
