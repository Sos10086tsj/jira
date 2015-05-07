package com.chinesedreamer.jira.biz.service;

import java.util.Date;

import com.chinesedreamer.jira.biz.vo.ProjectReportVo;
import com.chinesedreamer.jira.biz.vo.TimeScopeReportVo;

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
	
	/**
	 * 某段时间内的项目整体进度报告，用户详细进度报告
	 * @param start
	 * @param end
	 * @return
	 */
	public TimeScopeReportVo generateTimeScopeReport(Date start, Date end);
}
