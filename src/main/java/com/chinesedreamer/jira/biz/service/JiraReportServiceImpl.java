package com.chinesedreamer.jira.biz.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chinesedreamer.jira.biz.vo.ReportTaskAssigneeVo;
import com.chinesedreamer.jira.biz.vo.ReportTaskSummaryVo;
import com.chinesedreamer.jira.biz.vo.ReportTaskVo;
import com.chinesedreamer.jira.biz.vo.ReportTaskVoComparator;
import com.chinesedreamer.jira.core.BasicCredentials;
import com.chinesedreamer.jira.core.Issue;
import com.chinesedreamer.jira.core.IssueLink;
import com.chinesedreamer.jira.core.JiraClient;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.greenhopper.GreenHopperClient;
import com.chinesedreamer.jira.core.greenhopper.RapidView;
import com.chinesedreamer.jira.core.greenhopper.Sprint;
import com.chinesedreamer.jira.core.greenhopper.SprintIssue;
import com.chinesedreamer.jira.core.greenhopper.SprintReport;
import com.chinesedreamer.jira.reader.PropertiesReader;

/**
 * Description: 
 * @author Paris
 * @date Jan 20, 20158:30:54 AM
 * @version beta
 */
@Service
public class JiraReportServiceImpl implements JiraReportService{
	private JiraClient jiraClient;
	private GreenHopperClient gh;
	
	private List<String> keys ;
	private List<String> duplicateKeys ;
	private int taskNum ;
	private int bugNum ;
	private int subTaskNum ;
	private int tecNum ;
	
	private GreenHopperClient getGreenHopperClient() {
		if (null == this.gh) {
			if (null == this.jiraClient) {
				Properties prop = PropertiesReader.loadProperties();
				String url = prop.getProperty("jira.connection.url");
				String username = prop.getProperty("jira.connection.username");
				String password = prop.getProperty("jira.connection.password");
				jiraClient = new JiraClient(url, new BasicCredentials(username, password));
			}
			gh = new GreenHopperClient(jiraClient);
		}
		return gh;
	}
	
	@Override
	public List<RapidView> loadRapidViews() throws JiraException{
		return this.getGreenHopperClient().getRapidViews();
	}
	
	@Override
	public List<Sprint> loadRapidViewSprints(int rapidViewId)
			throws JiraException {
		return this.getGreenHopperClient().getRapidView(rapidViewId).getSprints();
	}

	@Override
	public List<ReportTaskVo> generateDailyReport(int rapidViewId,int sprintId, String templateCode) throws JiraException {
		List<ReportTaskVo> vos = new ArrayList<ReportTaskVo>();
		
		Sprint sprint = null;
		keys = new ArrayList<String>();
		duplicateKeys = new ArrayList<String>();

		RapidView board = this.getGreenHopperClient().getRapidView(rapidViewId);
		for (Sprint s : board.getSprints()) {
			if (s.getId() == sprintId) {
				sprint = s;
				break;
			}
		}
		
		SprintReport sp = board.getSprintReport(sprint);
		//Punted Issues貌似只会获取最大level的issue
//		for (SprintIssue si : sp.getPuntedIssues()) {
//			vos.add(this.generateReportTaskVo(si.getJiraIssue()));
//		}
		List<SprintIssue> allIssues = new ArrayList<SprintIssue>();
		allIssues.addAll(sp.getCompletedIssues());
		allIssues.addAll(sp.getIncompletedIssues());
		for (SprintIssue si : allIssues) {
			if (!keys.contains(si.getKey())) {
				ReportTaskVo vo = this.generateReportTaskVo(si.getJiraIssue());
				if (null != vo) {
					vos.add(vo);
				}
			}
		}
		
		Set<ReportTaskVo> removeVos = new HashSet<ReportTaskVo>();
		for (String key : duplicateKeys) {
			for (ReportTaskVo vo : vos) {
				if (vo.getKey().equals(key)) {
					removeVos.add(vo);
				}
			}
		}
		vos.removeAll(removeVos);
		
		this.calculateParentTask(vos);
		
		this.sortReportVos(vos);
		return vos;
	}
	
