package com.imi.controllers.admin.policy;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.controllers.admin.security.ModuleConstant;
import com.imi.entity.reportPolicy.ReportPolicy;
import com.imi.entity.reportPolicy.ReportSummary;
import com.imi.entity.security.Right;
import com.imi.entity.setting.MemberUser;
import com.imi.model.excel.PolicySummaryExcel;
import com.imi.model.excel.Risk05OPMessageExcel;
import com.imi.model.excel.Risk05PIMessageExcel;
import com.imi.model.excel.Risk08CTMessageExcel;
import com.imi.model.excel.Risk08OVMessageExcel;
import com.imi.model.excel.Risk09IEMessageExcel;
import com.imi.model.policy.PolicyInformation;
import com.imi.model.policy.PolicySummaryInfo;
import com.imi.model.policy.SummaryVo;
import com.imi.service.policy.IPolicyInformationService;
import com.imi.service.policy.IPolicySummaryService;
import com.imi.service.security.IMemberUserService;
import com.imi.support.ImportExport;
import com.softvan.aware.IUserAware;
import com.softvan.model.Json;
import com.softvan.utils.DateUtil;
import com.softvan.utils.StringUtil;

@Controller(value="adminPolicyController")
@RequestMapping(value = "/admin/policy")
public class PolicyController implements IUserAware{
	private static final Logger logger = Logger.getLogger(PolicyController.class);
	
	private static final SimpleDateFormat farmat = new SimpleDateFormat("yyyy-MM-dd");
    @Resource
	private IPolicyInformationService policyInformationService;
    @Resource
   	private IPolicySummaryService policySumService;
    
	private Long current_user_id;
	private String userNickName;
	
