package com.chinesedreamer.jira.biz.rptconfig.repository;

import java.util.List;

import com.chinesedreamer.jira.base.repository.BaseRepository;
import com.chinesedreamer.jira.biz.rptconfig.model.JiraRptVersionConfig;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20154:10:12 PM
 * @version beta
 */
public interface JiraRptVersionConfigRepository extends BaseRepository<JiraRptVersionConfig, Long>{
	public List<JiraRptVersionConfig> findByProjectJiraId(String projectJiraId);
	public JiraRptVersionConfig findByProjectJiraIdAndVersionJiraId(String projectJiraId, String versionJiraId);
}
