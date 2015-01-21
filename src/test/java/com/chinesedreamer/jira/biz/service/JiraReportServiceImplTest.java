package com.chinesedreamer.jira.biz.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.chinesedreamer.jira.base.SpringTest;
import com.chinesedreamer.jira.biz.vo.ReportTaskVo;
import com.chinesedreamer.jira.core.JiraException;

/**
 * Description: 
 * @author Paris
 * @date Jan 20, 20158:55:37 AM
 * @version beta
 */
public class JiraReportServiceImplTest extends SpringTest{
	@Resource
	private JiraReportService jiraReportService;
	@Test
	public void testGenerateDailyReport() {
		try {
			List<ReportTaskVo> vos = this.jiraReportService.generateDailyReport(1,2, "");
			assertNotNull(vos);
			for (ReportTaskVo reportTaskVo : vos) {
				System.out.println(reportTaskVo);
			}
		} catch (JiraException e) {
			e.printStackTrace();
		}
	}

}
