package com.chinesedreamer.jira.curd.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinesedreamer.jira.biz.service.JiraReportService;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.greenhopper.Sprint;

/**
 * Description: 
 * @author Paris
 * @date Jan 20, 20151:52:08 PM
 * @version beta
 */
@Controller
@RequestMapping(value = "report")
public class ReportController {
	@Resource
	private JiraReportService jiraReportService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showReportBoard(Model model) throws JiraException{
		model.addAttribute("rapidViews", this.jiraReportService.loadRapidViews());
		model.addAttribute("sprints", this.jiraReportService.loadRapidViewSprints(23));//default gaphone-taosj
		return "report/reportBoard";
	}
	
	@ResponseBody
	@RequestMapping(value = "loadRapidViewSprint", method = RequestMethod.POST)
	public List<Sprint> loadRapidViewSprint(Model model, HttpServletRequest request) throws JiraException{
		int rapidViewId = Integer.parseInt(request.getParameter("rapidViewId"));
		List<Sprint> sprints = this.jiraReportService.loadRapidViewSprints(rapidViewId);
		return sprints;
	}
	
	@RequestMapping(value = "generateSprintReport", method = RequestMethod.GET)
	public String generateSprintReport(Model model, HttpServletRequest request) throws JiraException{
		int rapidViewId = Integer.parseInt(request.getParameter("rapidViewId"));
		int sprintId = Integer.parseInt(request.getParameter("sprintId"));
		model.addAttribute("vos",this.jiraReportService.generateDailyReport(rapidViewId, sprintId, ""));
		return "report/report";
	}
}
