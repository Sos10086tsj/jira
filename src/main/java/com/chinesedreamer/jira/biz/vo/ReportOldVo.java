package com.chinesedreamer.jira.biz.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 201510:30:14 AM
 * @version beta
 */
public @Getter @Setter class ReportOldVo {
	private String filterName;
	private List<ReportTaskOldVo> vos;
	private ReportTaskSummaryOldVo summary;
	
	public ReportOldVo(String filterName, List<ReportTaskOldVo> vos,ReportTaskSummaryOldVo summary){
		this.filterName = filterName;
		this.vos = vos;
		this.summary = summary;
	}
}
