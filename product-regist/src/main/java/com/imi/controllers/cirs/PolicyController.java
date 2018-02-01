package com.imi.controllers.cirs;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.controllers.admin.security.ModuleConstant;
import com.imi.entity.reportPolicy.ReportPolicy;
import com.imi.entity.reportPolicy.ReportSummary;
import com.imi.entity.security.Right;
import com.imi.entity.setting.MemberUser;
import com.imi.model.excel.PolicySummaryExcel;
import com.imi.model.policy.PolicyInformation;
import com.imi.model.policy.PolicySummaryInfo;
import com.imi.model.policy.SummaryVo;
import com.imi.service.policy.IPolicyInformationService;
import com.imi.service.policy.IPolicySummaryService;
import com.imi.support.ImportExport;
import com.softvan.aware.IUserAware;
import com.softvan.model.Json;
import com.softvan.utils.DateUtil;

@Controller(value="cirsAdminPolicyController")
@RequestMapping(value = "/cirsAdmin/policy")
public class PolicyController implements IUserAware{
	private static final Logger logger = Logger.getLogger(PolicyController.class);
	
    @Resource
	private IPolicyInformationService policyInformationService;
	
	private Long current_user_id;
	
    @Resource
   	private IPolicySummaryService policySumService;
    
    @RequestMapping(value={"/getAllCompany"})
	@ResponseBody
	public Json getAllCompany(HttpServletRequest request){
		Json result = new Json();
		//获取险类

		List a= new ArrayList();
        int index = 0;
        List<MemberUser> lst = this.policyInformationService.getAllCompany(null);
        for (MemberUser user : lst) {
        	a.add(user.getRegisteredCode()+"|"+user.getCompanyName());
        }
	       
		result.setSuccess(true);
		result.setData(a);
		return result;
	}
    
    @RequestMapping(value = "/summaryLst")
	public String test2(@ModelAttribute(value="policySummaryInfo")PolicySummaryInfo policySummaryInfo,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载汇总信息查询页面...");
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
		MemberUser user = new MemberUser();
		user.setId(current_user_id);
		policySummaryInfo.setUser(user);
		if(policySummaryInfo.getPage()==null){
			policySummaryInfo.setPage(1);
		}

		policySummaryInfo.setRows(10);
		policySummaryInfo.setCurrentRowNo((policySummaryInfo.getPage()-1)*policySummaryInfo.getRows()+1);
		policySummaryInfo.setCurrentUserId(current_user_id);
		try {
			policySumService.queryListForPage(policySummaryInfo);
//			complaintsServiceImpl.findDisposeComplaints(productInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<MemberUser> musers = policyInformationService.getAllCompany(null);

		request.setAttribute("musers", musers);
		request.setAttribute("policySummaryInfo", policySummaryInfo);
		return "policy_cirs/policy_summary";
	}
	
	/**
	 * 跳转管理员信息管理页面
	 * 
	 * @param productInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/list"})
	public String disposeList(@ModelAttribute(value="policyInformation")PolicyInformation policyInformation,HttpServletRequest request,HttpServletResponse response){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
		
		MemberUser user = new MemberUser();
		user.setId(current_user_id);
		policyInformation.setUser(user);
		if(policyInformation.getPage()==null){
			policyInformation.setPage(1);
		}

		policyInformation.setRows(10);
		policyInformation.setCurrentRowNo((policyInformation.getPage()-1)*policyInformation.getRows()+1);
		try {
			policyInformationService.queryList(policyInformation);
//			complaintsServiceImpl.findDisposeComplaints(productInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("policyInformation",policyInformation);
		List<MemberUser> musers = policyInformationService.getAllCompany(null);
		request.setAttribute("musers",musers);
		return "policy_cirs/policy_list";
	}
	/**
	 * 跳转到信息查询页面
	 * 
	 */
	@RequestMapping(value={"/query"})
	public String query(){
		return "policy_cirs/policy_query";
	}
	/**
	 * 点击详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/details"})
	public String details(Model model,HttpServletRequest request){
		ReportPolicy reportPolicy = new ReportPolicy();
		String riskType = request.getParameter("riskType");
		Long policyId = Long.parseLong(request.getParameter("policyId")); 
		//通过policyId查询详情
		try {
			reportPolicy =policyInformationService.queryDetails(policyId);
//			complaintsServiceImpl.findDisposeComplaints(productInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("reportPolicy",reportPolicy);
		if(null != reportPolicy.getPolicyRate() ){
			model.addAttribute("policyRate",reportPolicy.getPolicyRate().toString());
		}
		return "policy_cirs/policy_"+riskType;
	}
	
	@RequestMapping(value={"/uploadSum"})
	public void uploadSum(ModelMap model,HttpServletRequest request,HttpServletResponse response,String summaryStartTime,String summaryEndTime,
			String riskType,String reportUnit){
		List<ReportSummary> list = new ArrayList<ReportSummary>();
		PolicySummaryExcel messageSum = new PolicySummaryExcel();
		//获取数据
		model.addAttribute("summaryStartTime",summaryStartTime);
		model.addAttribute("summaryEndTime",summaryEndTime);
		model.addAttribute("riskType",riskType);
		model.addAttribute("reportUnit",reportUnit);
		List<SummaryVo> summaryInfoList= policySumService.queryList(model);
		//导出报表
		Workbook workbook = ImportExport.exportExcel(messageSum.getClass(),"汇总信息", summaryInfoList);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = "汇总信息"+format.format(new Date());
		download(request,response,fileName,workbook);
	}
	
	public void download(HttpServletRequest request,HttpServletResponse response,String fileName,Workbook workbook){
		try {
			
			 String userAgent = request.getHeader("user-agent").toLowerCase();  
			 if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
			         // win10 ie edge 浏览器 和其他系统的ie  
				 fileName = URLEncoder.encode(fileName, "UTF-8");  
			 } else {  
			         // fe  
				 fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");  
			 }
			 response.setContentType("application/octet-stream; charset=UTF-8");
			 response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".xlsx");
			 workbook.write(response.getOutputStream());
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}
	@Override
	public void setUserId(Long userId) {
		current_user_id = userId;
		
	}
	@Override
	public void setUserName(String userName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setUserNickName(String userNickName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setRoleCode(String roleCodes) {
		// TODO Auto-generated method stub
		
	}
	
}
