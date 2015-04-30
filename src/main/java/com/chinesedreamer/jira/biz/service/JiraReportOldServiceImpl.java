package com.chinesedreamer.jira.biz.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chinesedreamer.jira.biz.vo.ReportTaskAssigneeOldVo;
import com.chinesedreamer.jira.biz.vo.ReportTaskSummaryOldVo;
import com.chinesedreamer.jira.biz.vo.ReportTaskOldVo;
import com.chinesedreamer.jira.biz.vo.ReportTaskVoOldComparator;
import com.chinesedreamer.jira.biz.vo.ReportOldVo;
import com.chinesedreamer.jira.core.BasicCredentials;
import com.chinesedreamer.jira.core.Filter;
import com.chinesedreamer.jira.core.FilterIssue;
import com.chinesedreamer.jira.core.Issue;
import com.chinesedreamer.jira.core.IssueLink;
import com.chinesedreamer.jira.core.JiraClient;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.RestClient;
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
public class JiraReportOldServiceImpl implements JiraReportOldService{
	private JiraClient jiraClient;
	private GreenHopperClient gh;

	private int taskNum ;
	private int bugNum ;
	private int subTaskNum ;
	private int tecNum ;
	
	private List<String> duplicateKeys;
	
	@Autowired
	private HttpSession session;
	
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
	
	private JiraClient initJiraClient(){
		if (null == this.jiraClient) {
			Properties prop = PropertiesReader.loadProperties();
			String url = prop.getProperty("jira.connection.url");
			String username = prop.getProperty("jira.connection.username");
			String password = prop.getProperty("jira.connection.password");
			jiraClient = new JiraClient(url, new BasicCredentials(username, password));
		}
		return jiraClient;
	}
	
	@Override
	public List<RapidView> loadRapidViews() throws JiraException{
		System.out.println("session:" + session.getId());
		return this.getGreenHopperClient().getRapidViews();
	}
	
	@Override
	public List<Sprint> loadRapidViewSprints(int rapidViewId)
			throws JiraException {
		return this.getGreenHopperClient().getRapidView(rapidViewId).getSprints();
	}

	@Override
	public List<ReportTaskOldVo> generateDailyReport(int rapidViewId,int sprintId, String templateCode) throws JiraException {
		
		
		Sprint sprint = null;
		//获取sprint
		RapidView board = this.getGreenHopperClient().getRapidView(rapidViewId);
		for (Sprint s : board.getSprints()) {
			if (s.getId() == sprintId) {
				sprint = s;
				break;
			}
		}
		SprintReport sp = board.getSprintReport(sprint);
		List<ReportTaskOldVo> vos = this.getSprintVos(sp);
//		//this.removeDuplicateVos(vos);
		this.calculateParentTask(vos);
		this.sortReportVos(vos);
		return vos;
		
		//1. 获取所有issue
	}
	
	private List<ReportTaskOldVo> getSprintVos(SprintReport sp) throws JiraException{
		List<ReportTaskOldVo> vos = new ArrayList<ReportTaskOldVo>();
		List<SprintIssue> allIssues = new ArrayList<SprintIssue>();
		allIssues.addAll(sp.getCompletedIssues());
		allIssues.addAll(sp.getIncompletedIssues());
		duplicateKeys = new ArrayList<String>();
		//获取所有vo
		for (SprintIssue si : allIssues) {
			Issue issue = si.getJiraIssue();
			ReportTaskOldVo vo = this.generateReportTaskVo(issue);
			vos.add(vo);
		}
		return vos;
	}
	
	private ReportTaskOldVo generateReportTaskVo(Issue issue) throws JiraException{
		ReportTaskOldVo vo = this.generateIssueVo(issue.getKey());
		this.getSubTasks(issue, vo);
		this.getLinkTaskd(issue, vo, false);
		return vo;
	}
	
	private void getSubTasks(Issue issue, ReportTaskOldVo vo) throws JiraException{
		for (Issue subIssue : issue.getSubtasks()) {
			duplicateKeys.add(subIssue.getKey());
			ReportTaskOldVo subVo = this.generateIssueVo(subIssue.getKey());
			if (null != subVo.getSubTasks() && !subVo.getSubTasks().isEmpty()) {
				getSubTasks(subIssue, subVo);
			}
			if (!subTaskExist(vo.getSubTasks(), subVo)) {
				vo.getSubTasks().add(subVo);
			}
		}
	}
	
	private boolean subTaskExist(Collection<ReportTaskOldVo> vos, ReportTaskOldVo vo){
		for (ReportTaskOldVo reportTaskVo : vos) {
			if (vo.getKey().equals(reportTaskVo.getKey())) {
				return true;
			}
		}
		return false;
	}
	
