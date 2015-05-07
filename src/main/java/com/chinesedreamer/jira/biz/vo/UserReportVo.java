package com.chinesedreamer.jira.biz.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Description: 
 * @author Paris
 * @date May 7, 20159:35:23 AM
 * @version beta
 */
public @Getter @Setter class UserReportVo {
	//用户信息
	private String username;//用户姓名
	
	//统计信息
	private Integer totalNum;//任务总数
	private Integer completedNum;//完成数
	private String completeRate;//完成率
	private Integer bugNum;//bug总数
	private String totalTimeSpent;//总耗时
	private String totalTimeEstimated;//总预估
	private String totalTimeout;//总超时
	
	//issue 列表
	private List<JiraIssueVo> issueVos;
}
