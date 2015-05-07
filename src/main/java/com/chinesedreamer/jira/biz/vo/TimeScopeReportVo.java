package com.chinesedreamer.jira.biz.vo;

import java.util.List;

import lombok.Setter;


import lombok.Getter;

/**
 * Description: 某个时间范围内的报表
 * @author Paris
 * @date May 7, 20159:52:43 AM
 * @version beta
 */
public @Getter @Setter class TimeScopeReportVo {
	private String startDate;//开始时间
	private String endDate;//结束时间
	private List<ProjectReportVo> projects;
	private List<UserReportVo> users;
}
