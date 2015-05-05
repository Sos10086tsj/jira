package com.chinesedreamer.jira.biz.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

	private Logger logger = LoggerFactory.getLogger(SyncController.class);
	
	@Resource
	private JiraSyncService jiraSyncService;
	
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
		logger.info("********** sync user info begin, users:{}", username);
		this.jiraSyncService.syncUser(username);
		logger.info("********** sync user info end, users:{}", username);
	}
}
