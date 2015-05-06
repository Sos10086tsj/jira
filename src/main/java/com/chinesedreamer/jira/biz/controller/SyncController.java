package com.chinesedreamer.jira.biz.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinesedreamer.jira.biz.core.project.service.JiraProjectService;
import com.chinesedreamer.jira.biz.service.JiraSyncService;
import com.chinesedreamer.jira.core.JiraException;

/**
 * Description: 
 * @author Paris
 * @date May 5, 20151:19:34 PM
 * @version beta
 */
@Controller
@RequestMapping(value = "sync")
public class SyncController {
	
	@Resource
	private JiraSyncService jiraSyncService;
	@Resource
	private JiraProjectService jiraProjectService;
	
	/**
	 * 同步首页
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String sync(Model model){
		return "/sync/index";
	}
	
	/**
	 * 同步用户列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public String showSyncUser(Model model){
		return "/sync/syncUser";
	}
	
	/**
	 * 提交同步用户请求
	 * @param model
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "user", method = RequestMethod.POST)
	public void  syncUser(Model model, String username) throws JiraException{
		this.jiraSyncService.syncUser(username);
	}
	
	@RequestMapping(value = "project", method = RequestMethod.GET)
	public String showSyncProject(Model model){
		return "/sync/syncProject";
	}
	@ResponseBody
	@RequestMapping(value = "project", method = RequestMethod.POST)
	public void  syncProject(Model model, String project) throws JiraException{
		this.jiraSyncService.syncProject(project);
	}
	
	@ResponseBody
	@RequestMapping(value = "priority", method = RequestMethod.POST)
	public void  syncPriority(Model model) throws JiraException{
		this.jiraSyncService.syncPriority();
	}
	
	@ResponseBody
	@RequestMapping(value = "issueType", method = RequestMethod.POST)
	public void  syncIssueType(Model model) throws JiraException{
		this.jiraSyncService.syncIssueType();
	}
	
	@ResponseBody
	@RequestMapping(value = "issueStatus", method = RequestMethod.POST)
	public void  syncIssueStatus(Model model) throws JiraException{
		this.jiraSyncService.syncStatus();
	}
	
	@RequestMapping(value = "project/version", method = RequestMethod.GET)
	public String showSyncProjectVersion(Model model){
		model.addAttribute("projects", this.jiraProjectService.getAllProjects());
		return "/sync/syncProjectVersion";
	}
	
	@ResponseBody
	@RequestMapping(value = "project/version", method = RequestMethod.POST)
	public void syncProjectVersion(Model model, String projectIdOrKey) throws JiraException{
		this.jiraSyncService.syncProjectVersion(projectIdOrKey);
	}
	
	@RequestMapping(value = "project/issue", method = RequestMethod.GET)
	public String showSyncProjectIssue(Model model){
		model.addAttribute("projects", this.jiraProjectService.getAllProjects());
		return "/sync/syncProjectIssue";
	}
	
	@ResponseBody
	@RequestMapping(value = "project/issue", method = RequestMethod.POST)
	public void syncProjectIssue(Model model, String projectIdOrKey) throws JiraException{
		this.jiraSyncService.syncProjectIssue(projectIdOrKey);
	}
}
