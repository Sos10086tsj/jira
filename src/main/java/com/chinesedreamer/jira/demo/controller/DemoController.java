package com.chinesedreamer.jira.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Description: 
 * @author Paris
 * @date Jan 14, 20152:07:19 PM
 * @version beta
 */
@Controller
@RequestMapping(value = "demo")
public class DemoController {
	@RequestMapping(value = "gridDemo",method = RequestMethod.GET)
	public String gridDemo(Model model, HttpServletRequest request){
		return "demo/gridDemo";
	}
}
