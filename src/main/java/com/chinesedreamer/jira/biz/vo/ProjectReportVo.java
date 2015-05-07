package com.chinesedreamer.jira.biz.vo;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Description: 总体进度报告
 * @author Paris
 * @date May 6, 201511:32:43 AM
 * @version beta
 */
public @Getter @Setter class ProjectReportVo {
	//版本信息
	private String projectJiraId;
	private String project;//项目
	private String versionJiraId;
	private String version;//版本
	private Date releaseDate;//版本截止日期
	private String releaseDateStr = "";
	//总体信息
	private Integer total;//任务总数
	private Integer completedNum;//完成数
	private Integer bugNum;//bug总数
	private Float completionRate;//完成率
	private String completionRateStr;//完成率
	
	//issue列表
	private List<JiraIssueVo> uncompletedIssues;//未完成列表
	private List<JiraIssueVo> completedIssues;//已完成列表
	private List<JiraIssueVo> bugs;//bug列表
}
