package com.chinesedreamer.jira.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.biz.core.issue.logic.JiraIssueLogic;
import com.chinesedreamer.jira.biz.core.issue.logic.JiraIssueVersionLogic;
import com.chinesedreamer.jira.biz.core.issue.model.JiraIssue;
import com.chinesedreamer.jira.biz.core.issue.model.JiraIssueVersion;
import com.chinesedreamer.jira.biz.core.project.logic.JiraProjectLogic;
import com.chinesedreamer.jira.biz.core.project.model.JiraProject;
import com.chinesedreamer.jira.biz.core.status.logic.JiraStatusLogic;
import com.chinesedreamer.jira.biz.core.status.model.JiraStatus;
import com.chinesedreamer.jira.biz.core.user.logic.JiraUserLogic;
import com.chinesedreamer.jira.biz.core.user.model.JiraUser;
import com.chinesedreamer.jira.biz.core.version.logic.JiraVersionLogic;
import com.chinesedreamer.jira.biz.core.version.model.JiraVersion;
import com.chinesedreamer.jira.biz.service.JiraReportService;
import com.chinesedreamer.jira.biz.sysconfig.constant.SysConfigConstant;
import com.chinesedreamer.jira.biz.sysconfig.logic.SysConfigLogic;
import com.chinesedreamer.jira.biz.sysconfig.model.SysConfig;
import com.chinesedreamer.jira.biz.utils.DateUtil;
import com.chinesedreamer.jira.biz.vo.JiraIssuePorjectReportComparator;
import com.chinesedreamer.jira.biz.vo.JiraIssueVo;
import com.chinesedreamer.jira.biz.vo.ProjectReportVo;
import com.chinesedreamer.jira.biz.vo.TimeScopeReportVo;
import com.chinesedreamer.jira.biz.vo.UserReportVo;

/**
 * Description: 
 * @author Paris
 * @date May 6, 201511:22:40 AM
 * @version beta
 */
@Service
public class JiraReportServiceImpl implements JiraReportService{
	@Resource
	private JiraIssueVersionLogic jiraIssueVersionLogic;
	@Resource
	private JiraProjectLogic jiraProjectLogic;
	@Resource
	private JiraVersionLogic jiraVersionLogic;
	@Resource
	private SysConfigLogic sysConfigLogic;
	@Resource
	private JiraIssueLogic jiraIssueLogic;
	@Resource
	private JiraUserLogic jiraUserLogic;
	@Resource
	private JiraStatusLogic jiraStatusLogic;

	@Override
	public ProjectReportVo generateProjectReport(String projectJiraId,
			String versionJiraId) {
		ProjectReportVo vo = new ProjectReportVo();
		//1. 获取项目信息
		JiraProject jiraProject = this.jiraProjectLogic.findByJiraId(projectJiraId);
		vo.setProject(jiraProject.getName());
		//2. 获取版本信息
		JiraVersion jiraVersion = this.jiraVersionLogic.findByJiraId(versionJiraId);
		vo.setVersion(jiraVersion.getName());
		if (null != jiraVersion.getReleaseDate()) {
			vo.setReleaseDate(jiraVersion.getReleaseDate());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			vo.setReleaseDateStr(format.format(jiraVersion.getReleaseDate()));
		}
		//3. 获取版本内issue
		List<JiraIssueVersion> jiraIssueVersions = this.jiraIssueVersionLogic.findByProjectJiraIdAndVersionJiraId(projectJiraId, versionJiraId);
		List<JiraIssueVo> uncompletedIssues = new ArrayList<JiraIssueVo>();
		List<JiraIssueVo> completedIssues = new ArrayList<JiraIssueVo>();
		List<JiraIssueVo> bugs = new ArrayList<JiraIssueVo>();
		//3.1 获取配置
		Set<String> taskConfig = this.getConfig(SysConfigConstant.RPT_ISSUE_TYPE_TASK_LEVEL);
		Set<String> closeConfig = this.getConfig(SysConfigConstant.RPT_ISSUE_STATUS_CLOSED_LEVEL);
		Set<String> bugConfig = this.getConfig(SysConfigConstant.RPT_ISSUE_TYPE_BUG_LEVEL);
		for (JiraIssueVersion jiraIssueVersion : jiraIssueVersions) {
			JiraIssue jiraIssue = this.jiraIssueLogic.findByJiraId(jiraIssueVersion.getIssueJiraId());
			if (StringUtils.isNotEmpty(jiraIssue.getIssueType())) {
				if (taskConfig.contains(jiraIssue.getIssueType())) {
					if (StringUtils.isNotEmpty(jiraIssue.getStatus())) {
						if (closeConfig.contains(jiraIssue.getStatus())) {
							completedIssues.add(this.convert2Vo(jiraIssue));
						}else {
							uncompletedIssues.add(this.convert2Vo(jiraIssue));
						}
					}
				}else if (bugConfig.contains(jiraIssue.getIssueType())) {
					bugs.add(this.convert2Vo(jiraIssue));
				}
			}
		}
		//3.2 排序
		JiraIssuePorjectReportComparator comparator = new JiraIssuePorjectReportComparator();
		Collections.sort(uncompletedIssues, comparator);
		Collections.sort(completedIssues, comparator);
		Collections.sort(bugs, comparator);
		vo.setUncompletedIssues(uncompletedIssues);
		vo.setCompletedIssues(completedIssues);
		vo.setBugs(bugs);
		//4. 统计
		vo.setTotal(uncompletedIssues.size() + completedIssues.size());
		vo.setCompletedNum(completedIssues.size());
		vo.setBugNum(bugs.size());
		if (uncompletedIssues.size() + completedIssues.size() == 0) {
			vo.setCompletionRate(0.00f);
		}else {
			vo.setCompletionRate(1.00f * completedIssues.size() / (uncompletedIssues.size() + completedIssues.size()));
		}
		vo.setCompletionRateStr(vo.getCompletionRate() * 100 + "%");
		return vo;
	}

