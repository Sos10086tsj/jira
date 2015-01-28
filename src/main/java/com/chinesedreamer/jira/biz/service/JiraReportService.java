package com.chinesedreamer.jira.biz.service;

import java.util.List;

import com.chinesedreamer.jira.biz.vo.ReportTaskAssigneeVo;
import com.chinesedreamer.jira.biz.vo.ReportTaskSummaryVo;
import com.chinesedreamer.jira.biz.vo.ReportTaskVo;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.greenhopper.RapidView;
import com.chinesedreamer.jira.core.greenhopper.Sprint;

/**
 * Description: 
 * @author Paris
 * @date Jan 20, 20158:30:39 AM
 * @version beta
 */
public interface JiraReportService {
	public List<ReportTaskVo> generateDailyReport(int rapidViewId, int sprintId, String templateCode) throws JiraException;
	
	public List<RapidView> loadRapidViews() throws JiraException;
	
	public List<Sprint> loadRapidViewSprints(int rapidViewId) throws JiraException;
	
	
	/**
	 * 统计分析report
	 * @param reportTaskVos
	 * @param templateCode
	 * @return
	 */
	public ReportTaskSummaryVo analyzeSprit(List<ReportTaskVo> reportTaskVos, String templateCode);
	
	/**
	 * 开发统计报告
	 * @param rapidViewId
	 * @param sprintId
	 * @param templateCode
	 * @return
	 * @throws JiraException
	 */
	public List<ReportTaskAssigneeVo> generateAssigneeReport(int rapidViewId, int sprintId, String templateCode) throws JiraException;
}
