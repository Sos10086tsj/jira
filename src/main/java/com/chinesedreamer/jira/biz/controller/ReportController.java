package com.chinesedreamer.jira.biz.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinesedreamer.jira.biz.core.project.service.JiraProjectService;
import com.chinesedreamer.jira.biz.core.version.model.JiraVersion;
import com.chinesedreamer.jira.biz.core.version.service.JiraVersionService;
import com.chinesedreamer.jira.biz.rptconfig.service.JiraRptVersionConfigService;
import com.chinesedreamer.jira.biz.service.JiraReportService;
import com.chinesedreamer.jira.core.JiraException;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20151:30:00 PM
 * @version beta
 */
@Controller
@RequestMapping(value = "report")
public class ReportController {
	
	@Resource
	private JiraProjectService jiraProjectService;
	@Resource
	private JiraVersionService jiraVersionService;
	@Resource
	private JiraReportService jiraReportService;
	@Resource
	private JiraRptVersionConfigService jiraRptVersionConfigService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		return "/report/index";
	}
	
	@RequestMapping(value = "projectReport", method = RequestMethod.GET)
	public String projectReport(Model model){
		model.addAttribute("projects", this.jiraProjectService.getAllProjects());
		return "/report/projectReport";
	}
	
	@ResponseBody
	@RequestMapping(value = "projectReport/version", method = RequestMethod.POST)
	public List<JiraVersion>  getProjectVersion(Model model, String projectJiraId) throws JiraException{
		return this.jiraVersionService.getProjectVersions(projectJiraId);
	}
	
	@RequestMapping(value = "showProjectReport", method = RequestMethod.GET)
	public String generateProjectReport(Model model,String projectJiraId,String versionJiraId){
		model.addAttribute("report", this.jiraReportService.generateProjectReport(projectJiraId, versionJiraId));
		return "/report/showProjectReport";
	}
	
	@RequestMapping(value = "timeScopeReport", method = RequestMethod.GET)
	public String timeScopeReport(Model model){
		return "/report/timeScopeReport";
	}
	
	@RequestMapping(value = "showTimeScopeReport", method = RequestMethod.GET)
	public String generateTimeScopeReport(Model model,String start,String end) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("template", this.jiraReportService.generateTimeScopeReport(format.parse(start), format.parse(end)));
		return "/report/showTimeScopeReport";
	}
	
	/**
	 * 配置
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "projectReportConfig", method = RequestMethod.GET)
	public String showProjectReportConfig(Model model){
		model.addAttribute("configs", this.jiraRptVersionConfigService.getAllVos());
		model.addAttribute("projects", this.jiraProjectService.getAllProjects());
		return "/report/projectReportConfig";
	}
	
	@RequestMapping(value = "projectReportConfig/add", method = RequestMethod.POST)
	public String addProjectReportConfig(Model model, String projectJiraId,String versionJiraId){
		this.jiraRptVersionConfigService.addConfig(projectJiraId, versionJiraId);
		return "redirect:/report/projectReportConfig";
	}
	
	@RequestMapping(value = "projectReportConfig/delete", method = RequestMethod.GET)
	public String deleteProjectReportConfig(Model model, String projectJiraId,String versionJiraId){
		this.jiraRptVersionConfigService.deleteConfig(projectJiraId, versionJiraId);
		return "redirect:/report/projectReportConfig";
	}
	
	
}