	private Set<String> getConfig(String property) {
		SysConfig config = this.sysConfigLogic.findByProperty(property);
		String[] configIds = config.getPropertyValue().split(",");
		Set<String> configs = new HashSet<String>();
		for (String id : configIds) {
			configs.add(id);
		}
		return configs;
	}
	
	private JiraIssueVo convert2Vo(JiraIssue jiraIssue) {
		JiraIssueVo vo = new JiraIssueVo();
		vo.setKey(jiraIssue.getKey());
		vo.setSummary(jiraIssue.getSummary());
		if (StringUtils.isNotEmpty(jiraIssue.getAssigne())) {
			JiraUser jiraUser = this.jiraUserLogic.findByUsername(jiraIssue.getAssigne());
			if (null == jiraUser) {
				vo.setAssignee("未知用户");
			}else {
				vo.setAssignee(jiraUser.getDisplayName());
			}
		}else {
			vo.setAssignee("未分配");
		}
		if (null != jiraIssue.getDueDate()) {
			vo.setDueDate(jiraIssue.getDueDate());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			vo.setDueDateStr(format.format(jiraIssue.getDueDate()));
		}
		if (StringUtils.isNotEmpty(jiraIssue.getStatus())) {
			JiraStatus jiraStatus = this.jiraStatusLogic.findByJiraId(jiraIssue.getStatus());
			if (null == jiraStatus) {
				vo.setStatus("未知状态");
			}else {
				vo.setStatus(jiraStatus.getName());
			}
		}else {
			vo.setStatus("无");
		}
		if (null != jiraIssue.getTimeSpent()) {
			vo.setTimeSpent(DateUtil.formatTimeTracking(jiraIssue.getTimeSpent()));
		}
		if (null != jiraIssue.getTimeEstemate()) {
			vo.setTimeEstimated(DateUtil.formatTimeTracking(jiraIssue.getTimeEstemate()));
		}
		vo.setProject(this.jiraProjectLogic.findByJiraId(jiraIssue.getProject()).getName());
		return vo;
	}

	@Override
	public TimeScopeReportVo generateTimeScopeReport(Date start, Date end) {
		//1. 找到相关的版本
		SysConfig fluctuation =  this.sysConfigLogic.findByProperty(SysConfigConstant.RPT_TIME_SCOP_FLUCTUATION);
		int inteval = Integer.parseInt(fluctuation.getPropertyValue());
		List<JiraVersion> versions = this.jiraVersionLogic.findByReleaseDateBetween(
				DateUtil.calculateDate(start, -1 * inteval),
				DateUtil.calculateDate(end, 3 * inteval));
		//2. 找到相关的项目-版本
		List<ProjectReportVo> projects =  new ArrayList<ProjectReportVo>();
		for (JiraVersion version : versions) {
			JiraProject project = this.jiraProjectLogic.findByJiraId(version.getProjectJiraId());
			ProjectReportVo vo = new ProjectReportVo();
			vo.setProject(project.getName());
			vo.setProjectJiraId(project.getJiraId());
			vo.setVersion(version.getName());
			vo.setVersionJiraId(version.getJiraId());
			projects.add(vo);
		}
		//3. 统计issue
		Map<String, List<JiraIssue>> userIssues = new HashMap<String, List<JiraIssue>>();
		for (ProjectReportVo vo : projects) {
			this.calculateIssues(vo, userIssues);
		}
		List<UserReportVo> users = this.generateUserReport(userIssues);
		
		TimeScopeReportVo timeScopReport = new TimeScopeReportVo();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		timeScopReport.setStartDate(format.format(start));
		timeScopReport.setEndDate(format.format(end));
		timeScopReport.setProjects(projects);
		timeScopReport.setUsers(users);
		return timeScopReport;
	}
	
	private List<UserReportVo> generateUserReport(Map<String, List<JiraIssue>> userIssues) {
		List<UserReportVo> users = new ArrayList<UserReportVo>();
		for (String username : userIssues.keySet()) {
			users.add(this.generateUserReportVo(username, userIssues.get(username)));
		}
		return users;
	}
	
