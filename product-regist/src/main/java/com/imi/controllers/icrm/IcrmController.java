package com.imi.controllers.icrm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.imi.entity.security.User;
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
import com.imi.service.security.IUserService;
import com.imi.support.ImportExport;
import com.softvan.aware.IUserAware;
import com.softvan.model.Json;
import com.softvan.utils.DateUtil;
import com.softvan.utils.StringUtil;

@Controller
@RequestMapping(value = { "/icrm" })
public class IcrmController implements IUserAware {
	
	private Long current_user_id;
	private String userName,userNickName,roleCode;
	
	private static final Logger logger = Logger.getLogger(IcrmController.class);
	private String tempStoragePath = "uploadfiles" + File.separator
			+ "reportPolicy";
	
	@Resource
	private IUserService userService;
	
	@Resource
	private IPolicyInformationService reportPolicyService;
	
    @Resource
   	private IPolicySummaryService policySumService;
    
    @RequestMapping(value={"/getAllCompany"})
	@ResponseBody
	public Json getAllCompany(HttpServletRequest request){
		Json result = new Json();
		//获取险类

		List a= new ArrayList();
        int index = 0;
        List<MemberUser> lst = this.reportPolicyService.getAllCompany(this.current_user_id);
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
	@RequestMapping(value={"/list"})
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
		
		//查询menber表的registerCode
		List<MemberUser> lst = this.reportPolicyService.getAllCompany(current_user_id);
		policyInformation.setPolicyUploadUnit(lst.get(0).getRegisteredCode());
		
		try {
			reportPolicyService.queryList(policyInformation);
//			complaintsServiceImpl.findDisposeComplaints(productInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("userNickName",userNickName);
		request.setAttribute("policyInformation",policyInformation);
		//List<MemberUser> musers = reportPolicyService.getAllCompany(this.current_user_id);
		request.setAttribute("musers",lst);
		return "policy_icrm/policy_list";
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
			reportPolicy =reportPolicyService.queryDetails(policyId);
//			complaintsServiceImpl.findDisposeComplaints(productInfo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("reportPolicy",reportPolicy);
		if(null != reportPolicy.getPolicyRate() ){
			model.addAttribute("policyRate",reportPolicy.getPolicyRate().toString());
		}
		return "policy_icrm/policy_"+riskType;
	}
	
	/**
	 * 详情下载
	 * @param reportPolicy
	 * @param request
	 */
	@RequestMapping(value={"/upload"})
	public void upload(@ModelAttribute(value="policyInformation")PolicyInformation policyInformation,HttpServletRequest request,
			HttpServletResponse response){
		try{
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
		
		this.reportPolicyService.queryList(policyInformation);
		reportPolicyList= policyInformation.getListPolicy();
		//如果选择了险种
		if(!StringUtils.isBlank(riskType)){
			//08CT集装箱保险、08OV远洋船舶险 、09IE进出口货运险 、05PI保赔保险、05OP船舶油污责任险
			if(reportPolicyList.size()>0){
				//导出单个险种信息
				if("08CT".equals(riskType)){
					Workbook workbook = ImportExport.exportExcel(message08CT.getClass(),"集装箱保险", reportPolicyList);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String fileName = "集装箱保险"+format.format(new Date()) + ".xlsx";
					download(request,response,fileName,workbook);
				}else if("08OV".equals(riskType)){
					
					Workbook workbook = ImportExport.exportExcel(message08OV.getClass(),"远洋船舶险", reportPolicyList);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String fileName = "远洋船舶险"+format.format(new Date()) + ".xlsx";
					download(request,response,fileName,workbook);
				}
				else if("09IE".equals(riskType)){
					
					Workbook workbook = ImportExport.exportExcel(message09IE.getClass(),"进出口货运险", reportPolicyList);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String fileName = "进出口货运险"+format.format(new Date()) + ".xlsx";
					download(request,response,fileName,workbook);
				}
				else if("05PI".equals(riskType)){
					
					Workbook workbook = ImportExport.exportExcel(message05PI.getClass(),"保赔保险", reportPolicyList);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String fileName = "保赔保险"+format.format(new Date()) + ".xlsx";
					download(request,response,fileName,workbook);
				}
				else if("05OP".equals(riskType)){
					
					Workbook workbook = ImportExport.exportExcel(message05OP.getClass(),"船舶油污责任险", reportPolicyList);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String fileName = "船舶油污责任险"+format.format(new Date()) + ".xlsx";
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
					}if("05PI".equals(i.getRiskType())){
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
			//多脚本excel生成08CT集装箱保险、08OV远洋船舶险 、09IE进出口货运险 、05PI保赔保险、05OP船舶油污责任险
			Workbook workbook = ImportExport.exportExcel2(message08CT.getClass(),message08OV.getClass(),message09IE.getClass(),message05PI.getClass(),message05OP.getClass(), reportPolicy08CTList
										,reportPolicy08OVList,reportPolicy09IEList,reportPolicy05PIList,reportPolicy05OPList);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String fileName = "保单信息"+format.format(new Date()) + ".xlsx";
			download(request,response,fileName,workbook);
		
		}}catch(Exception ex){
			ex.printStackTrace();
		}
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
			 response.setHeader("Content-Disposition","attachment; filename=" + fileName);
			 workbook.write(response.getOutputStream());
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}
	
	@RequestMapping(value={"/uploadSum"})
	public void uploadSum(ModelMap model,HttpServletRequest request,HttpServletResponse response,String summaryStartTime,String summaryEndTime,
			String riskType,String reportUnit){
		try{
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
			String fileName = "汇总信息"+format.format(new Date()) + ".xlsx";
			download(request,response,fileName,workbook);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * 模板下载
	 * @param response
	 * @param fileName
	 * @param workbook
	 */
	@RequestMapping(value = { "/downloadModel" })
	public void downloadModel(HttpServletRequest request,HttpServletResponse response){
        String url = request.getSession().getServletContext().getRealPath("/") + "resources"+
        		File.separator +"excel" + File.separator +"登记模板.xlsx";  
		
		File tempFile = new File(url);
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(tempFile));
			download(request,response,"登记模板.xlsx",workbook);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 模板填写描述
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value = { "/downloadDesc" })
	public void downloadDesc(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String url = request.getSession().getServletContext().getRealPath("/") + "resources"+
        		File.separator +"word" + File.separator +"使用说明.docx";  
        File file = new File(url);  
        String fileName = file.getName();
        String userAgent = request.getHeader("user-agent").toLowerCase();  
		 if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
		         // win10 ie edge 浏览器 和其他系统的ie  
			 fileName = URLEncoder.encode(fileName, "UTF-8");  
		 } else {  
		         // fe  
			 fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");  
		 }
        
       
        InputStream fin = null;  
        OutputStream out = null;  
        try {  
            // 调用工具类的createDoc方法生成Word文档  
            fin = new FileInputStream(file);  
  
            response.setCharacterEncoding("utf-8");  
            response.setContentType("application/msword");  
            // 设置浏览器以下载的方式处理该文件名  
            //String fileName = "材质单"+DateUtils.curDateTimeStr14() + ".doc";  
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);  
  
            out = response.getOutputStream();  
            byte[] buffer = new byte[512];  // 缓冲区  
            int bytesToRead = -1;  
            // 通过循环将读入的Word文件的内容输出到浏览器中  
            while((bytesToRead = fin.read(buffer)) != -1) {  
                out.write(buffer, 0, bytesToRead);  
            }  
        } finally {  
            if(fin != null) fin.close();  
            if(out != null) out.close();  
        }  
	}
	
	/**
	 * 模板下载
	 * @param response
	 * @param fileName
	 * @param workbook
	 */
	@RequestMapping(value = { "/downloadWrong" })
	public void downloadWrong(HttpServletRequest request,HttpServletResponse response){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
        String url = tempStoragePath + File.separator + sdf.format(new Date())+ File.separator + this.current_user_id;
		File tempFile = new File(url+File.separator+"信息登记_wrong.xlsx");
		if(!tempFile.exists() ){
			tempFile = new File(url+File.separator+"信息登记_wrong.xls");
		}
		Workbook workbook = null;
		try {
			if(tempFile.getName().endsWith(".xlsx")){
				workbook = new XSSFWorkbook(new FileInputStream(tempFile));
				download(request,response,"信息登记_wrong.xlsx",workbook);
			}else{
				workbook = new HSSFWorkbook(new FileInputStream(tempFile));
				download(request,response,"信息登记_wrong.xls",workbook);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		List<MemberUser> musers = reportPolicyService.getAllCompany(current_user_id);
		policySummaryInfo.setReportUnit(musers.get(0).getRegisteredCode());
		try {
			policySumService.queryListForPage(policySummaryInfo);
//			complaintsServiceImpl.findDisposeComplaints(productInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		request.setAttribute("musers", musers);
		request.setAttribute("policySummaryInfo", policySummaryInfo);
		//request.setAttribute("reportUnit", musers.get(0).getRegisteredCode());
		return "policy_icrm/policy_summary";
	}
	
	/**
	 * 修改密码。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/modifyPassword"}, method = RequestMethod.POST)
	@ResponseBody
	public Json modifyPassword(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		Json result = new Json();
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		try {
			userService.modifyPassword(current_user_id, oldPassword, newPassword);
			result.setMsg("1");
		} catch (Exception e) {
			result.setMsg("0");
		}
		result.setSuccess(true);
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("userNickName",userNickName);
		return result;
	}
	/**
	 * 获取修改密码页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/modifyPassword_init"})
	public String modifyPassword_init(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		
		User user = new User();
		user = userService.findById(current_user_id);
		request.setAttribute("userNickName",userNickName);
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("oldPassword", user.getPassword());
		
		return "icrm_modifyPassword";
	}
	

	@Override
	public void setUserId(Long userId) {
		current_user_id=userId;
		
	}
	@Override
	public void setUserName(String userName) {
		this.userName=userName;
		
	}
	@Override
	public void setUserNickName(String userNickName) {
		this.userNickName=userNickName;
		
	}
	@Override
	public void setRoleCode(String roleCodes) {
		this.roleCode=roleCodes;
		
	}

	
		
		
}