	private ReportTaskVo generateReportTaskVo(Issue issue) throws JiraException{
		ReportTaskVo vo = this.generateIssueVo(issue);
		if (null != vo) {
			this.getSubTasks(issue, vo);
			this.getLinkTaskd(issue, vo, false);
		}
		return vo;
	}
	
	private void getSubTasks(Issue issue, ReportTaskVo vo){
		for (Issue subIssue : issue.getSubtasks()) {
			ReportTaskVo subVo = this.generateIssueVo(subIssue);
			if (null != subIssue.getSubtasks() && !subIssue.getSubtasks().isEmpty()) {
				getSubTasks(subIssue, subVo);
			}
			vo.getSubTasks().add(subVo);
		}
	}
	
	private void getLinkTaskd(Issue issue, ReportTaskVo vo, boolean inward) throws JiraException{
		for (IssueLink is : issue.getIssueLinks()) {
			Issue linkIssue = inward ? is.getInwardIssue() : is.getOutwardIssue();
			if (null != linkIssue) {
				String key = linkIssue.getKey();
				linkIssue = this.jiraClient.getIssue(key);
				ReportTaskVo linkVo = this.generateIssueVo(linkIssue);
				getLinkTaskd(linkIssue, linkVo, inward);
				vo.getIncludedTasks().add(linkVo);
			}
		}
	}

	private ReportTaskVo generateIssueVo(Issue issue){
		if (issue.getIssueType().getName().equals("新功能")) {//过滤新功能
			return null;
		}
		
		ReportTaskVo vo = new ReportTaskVo();
		vo.setKey(issue.getKey());
		if (!keys.contains(issue.getKey())) {
			keys.add(issue.getKey());
		}else {
			duplicateKeys.add(issue.getKey());
		}
		
		vo.setSummary(StringUtils.isEmpty(issue.getSummary()) ? "" : issue.getSummary());
		vo.setIssueType( (null == issue.getIssueType() ? "" :  issue.getIssueType().getName()) );
		vo.setStatus( (null == issue.getStatus() ? "" : issue.getStatus().getName()) );
		vo.setAssignee( (null == issue.getAssignee() ? "" : issue.getAssignee().getDisplayName()) );
		vo.setDueDate(issue.getDueDate());
		vo.setResolutionDate(issue.getResolutionDate());
		int timeEstimated = (this.getTimeEstimated(issue));
		vo.setTimeEstimated(timeEstimated);
		vo.setTimeEstimatedStr(this.formatTimeTracking(timeEstimated));
		int timeSpent = (this.getTimeSpent(issue));
		vo.setTimeSpent(timeSpent);
		vo.setTimeSpentStr(this.formatTimeTracking(timeSpent));
		vo.setDeliveryOnTime(this.isOnTime(issue));
		return vo;
	}
	
	private boolean isOnTime(Issue issue){
		return (issue.getDueDate() != null && issue.getResolutionDate() != null && (issue.getResolutionDate().getTime() - issue.getDueDate().getTime() < 3600)) || (issue.getDueDate() == null && issue.getResolutionDate() == null);
	}
	
	private int getTimeSpent(Issue issue){
		return null == issue.getTimeTracking() ? (null == issue
				.getTimeSpent() ? 0 : issue.getTimeSpent()) : issue
				.getTimeTracking().getTimeSpentSeconds();
	}
	
	private int getTimeEstimated(Issue issue){
		return null == issue.getTimeTracking() ? (null == issue
				.getTimeEstimate() ? 0 : issue.getTimeEstimate()) : issue
				.getTimeTracking().getOriginalEstimateSeconds();
	}

	private String formatTimeTracking(int time){
		StringBuffer buffer = new StringBuffer();
		int minutes = time / 60;
		int hours = minutes / 60;
		int days = hours / 24;
		if (days > 0) {
			buffer.append(days + "天");
		}
		if (hours > 0 && hours % 24 != 0) {
			buffer.append( (hours - 24 * days) + "小时");
		}
		if (minutes > 0 && minutes % 60 != 0) {
			buffer.append( (minutes - hours * 60) + "分");
		}
		if(buffer.length() == 0) {
			buffer.append("0");
		}
		return buffer.toString();
	}
	
