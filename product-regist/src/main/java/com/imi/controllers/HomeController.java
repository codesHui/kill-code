package com.imi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 首页.
 */
@Controller
public class HomeController {
	/**
	 * 默认页面。
	 * @return
	 */
	@RequestMapping(value = {"/default","/index","/",""}, method =  RequestMethod.GET)
	public String index(){
		return "redirect:/front/index";
	}
	
	
}