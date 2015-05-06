package com.chinesedreamer.jira.biz.service;

import com.chinesedreamer.jira.biz.vo.ProjectReportVo;

/**
 * Description: jira报告相关
 * @author Paris
 * @date May 4, 20154:45:23 PM
 * @version beta
 */
public interface JiraReportService {
	/**
	 * 生成项目报告
	 * @param projectJiraId
	 * @param versionJiraid
	 * @return
	 */
	public ProjectReportVo generateProjectReport(String projectJiraId, String versionJiraId);
}