	@Resource
	private IMemberUserService memberUserService;
	
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
		return "policy/policy_summary";
	}
	
	
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
	
	/**
	 * 跳转管理员信息管理页面
	 * 
	 * @param productInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/listAll"})
	public String disposeList2(@ModelAttribute(value="policyInformation")PolicyInformation policyInformation,HttpServletRequest request,HttpServletResponse response){
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
		policyInformation.setCurrentUserId(current_user_id);
		try {
			policyInformationService.queryList(policyInformation);
//			complaintsServiceImpl.findDisposeComplaints(productInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("userNickName",userNickName);
		request.setAttribute("policyInformation",policyInformation);
		List<MemberUser> musers = policyInformationService.getAllCompany(null);
		request.setAttribute("musers",musers);
		return "policy/policy_list";
	}
	/**
	 * 跳转到信息查询页面
	 * 
	 */
	@RequestMapping(value={"/query"})
	public String query(){
		return "policy/policy_query";
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
		String policyIdReq = request.getParameter("policyId");
		if(StringUtil.isNotNull(policyIdReq)){
			if(policyIdReq.contains(",")){
				policyIdReq = policyIdReq.replace(",", "");
			}
		}
		long policyId = Long.parseLong(policyIdReq); 
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
		return "policy/policy_"+riskType;
	}
	/**
	 * 详情下载
	 * @param reportPolicy
	 * @param request
	 * @throws ParseException 
	 */
	@RequestMapping(value={"/upload"})
	public void upload(@ModelAttribute(value="policyInformation")PolicyInformation policyInformation,HttpServletRequest request,HttpServletResponse response) throws ParseException{
		String riskType = policyInformation.getRiskType();
		policyInformation.setPage(1);
		policyInformation.setRows(100000);
		//所有险种信息数据
		List<ReportPolicy> reportPolicyList = new ArrayList<ReportPolicy>();
		//单个险种数据
		List<ReportPolicy> reportPolicy08CTList = new ArrayList<ReportPolicy>();
		Risk08CTMessageExcel message08CT = new Risk08CTMessageExcel();
		
		List<ReportPolicy> reportPolicy08OVList = new ArrayList<ReportPolicy>();
		Risk08OVMessageExcel message08OV = new Risk08OVMessageExcel();
		
		List<ReportPolicy> reportPolicy09IEList = new ArrayList<ReportPolicy>();
		Risk09IEMessageExcel message09IE = new Risk09IEMessageExcel();
		
		List<ReportPolicy> reportPolicy05PIList = new ArrayList<ReportPolicy>();
		Risk05PIMessageExcel message05PI = new Risk05PIMessageExcel();
		
		List<ReportPolicy> reportPolicy05OPList = new ArrayList<ReportPolicy>();
		Risk05OPMessageExcel message05OP = new Risk05OPMessageExcel();
		
		policyInformationService.queryList(policyInformation);
		reportPolicyList= policyInformation.getListPolicy();
			//如果选择了险种
			if(!StringUtils.isBlank(riskType)){
				//08CT集装箱保险、08OV远洋船舶险 、09IE进出口货运险 、05PI保赔保险、05OP船舶油污责任险
				if(reportPolicyList.size()>0){
					//导出单个险种信息
					if("08CT".equals(riskType)){
						Workbook workbook = ImportExport.exportExcel(message08CT.getClass(),"集装箱保险", reportPolicyList);
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						String fileName = "集装箱保险"+format.format(new Date());
						download(request,response,fileName,workbook);
					}else if("08OV".equals(riskType)){
						
						Workbook workbook = ImportExport.exportExcel(message08OV.getClass(),"远洋船舶险", reportPolicyList);
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						String fileName = "远洋船舶险"+format.format(new Date());
						download(request,response,fileName,workbook);
					}
					else if("09IE".equals(riskType)){
						
						Workbook workbook = ImportExport.exportExcel(message09IE.getClass(),"进出口货运险", reportPolicyList);
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						String fileName = "进出口货运险"+format.format(new Date());
						download(request,response,fileName,workbook);
					}
					else if("05PI".equals(riskType)){
						
						Workbook workbook = ImportExport.exportExcel(message05PI.getClass(),"保赔保险", reportPolicyList);
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						String fileName = "保赔保险"+format.format(new Date());
						download(request,response,fileName,workbook);
					}
					else if("05OP".equals(riskType)){
						
						Workbook workbook = ImportExport.exportExcel(message05OP.getClass(),"船舶油污责任险", reportPolicyList);
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						String fileName = "船舶油污责任险"+format.format(new Date());
						download(request,response,fileName,workbook);
					}
				}
			}else{
				//没有选择具体险种导出多脚本格式
				if(reportPolicyList.size()>0){
					for(ReportPolicy i:reportPolicyList){
						if("08CT".equals(i.getRiskType())){
							reportPolicy08CTList.add(i);
							continue;
						}
						if("05OP".equals(i.getRiskType())){
							reportPolicy05OPList.add(i);
							continue;
						}
						if("05PI".equals(i.getRiskType())){
							reportPolicy05PIList.add(i);
							continue;
						}
						if("09IE".equals(i.getRiskType())){
							reportPolicy09IEList.add(i);
							continue;
						}
						if("08OV".equals(i.getRiskType())){
							reportPolicy08OVList.add(i);
						}
					}
				}
			}
				//多脚本excel生成08CT集装箱保险、08OV远洋船舶险 、09IE进出口货运险 、05PI保赔保险、05OP船舶油污责任险
				Workbook workbook = ImportExport.exportExcel2(message08CT.getClass(),message08OV.getClass(),message09IE.getClass(),message05PI.getClass(),message05OP.getClass(), reportPolicy08CTList
											,reportPolicy08OVList,reportPolicy09IEList,reportPolicy05PIList,reportPolicy05OPList);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String fileName = "保单信息"+format.format(new Date());
				download(request,response,fileName,workbook);
		
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
		this.userNickName = userNickName;
	}
	@Override
	public void setRoleCode(String roleCodes) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) throws ParseException {
		String date = new Date().toString();
		String aa= "2017-01-01";
		System.out.println(StringUtils.substring(aa, 5,7));
		System.out.println(Integer.parseInt("01"));
		
	}
}