	private UserReportVo generateUserReportVo(String username, List<JiraIssue> issues){
		UserReportVo vo = new UserReportVo();
		vo.setUsername(this.jiraUserLogic.findByUsername(username).getDisplayName());
		Integer totalNum = 0;
		Integer completedNum = 0;
		Integer bugNum = 0;
		Integer totalTimeEstimated = 0;
		Integer totalTimeSpent = 0;
		List<JiraIssueVo> jiraIssueVos = new ArrayList<JiraIssueVo>();
		Set<String> taskConfig = this.getConfig(SysConfigConstant.RPT_ISSUE_TYPE_TASK_LEVEL);
		Set<String> closeConfig = this.getConfig(SysConfigConstant.RPT_ISSUE_STATUS_CLOSED_LEVEL);
		Set<String> bugConfig = this.getConfig(SysConfigConstant.RPT_ISSUE_TYPE_BUG_LEVEL);
		for (JiraIssue jiraIssue : issues) {
			if (StringUtils.isNotEmpty(jiraIssue.getIssueType())) {
				if (taskConfig.contains(jiraIssue.getIssueType())) {
					totalNum += 1;
					if (closeConfig.contains(jiraIssue.getStatus())) {
						completedNum += 1;
					}
				}else if (bugConfig.contains(jiraIssue.getIssueType())) {
					bugNum += 1;
				}
				if (null != jiraIssue.getTimeEstemate()) {
					totalTimeEstimated += jiraIssue.getTimeEstemate();
				}
				if (null != jiraIssue.getTimeSpent()) {
					totalTimeSpent += jiraIssue.getTimeSpent();
				}
				jiraIssueVos.add(this.convert2Vo(jiraIssue));
			}
		}
		vo.setTotalNum(totalNum);
		vo.setCompletedNum(completedNum);
		vo.setBugNum(bugNum);
		vo.setTotalTimeEstimated(DateUtil.formatTimeTracking(totalTimeEstimated));
		vo.setTotalTimeSpent(DateUtil.formatTimeTracking(totalTimeSpent));
		if (totalNum != 0) {
			float rate = completedNum / totalNum * 1.0000f;
			vo.setCompleteRate(rate * 100 + "%");
		}
		if (totalTimeEstimated != 0) {
			float rate = (totalTimeSpent - totalTimeEstimated) / totalTimeEstimated * 1.0000f;
			vo.setTotalTimeout(rate > 0 ? "超时" + rate * 1000 + "%" : "提前" + rate * 1000 + "%");
		}
		vo.setIssueVos(jiraIssueVos);
		return vo;
	}
	
	private void calculateIssues(ProjectReportVo vo, Map<String, List<JiraIssue>> userIssues) {
		List<JiraIssueVersion> issueVersions = this.jiraIssueVersionLogic.findByProjectJiraIdAndVersionJiraId(vo.getProjectJiraId(), vo.getVersionJiraId());
		Integer uncompletedIssues = 0;
		Integer completedIssues = 0;
		Integer bugs = 0;
		Set<String> taskConfig = this.getConfig(SysConfigConstant.RPT_ISSUE_TYPE_TASK_LEVEL);
		Set<String> closeConfig = this.getConfig(SysConfigConstant.RPT_ISSUE_STATUS_CLOSED_LEVEL);
		Set<String> bugConfig = this.getConfig(SysConfigConstant.RPT_ISSUE_TYPE_BUG_LEVEL);
		for (JiraIssueVersion jiraIssueVersion : issueVersions) {
			JiraIssue jiraIssue = this.jiraIssueLogic.findByJiraId(jiraIssueVersion.getIssueJiraId());
			//project 统计
			if (StringUtils.isNotEmpty(jiraIssue.getIssueType())) {
				if (taskConfig.contains(jiraIssue.getIssueType())) {
					if (StringUtils.isNotEmpty(jiraIssue.getStatus())) {
						if (closeConfig.contains(jiraIssue.getStatus())) {
							completedIssues += 1;
						}else {
							uncompletedIssues += 1;
						}
					}
				}else if (bugConfig.contains(jiraIssue.getIssueType())) {
					bugs += 1;
				}
			}
			vo.setTotal(uncompletedIssues + completedIssues + bugs);
			vo.setCompletedNum(completedIssues);
			vo.setBugNum(bugs);
			if (uncompletedIssues + completedIssues + bugs != 0) {
				float rate = completedIssues / (uncompletedIssues + completedIssues + bugs) * 1.0000f;
				vo.setCompletionRateStr(rate * 100 + "%");
			}
			
			//用户统计
			if (null != jiraIssue.getAssigne()) {
				String key = jiraIssue.getAssigne();
				if (null == userIssues.get(key)) {
					userIssues.put(key, new ArrayList<JiraIssue>());
				}
				List<JiraIssue> issues = userIssues.get(key);
				issues.add(jiraIssue);
				userIssues.put(key, issues);
			}
		}
	}
}