	private void getLinkTaskd(Issue issue, ReportTaskOldVo vo, boolean inward) throws JiraException{
		for (IssueLink is : issue.getIssueLinks()) {
			Issue linkIssue = inward ? is.getInwardIssue() : is.getOutwardIssue();
			if (null != linkIssue) {
				String key = linkIssue.getKey();
				duplicateKeys.add(key);
				linkIssue = this.jiraClient.getIssue(key);
				ReportTaskOldVo linkVo = this.generateIssueVo(linkIssue.getKey());
				if (null != linkIssue.getIssueLinks() && !linkIssue.getIssueLinks().isEmpty()) {
					getLinkTaskd(linkIssue, linkVo, inward);
				}
				if (!linkTaskExist(vo.getIncludedTasks(), linkVo)) {
					vo.getIncludedTasks().add(linkVo);
				}
			}
		}
	}
	
	private boolean linkTaskExist(Collection<ReportTaskOldVo> vos, ReportTaskOldVo vo){
		for (ReportTaskOldVo reportTaskVo : vos) {
			if (vo.getKey().equals(reportTaskVo.getKey())) {
				return true;
			}
		}
		return false;
	}

	private ReportTaskOldVo generateIssueVo(String issueKey) throws JiraException{
		Issue issue = this.jiraClient.getIssue(issueKey);		
		ReportTaskOldVo vo = new ReportTaskOldVo();
		vo.setKey(issue.getKey());
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
	
	private void calculateParentTask(List<ReportTaskOldVo> vos){
		for (ReportTaskOldVo vo : vos) {
			int timeEstimated = vo.getTimeEstimated();
			int timeSpent = vo.getTimeSpent();
			int totalSubTimeEstimated = 0;
			int totalSubTimeSpent = 0;
			int totalLinkTimeEstimated = 0;
			int totalLinkTimeSpent = 0;
			Date dueDate = vo.getDueDate();
			Date resolutionDate = vo.getDueDate();
			for (ReportTaskOldVo subVo : vo.getSubTasks()) {
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
			for (ReportTaskOldVo linkVo : vo.getIncludedTasks()) {
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
			if (null != vo.getSubTasks() && !vo.getSubTasks().isEmpty()) {
				calculateParentTask(vo.getSubTasks());
			}
			if (null != vo.getIncludedTasks() && !vo.getIncludedTasks().isEmpty()) {
				calculateParentTask(vo.getIncludedTasks());
			}
		}
	}
	
	private void sortReportVos(List<ReportTaskOldVo> vos){
		Collections.sort(vos, new ReportTaskVoOldComparator());
	}

	@Override
	public ReportTaskSummaryOldVo analyzeSprit(List<ReportTaskOldVo> reportTaskVos,
			String templateCode) {
		ReportTaskSummaryOldVo reportTaskSummaryVo = new ReportTaskSummaryOldVo();
		taskNum = 0;
		bugNum = 0;
		subTaskNum = 0;
		tecNum = 0;
		for (ReportTaskOldVo vo : reportTaskVos) {
			this.analyzeTask(vo, reportTaskSummaryVo);
			for (ReportTaskOldVo subVo : vo.getSubTasks()) {
				this.analyzeTask(subVo, reportTaskSummaryVo);
			}
			for (ReportTaskOldVo includedVo : vo.getIncludedTasks()) {
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
	
	private void analyzeTask(ReportTaskOldVo vo,ReportTaskSummaryOldVo reportTaskSummaryVo){
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
	public List<ReportTaskAssigneeOldVo> generateAssigneeReport(int rapidViewId,
			int sprintId, String templateCode) throws JiraException {
		List<ReportTaskAssigneeOldVo> vos = new ArrayList<ReportTaskAssigneeOldVo>();
		
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
				ReportTaskAssigneeOldVo vo = this.generateIssueAssigneeVo(si.getJiraIssue());
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
		for (ReportTaskAssigneeOldVo vo : vos) {
			int tasks = vo.getTotal() - vo.getBugs();
			vo.setBugRate( (0 == tasks) ? 0.00 : 1.00 * vo.getBugs() / tasks );
			vo.setOnTimeRate( 1.00 * vo.getOnTime() / vo.getTotal() );
			vo.setTimeRate( (0 == vo.getTotalEstimated()) ? 0 : 1.00 * vo.getTotalTimeSpent() / vo.getTotalEstimated() );
			vo.setTotalTimeSpentStr( this.formatTimeTracking(vo.getTotalTimeSpent()) );
			vo.setTotalEstimatedStr( this.formatTimeTracking(vo.getTotalEstimated()) );
		}
		return vos;
	}
	
	private ReportTaskAssigneeOldVo generateIssueAssigneeVo(Issue issue){
		ReportTaskAssigneeOldVo vo = new ReportTaskAssigneeOldVo();
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
	
	
	
	/**************************************
	 * 
	 */
	private Set<String> loadedIssues; //已经加载的task
	private Set<String> pendingLoadIssues; //等待加载的task
	
	@Override
	public List<ReportTaskOldVo> generateSprintReport(int rapidViewId,int sprintId, String templateCode) throws JiraException {
		this.initSet();
		
		Sprint sprint = null;
		RapidView board = this.getGreenHopperClient().getRapidView(rapidViewId);
		for (Sprint s : board.getSprints()) {
			if (s.getId() == sprintId) {
				sprint = s;
				break;
			}
		}
		SprintReport sp = board.getSprintReport(sprint);
		List<ReportTaskOldVo> vos = new ArrayList<ReportTaskOldVo>();
		Set<SprintIssue> allSprintIssues = new HashSet<SprintIssue>();
		allSprintIssues.addAll(sp.getCompletedIssues());
		allSprintIssues.addAll(sp.getIncompletedIssues());
		//1. 遍历获取所有相关的task
		for (SprintIssue sprintIssue : allSprintIssues) {
			Issue issue = sprintIssue.getJiraIssue();
			System.out.println(issue);
		}
		//2. 整理task tree
		//3. 计算
		//4. 返回结果
		return vos;
		
	}
	
	/**
	 * 初始化set数组
	 */
	private void initSet(){
		if (null == loadedIssues) {
			loadedIssues = new HashSet<String>();
		}
		if (null == pendingLoadIssues) {
			pendingLoadIssues = new HashSet<String>();
		}
	}

	/*********************************** 20150408 迭代每日report提醒 **********************************/
	@Override
	public List<ReportOldVo> generateDailyReport() throws JiraException{
		//1. 获取filter
		Properties prop = PropertiesReader.loadProperties();
		String url = prop.getProperty("jira.connection.url");
		String username = prop.getProperty("jira.connection.username");
		String password = prop.getProperty("jira.connection.password");
		BasicCredentials creds = new BasicCredentials(username, password);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		RestClient restClient = new RestClient(httpclient, creds, URI.create(url));
		
		List<ReportOldVo> reports = new ArrayList<ReportOldVo>();
		
		//2. 获取filter筛选出来的数据集合
		List<Filter> favourites = Filter.getFavourite(restClient);
		for (Filter filter : favourites) {
			List<FilterIssue> filterIssues = FilterIssue.get(restClient, filter.getSearchUrl());
			//3. 生成vo 列表
			List<ReportTaskOldVo> vos = new ArrayList<ReportTaskOldVo>();
			for (FilterIssue filterIssue : filterIssues) {
				vos.add(this.generateVo(filterIssue));
			}
			//4. parent，sub统计
			this.subTask(vos);
			reports.add(new ReportOldVo(filter.getName(), vos, this.analyzeSprit(vos, "")));
		}

		return reports;
	}
	
	private List<ReportTaskOldVo> subTask(List<ReportTaskOldVo> vos) {
		Map<String, ReportTaskOldVo> map = new HashMap<String, ReportTaskOldVo>();
		for (ReportTaskOldVo vo : vos) {
			map.put(vo.getKey(), vo);
		}
		for (ReportTaskOldVo vo : vos) {
			if ( !StringUtils.isEmpty(vo.getParentKey()) && map.keySet().contains(vo.getParentKey())) {
				ReportTaskOldVo parentVo = map.get(vo.getParentKey());
				parentVo.getSubTasks().add(vo);
				map.put(vo.getParentKey(), parentVo);
				map.remove(vo.getKey());
			}
		}
		List<ReportTaskOldVo> results = new ArrayList<ReportTaskOldVo>();
		for (String key : map.keySet()) {
			results.add(map.get(key));
		}
		return results;
	}

	/**
	 * FilterIssue 生成 ReportTaskVo
	 * @param issue
	 * @return
	 * @throws JiraException
	 */
	private ReportTaskOldVo generateVo(FilterIssue issue) throws JiraException {
		ReportTaskOldVo vo = new ReportTaskOldVo();
		vo.setKey(issue.getKey());
		if (null != issue.getParent()) {
			vo.setParentKey(issue.getParent().getKey());
		}
		vo.setSummary(vo.getSummary());
		
		Issue is = this.initJiraClient().getIssue(issue.getKey());
		if (null != is.getDueDate()) {
			vo.setDueDate(is.getDueDate());
		}
		vo.setStatus(issue.getStatus().getName());
		if (null != issue.getAssignee()) {
			vo.setAssignee(issue.getAssignee().getDisplayName());
		}
		vo.setIssueType(issue.getIssueType().getName());
		if (null != issue.getTimeEstimate()) {
			vo.setTimeEstimated(issue.getTimeEstimate());
			vo.setTimeEstimatedStr(this.formatTimeTracking(issue.getTimeEstimate()));
		}
		if (null != issue.getTimeSpent()) {
			vo.setTimeSpent(issue.getTimeSpent());
			vo.setTimeSpentStr(this.formatTimeTracking(issue.getTimeSpent()));
		}
		if (null != issue.getResolutionDate()) {
			vo.setResolutionDate(issue.getResolutionDate());
		}
		vo.setDeliveryOnTime(this.isOnTime(is));
		return vo;
	}
	
	
}