	private void calculateParentTask(List<ReportTaskVo> vos){
		for (ReportTaskVo vo : vos) {
			int timeEstimated = vo.getTimeEstimated();
			int timeSpent = vo.getTimeSpent();
			int totalSubTimeEstimated = 0;
			int totalSubTimeSpent = 0;
			int totalLinkTimeEstimated = 0;
			int totalLinkTimeSpent = 0;
			Date dueDate = vo.getDueDate();
			Date resolutionDate = vo.getDueDate();
			for (ReportTaskVo subVo : vo.getSubTasks()) {
				totalSubTimeEstimated += subVo.getTimeEstimated();
				totalSubTimeSpent += subVo.getTimeSpent();
				dueDate = (subVo.getDueDate() == null ? dueDate
						: (dueDate == null ? subVo.getDueDate() : dueDate
								.before(subVo.getDueDate()) ? subVo
								.getDueDate() : dueDate));
				resolutionDate = (subVo.getResolutionDate() == null ? resolutionDate
						: (resolutionDate == null ? subVo.getResolutionDate()
								: resolutionDate.before(subVo
										.getResolutionDate()) ? subVo
										.getResolutionDate() : resolutionDate));
			}
			for (ReportTaskVo linkVo : vo.getIncludedTasks()) {
				totalLinkTimeEstimated += linkVo.getTimeEstimated();
				totalLinkTimeSpent += linkVo.getTimeSpent();
				dueDate = (linkVo.getDueDate() == null ? dueDate
						: (dueDate == null ? linkVo.getDueDate() : dueDate
								.before(linkVo.getDueDate()) ? linkVo
								.getDueDate() : dueDate));
				resolutionDate = (linkVo.getResolutionDate() == null ? resolutionDate
						: (resolutionDate == null ? linkVo.getResolutionDate()
								: resolutionDate.before(linkVo
										.getResolutionDate()) ? linkVo
										.getResolutionDate() : resolutionDate));
			}
			if (totalLinkTimeEstimated != 0 || totalSubTimeEstimated != 0) {
				timeEstimated = totalLinkTimeEstimated + totalSubTimeEstimated;
			}
			if (totalSubTimeSpent != 0 || totalLinkTimeSpent != 0) {
				timeSpent = totalSubTimeSpent + totalLinkTimeSpent;
			}

			vo.setTimeEstimated(timeEstimated);
			vo.setTimeEstimatedStr(this.formatTimeTracking(timeEstimated));
			vo.setTimeSpent(timeSpent);
			vo.setTimeSpentStr(this.formatTimeTracking(timeSpent));
			vo.setDueDate(dueDate);
			vo.setResolutionDate(resolutionDate);
		}
	}
	
	private void sortReportVos(List<ReportTaskVo> vos){
		Collections.sort(vos, new ReportTaskVoComparator());
	}

	@Override
	public ReportTaskSummaryVo analyzeSprit(List<ReportTaskVo> reportTaskVos,
			String templateCode) {
		ReportTaskSummaryVo reportTaskSummaryVo = new ReportTaskSummaryVo();
		taskNum = 0;
		bugNum = 0;
		subTaskNum = 0;
		tecNum = 0;
		for (ReportTaskVo vo : reportTaskVos) {
			this.analyzeTask(vo, reportTaskSummaryVo);
			for (ReportTaskVo subVo : vo.getSubTasks()) {
				this.analyzeTask(subVo, reportTaskSummaryVo);
			}
			for (ReportTaskVo includedVo : vo.getIncludedTasks()) {
				this.analyzeTask(includedVo, reportTaskSummaryVo);
			}
		}
		reportTaskSummaryVo.setCompletedRate( 1.00 * reportTaskSummaryVo.getCompletedTasks() / reportTaskSummaryVo.getTotalTasks());
		reportTaskSummaryVo.setWorkingRate( 1.00 * reportTaskSummaryVo.getWorkingMinutes() / reportTaskSummaryVo.getTotalMinutes() );
		reportTaskSummaryVo.setWorkingMinutesStr( this.formatTimeTracking(reportTaskSummaryVo.getWorkingMinutes()) );
		reportTaskSummaryVo.setTotalMinutesStr( this.formatTimeTracking(reportTaskSummaryVo.getTotalMinutes()) );
		reportTaskSummaryVo.setOnTimeRate( 1.00 * reportTaskSummaryVo.getOnTime() / reportTaskSummaryVo.getTotalTasks() );
		reportTaskSummaryVo.setBugRate( 1.00 * bugNum / (taskNum + subTaskNum + tecNum) );
		Map<String, Integer> constitution = new HashMap<String, Integer>();
		constitution.put("task", taskNum);
		constitution.put("bug", bugNum);
		constitution.put("subTask", subTaskNum);
		constitution.put("tec", tecNum);
		reportTaskSummaryVo.setConstitution(constitution);
		return reportTaskSummaryVo;
	}
	
