package com.chinesedreamer.jira.biz.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
import com.chinesedreamer.jira.biz.vo.JiraIssuePorjectReportComparator;
import com.chinesedreamer.jira.biz.vo.JiraIssueVo;
import com.chinesedreamer.jira.biz.vo.ProjectReportVo;

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
		if (uncompletedIssues.size() + completedIssues.size() == 0) {
			vo.setCompletionRate(0.00f);
		}else {
			vo.setCompletionRate(1.00f * completedIssues.size() / (uncompletedIssues.size() + completedIssues.size()));
		}
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
		return vo;
	}
}
