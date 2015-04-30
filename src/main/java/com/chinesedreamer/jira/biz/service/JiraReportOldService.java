package com.chinesedreamer.jira.biz.service;

import java.util.List;

import com.chinesedreamer.jira.biz.vo.ReportTaskAssigneeOldVo;
import com.chinesedreamer.jira.biz.vo.ReportTaskSummaryOldVo;
import com.chinesedreamer.jira.biz.vo.ReportTaskOldVo;
import com.chinesedreamer.jira.biz.vo.ReportOldVo;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.greenhopper.RapidView;
import com.chinesedreamer.jira.core.greenhopper.Sprint;

/**
 * Description: 
 * @author Paris
 * @date Jan 20, 20158:30:39 AM
 * @version beta
 */
public interface JiraReportOldService {
	public List<ReportTaskOldVo> generateDailyReport(int rapidViewId, int sprintId, String templateCode) throws JiraException;
	
	public List<RapidView> loadRapidViews() throws JiraException;
	
	public List<Sprint> loadRapidViewSprints(int rapidViewId) throws JiraException;
	
	
	/**
	 * 统计分析report
	 * @param reportTaskVos
	 * @param templateCode
	 * @return
	 */
	public ReportTaskSummaryOldVo analyzeSprit(List<ReportTaskOldVo> reportTaskVos, String templateCode);
	
	/**
	 * 开发统计报告
	 * @param rapidViewId
	 * @param sprintId
	 * @param templateCode
	 * @return
	 * @throws JiraException
	 */
	public List<ReportTaskAssigneeOldVo> generateAssigneeReport(int rapidViewId, int sprintId, String templateCode) throws JiraException;
	
	public List<ReportTaskOldVo> generateSprintReport(int rapidViewId,int sprintId, String templateCode) throws JiraException;
	
	/**
	 * 生成每日report
	 * @return
	 */
	public List<ReportOldVo> generateDailyReport() throws JiraException;
}