	private void analyzeTask(ReportTaskVo vo,ReportTaskSummaryVo reportTaskSummaryVo){
		reportTaskSummaryVo.addTotalTasks();
		if (vo.getStatus().equals("已关闭")) {
			reportTaskSummaryVo.addCompletedTasks();
		}
		if (vo.isDeliveryOnTime()) {
			reportTaskSummaryVo.addOnTime();
		}
		reportTaskSummaryVo.addWorkingMinutes(vo.getTimeSpent());
		reportTaskSummaryVo.addTotalMinutes(vo.getTimeEstimated());
		if (vo.getIssueType().equals("任务")) {
			taskNum += 1;
		}else if (vo.getIssueType().equals("子任务")) {
			subTaskNum += 1;
		}else if (vo.getIssueType().equals("Technical task")) {
			tecNum += 1;
		}else if (vo.getIssueType().equals("Bug")) {
			bugNum += 1;
		}
	}

	@Override
	public List<ReportTaskAssigneeVo> generateAssigneeReport(int rapidViewId,
			int sprintId, String templateCode) throws JiraException {
		List<ReportTaskAssigneeVo> vos = new ArrayList<ReportTaskAssigneeVo>();
		
		Sprint sprint = null;
		RapidView board = this.getGreenHopperClient().getRapidView(rapidViewId);
		for (Sprint s : board.getSprints()) {
			if (s.getId() == sprintId) {
				sprint = s;
				break;
			}
		}
		SprintReport sp = board.getSprintReport(sprint);
		List<SprintIssue> allIssues = new ArrayList<SprintIssue>();
		allIssues.addAll(sp.getCompletedIssues());
		allIssues.addAll(sp.getIncompletedIssues());
		
		List<String> keys = new ArrayList<String>();
		for (SprintIssue si : allIssues) {
			Issue issue = si.getJiraIssue();
			if (null != issue.getAssignee() && !issue.getIssueType().getName().equals("Story") && !issue.getIssueType().getName().equals("新功能") ) {
				ReportTaskAssigneeVo vo = this.generateIssueAssigneeVo(si.getJiraIssue());
				if (null != vo) {
					if (!keys.contains(vo.getUsername())) {
						keys.add(vo.getUsername());
						vos.add(vo);
					}else {
						int index = keys.indexOf(vo.getUsername());
						vos.get(index).merge(vo);
					}
				}
			}
		}
		for (ReportTaskAssigneeVo vo : vos) {
			int tasks = vo.getTotal() - vo.getBugs();
			vo.setBugRate( (0 == tasks) ? 0.00 : 1.00 * vo.getBugs() / tasks );
			vo.setOnTimeRate( 1.00 * vo.getOnTime() / vo.getTotal() );
			vo.setTimeRate( (0 == vo.getTotalEstimated()) ? 0 : 1.00 * vo.getTotalTimeSpent() / vo.getTotalEstimated() );
			vo.setTotalTimeSpentStr( this.formatTimeTracking(vo.getTotalTimeSpent()) );
			vo.setTotalEstimatedStr( this.formatTimeTracking(vo.getTotalEstimated()) );
		}
		return vos;
	}
	
	private ReportTaskAssigneeVo generateIssueAssigneeVo(Issue issue){
		ReportTaskAssigneeVo vo = new ReportTaskAssigneeVo();
		vo.setTotal(1);
		vo.setUsername(issue.getAssignee().getDisplayName());
		if (issue.getIssueType().getName().equals("Bug")) {
			vo.setBugs(1);
		}
		if (this.isOnTime(issue)) {
			vo.setOnTime(1);
		}
		vo.setTotalTimeSpent(this.getTimeSpent(issue));
		vo.setTotalEstimated(this.getTimeEstimated(issue));
		return vo;
	}
}
