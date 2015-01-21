package com.chinesedreamer.jira.biz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.biz.vo.ReportTaskVo;
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

		RapidView board = this.getGreenHopperClient().getRapidView(rapidViewId);
		for (Sprint s : board.getSprints()) {
			if (s.getId() == sprintId) {
				sprint = s;
				break;
			}
		}
		
		SprintReport sp = board.getSprintReport(sprint);
		for (SprintIssue si : sp.getPuntedIssues()) {
			Issue issue = si.getJiraIssue();

			ReportTaskVo vo = new ReportTaskVo();
			vo.setKey(issue.getKey());
			vo.setSummary(issue.getSummary());
			vo.setIssueType(issue.getIssueType().getName());
			vo.setStatus(issue.getStatus().getName());
			vo.setAssignee(issue.getAssignee().getDisplayName());
			vo.setDueDate(issue.getDueDate());
			this.getSubTasks(issue, vo);
			this.getLinkTaskd(issue, vo, false);
			//TODO not included in sotry/tasks
			vos.add(vo);
		}
		
		return vos;
	}
	
	private void getSubTasks(Issue issue, ReportTaskVo vo){
		for (Issue subIssue : issue.getSubtasks()) {
			ReportTaskVo subVo = new ReportTaskVo();
			subVo.setKey(subIssue.getKey());
			subVo.setSummary(subIssue.getSummary());
			subVo.setIssueType( (null == subIssue.getIssueType() ? "" : subIssue.getIssueType().getName()) );
			subVo.setStatus(subIssue.getStatus().getName());
			subVo.setAssignee( (null == subIssue.getAssignee() ? "" : subIssue.getAssignee().getDisplayName()) );
			subVo.setDueDate(subIssue.getDueDate());
			if (null != subIssue.getSubtasks() && !subIssue.getSubtasks().isEmpty()) {
				getSubTasks(subIssue, subVo);
			}
			vo.getSubTasks().add(subVo);
		}
	}
	
	private void getLinkTaskd(Issue issue, ReportTaskVo vo, boolean inward){
		for (IssueLink is : issue.getIssueLinks()) {
			Issue linkIssue = inward ? is.getInwardIssue() : is.getOutwardIssue();
			ReportTaskVo linkVo = new ReportTaskVo();
			if (null != linkIssue) {
				linkVo.setKey(linkIssue.getKey());
				linkVo.setSummary(linkIssue.getSummary());
				linkVo.setIssueType( (null == linkIssue.getIssueType() ? "" : linkIssue.getIssueType().getName()) );
				linkVo.setStatus(linkIssue.getStatus().getName());
				linkVo.setAssignee( (null == linkIssue.getAssignee() ? "" : linkIssue.getAssignee().getDisplayName()));
				linkVo.setDueDate(linkIssue.getDueDate());
				getLinkTaskd(linkIssue, linkVo, inward);
				vo.getIncludedTasks().add(linkVo);
			}
		}
	}

	

	
}
