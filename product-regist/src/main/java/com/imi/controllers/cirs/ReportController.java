package com.imi.controllers.cirs;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imi.controllers.admin.verifys.VerifysController;
import com.imi.entity.setting.MemberUser;
import com.imi.model.member.MemberUserInfo;
import com.imi.service.report.ReportService;
import com.imi.service.security.IMemberUserService;

@Controller(value="cirsAdminReportController")
@RequestMapping(value={"/cirsAdmin/report"})
public class ReportController {
	
	private static final Logger logger = Logger.getLogger(VerifysController.class);
	@Resource
	private ReportService reportService;
	@Resource
	private IMemberUserService memberUserService;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test3(ModelMap model, String registTimeStart,
			String registTimeEnd, String registeredCode) {
		if (logger.isDebugEnabled())
			logger.debug("加载登录页面...");

		model.addAttribute("registTimeStart", registTimeStart);
		model.addAttribute("registTimeEnd", registTimeEnd);
		model.addAttribute("registeredCode", registeredCode);

		model.addAttribute("reportMap", reportService.getReportRisk(model));
		MemberUserInfo muser = new MemberUserInfo();
		List<MemberUser> musers = memberUserService.findMemberUsers(muser);
		model.addAttribute("musers", musers);
		return "report_cirs/test3";
	}
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
public String test2(ModelMap model,String registTimeStart,String registTimeEnd,String productType){
		if(logger.isDebugEnabled()) logger.debug("加载登录页面...");
	
		
		model.addAttribute("registTimeStart",registTimeStart);
		model.addAttribute("registTimeEnd",registTimeEnd);
		model.addAttribute("productType",productType);
		model.addAttribute("reportList", reportService.getReportCompany(model));



		return "report_cirs/test2";
	}
}
