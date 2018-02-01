package com.imi.controllers.icrm.policy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.imi.controllers.icrm.policy.util.ConvertData;
import com.imi.entity.reportPolicy.ReportPolicy;
import com.imi.entity.reportPolicy.ReportPolicyHistory;
import com.imi.entity.reportPolicy.ReportSummary;
import com.imi.entity.security.User;
import com.imi.entity.setting.MemberUser;
import com.imi.model.member.MemberUserInfo;
import com.imi.service.policy.IPolicyHistoryService;
import com.imi.service.policy.IPolicyInformationService;
import com.imi.service.policy.IPolicySummaryService;
import com.imi.service.security.IMemberUserService;
import com.imi.support.UploadFileUtil;
import com.softvan.aware.IUserAware;
import com.softvan.model.Json;
import com.softvan.utils.CollectionUtil;
import com.softvan.utils.DateUtil;
import com.softvan.utils.StringUtil;

@Controller(value="icrmPolicyController")
@RequestMapping(value = "/icrm/policy")
public class PolicyController implements IUserAware{

	@Resource
	private IMemberUserService memberUserService;
	
	private Long current_user_id;
	private String userName,userNickName,roleCode;
	
	private static final Logger logger = Logger.getLogger(PolicyController.class);
	
	private String tempStoragePath = "uploadfiles" + File.separator + "reportPolicy";
	
	@Resource
	private UploadFileUtil uploadFileUtil;
	
	@Resource
	private IPolicyInformationService reportPolicyService;
	
	@Resource
	private IPolicySummaryService summaryService;
	
	@Resource
	private IPolicyHistoryService policyHistoryService;
	
	//定义的不需要做非空校验的列

	
	/**
	 * 加载信息上报页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/reportPolicyInit" })
	public String reportPolicyInit(HttpServletRequest request,HttpServletResponse response) {
		
		if (logger.isDebugEnabled())
			logger.debug("加载信息上报页面...");

		request.setAttribute("userNickName",userNickName);
		return "policy_icrm/policy_upload";
	}
	
	/**
	 * 加载保单上传文件轨迹信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/getPolicyHistory"})
	@ResponseBody
	public Json getPolicyHistory(HttpServletRequest request){
		Json result = new Json();
		//获取险类
		List a= new ArrayList();
        int index = 0;
        List<ReportPolicyHistory> lst = policyHistoryService.queryList(this.current_user_id);
        if(!CollectionUtil.isNotNull(lst)){
        	result.setSuccess(false);
        }else{
        	 for (ReportPolicyHistory history : lst) {
             	String isSuccess = "成功" ;
             	if("0".equals(history.getIsSuccess())){
             		isSuccess = "失败";
             	}
             	a.add(DateUtil.convertDate(history.getReportTime(), "yyyyMMdd  HH:mm:ss")+"|"+history.getFileName() + "|" + isSuccess);
             }
        	 result.setSuccess(true);
        }

		result.setData(a);
		return result;
	}
	

	private void responseMessage(HttpServletResponse response, Json json) throws JsonGenerationException, JsonMappingException, IOException {  
		ObjectMapper mapper = new ObjectMapper();  
		response.setCharacterEncoding("UTF-8");
		PrintWriter printwriter = response.getWriter();
		String str = mapper.writeValueAsString(json);
    	printwriter.print(str);
    	printwriter.flush();
		printwriter.close();
	} 
	
	/**
	 * 上报保单
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = { "/reportPolicy" },produces={"application/json; charset=UTF-8"})
	public void reportPolicy(
			@RequestParam(value = "reportPolicyFile", required = false) MultipartFile orgFile,
			HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		if (logger.isDebugEnabled()) logger.debug("进入信息上报...");
		
		Json json = new Json();
		json.setSuccess(true);

		// 源文件名
		final String orgFilename = orgFile.getOriginalFilename();
		logger.info("原文件名:" + orgFilename);
		
		//设置文件轨迹保存：isSuccess：1成功，0失败
		String remark = ""; 
		final ReportPolicyHistory policyHistory = new ReportPolicyHistory();
		policyHistory.setCreatedTime(new Date());
		policyHistory.setModifiedTime(new Date());
		policyHistory.setFileName(orgFilename);
		policyHistory.setReportTime(new Date());
		policyHistory.setUserId(this.current_user_id);

		// 1.先校验文件类型
		if (!orgFilename.endsWith(".xls") && !orgFilename.endsWith(".xlsx")) {
			// 文件格式的校验 TODO
			remark = "文件格式不正确";
			policyHistory.setIsSuccess("0");
			policyHistory.setRemark(remark);
			policyHistoryService.savePolicyReportHistory(policyHistory);
			
			json.setSuccess(false);
			json.setMsg("fileTypeWrong");
			this.responseMessage(response, json);
			return;
		}

		// 2.存储临时文件，以便进行读取
		File tempFile = uploadFileUtil.saveReportPolicyFile(orgFile,this.current_user_id);

		// 3.数据读取并校验
		Workbook workbook = null;
		Sheet sheet = null;
		Cell cell = null;
		String[] riskNameLst = new String[] { "远洋船舶险","保赔保险","进出口货运险", 
				"集装箱保险", "船舶油污责任保险", "汇总表" };
		// 需要定义六个集合，来存储六张表的数据
		final List<ReportPolicy> policyLst =  new ArrayList<ReportPolicy>();
		final List<ReportSummary> summaryLst =  new ArrayList<ReportSummary>();
		final List<String> policyNoLst =  new ArrayList<String>();
		try {
			if(orgFilename.endsWith(".xlsx")){
				workbook = new XSSFWorkbook(new FileInputStream(tempFile));// 拿到Excel
			}else{
				workbook = new HSSFWorkbook(new FileInputStream(tempFile));// 拿到Excel
			}
			
			int sheetNumbersTotal = workbook.getNumberOfSheets();// sheet总数
			boolean isWriteFlag = true;
			boolean canInsertDBFlag = true;
			if (sheetNumbersTotal == 6) {

				// 先处理前五张表
				for (int i = 0; i < sheetNumbersTotal; i++) {// 获取每个Sheet表
					logger.info("当前处理sheet[" + i + "]的数据");
					
					sheet = workbook.getSheetAt(i);
					String sheetName = sheet.getSheetName();

					if (!riskNameLst[i].equals(sheetName)) {
						
						remark = "信息上报表格与模板的表名不一致";
						policyHistory.setIsSuccess("0");
						policyHistory.setRemark(remark);
						policyHistoryService.savePolicyReportHistory(policyHistory);
						
						logger.info(remark);
						json.setSuccess(false);
						json.setMsg("sheetNameWrong");
						this.responseMessage(response, json);
						return;
					}
				
					// 定义五个数组
					int maxCellCount = sheet.getRow(0).getPhysicalNumberOfCells();
					// 每一个sheet的行处理
					for (int j = 1; j < sheet.getLastRowNum()+1; j++) {// 获取每行，数据从第二行开始
						//汇总表的总计一栏不处理

						String wrongMsg = "";
						int k = 0;
						Row row = sheet.getRow(j);
						if( null == row || "".equals(PolicyCheck.checkRowAllNull(row,maxCellCount))){
							//存在空行数据
							continue;
						}
						
						if(i == 5){
							//特别处理：如果该excel中的都为空不处理
							String result = PolicyCheck.checkSummaryAllNull(sheet);
							if("".equals(result)){
								continue;
							}
						}
						
						for (k=0; k < maxCellCount; k++) {// 获取每个单元格
							// 校验：(日期，数字，文本，模板)
							//对特定的列不做校验
							String title = sheet.getRow(0).getCell(k).getStringCellValue().trim();
							if( i ==0 ){
								if(ArrayUtils.contains(PolicyConstant.risk08OV_not_check,title)){
									continue;
								}
							}
							if( i ==1 ){
								if(ArrayUtils.contains(PolicyConstant.risk05PI_not_check,title)){
									continue;
								}
							}
							if( i ==2 ){
								if(ArrayUtils.contains(PolicyConstant.risk09IE_not_check,title)){
									continue;
								}
							}
							if( i ==3 ){
								if(ArrayUtils.contains(PolicyConstant.risk08CT_not_check,title)){
									continue;
								}
							}
							if( i ==4 ){
								if(ArrayUtils.contains(PolicyConstant.risk05OP_not_check,title)){
									continue;
								}
							}	
							
							// 1.先校验每行记录的第一个数据(保单号是否在数据库中存在)
							cell = row.getCell(k);
							if (null == cell || "null".equals(cell)||"".equals(cell)||cell.getCellType()==Cell.CELL_TYPE_BLANK) {
								//非空判断
								wrongMsg = wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--不能为空\n";
								isWriteFlag = false;
								canInsertDBFlag = false;
							}else if(cell.getCellType() == Cell.CELL_TYPE_STRING && !StringUtil.isNotNull(cell.getStringCellValue())){
								wrongMsg = wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--不能为空\n";
								isWriteFlag = false;
								canInsertDBFlag = false;
							}else{
								//表一校验
								Map<String,Object> checkResult = null;
								if(i==0){
									checkResult = PolicyCheck.checkRisk08OV(sheet, k, cell, 
											wrongMsg, isWriteFlag, canInsertDBFlag);
								}
								if(i==1){
									checkResult = PolicyCheck.checkRisk05PI(sheet, k, cell, 
											wrongMsg, isWriteFlag, canInsertDBFlag);
								}
								if(i==2){
									checkResult = PolicyCheck.checkRisk09IE(sheet, k, cell, 
											wrongMsg, isWriteFlag, canInsertDBFlag);
								}
								if(i==3){
									checkResult = PolicyCheck.checkRisk08CT(sheet, k, cell, 
											wrongMsg, isWriteFlag, canInsertDBFlag);
								}
								if(i==4){
									checkResult = PolicyCheck.checkRisk05OP(sheet, k, cell, 
											wrongMsg, isWriteFlag, canInsertDBFlag);
								}
								if(i==5){
									checkResult = PolicyCheck.checkSummary(sheet, k, cell, 
											wrongMsg, isWriteFlag, canInsertDBFlag);
								}
								wrongMsg = (String)checkResult.get("wrongMsg");
								isWriteFlag = (Boolean) checkResult.get("isWriteFlag");
								canInsertDBFlag = (Boolean) checkResult.get("canInsertDBFlag");
							}
						}
						// 获取每个sheet的最大列数
						
						// 每一行的列处理
						if (!isWriteFlag) {
							// 增加错误描述
							sheet.getRow(0).createCell(maxCellCount).setCellValue("错误描述");
							cell = row.createCell(maxCellCount);
							CellStyle cellStyle =workbook.createCellStyle();
							cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
							//设置字体颜色并加粗
							Font font = workbook.createFont();
							font.setBoldweight(Font.BOLDWEIGHT_BOLD);
							font.setColor(IndexedColors.WHITE.getIndex());
							cellStyle.setFont(font);
							cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
							cell.setCellStyle(cellStyle);
							cell.setCellValue(wrongMsg);
							isWriteFlag = true;
						} 
					}
				}
			} else {
				
				remark = "信息上报表格与模板的表数量不一致";
				policyHistory.setIsSuccess("0");
				policyHistory.setRemark(remark);
				policyHistoryService.savePolicyReportHistory(policyHistory);
				
				logger.info(remark);
				json.setSuccess(false);
				json.setMsg("sheetNumWrong");
				this.responseMessage(response, json);
				return;
			}
			// 错误文件名字
			if (!canInsertDBFlag) {
				
				policyHistory.setIsSuccess("0");
				policyHistory.setRemark("校验失败");
				policyHistoryService.savePolicyReportHistory(policyHistory);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
		        String url = tempStoragePath + File.separator + sdf.format(new Date()) + File.separator + this.current_user_id;
				File temFile = new File(url);
				if(!temFile.exists()){
					temFile.mkdirs();
				}
				
				String path = url+File.separator+"信息登记_wrong.xlsx";
				if(orgFilename.endsWith(".xls")){
					 path = url+File.separator+"信息登记_wrong.xls";				
				}
				FileOutputStream is = new FileOutputStream(path);
				workbook.write(is);
				is.close();
				json.setSuccess(false);
				//json.setMsg("");
				json.setCode("0");
				this.responseMessage(response, json);
				return;
			}else{
				
				MemberUserInfo memberUserInfo = new MemberUserInfo();
			    User user=  new User();
				user.setId(current_user_id);
				memberUserInfo.setUser(user);
				MemberUser muser  = memberUserService.findMemberUser(memberUserInfo); 
				final String registerCode = muser.getRegisteredCode();
				try{
					
				
				final Workbook workbookNew = workbook;
				
				new Thread(new Runnable(){
					public void run() {
						
						for (int i = 0; i < workbookNew.getNumberOfSheets(); i++) {// 获取每个Sheet表
							
							// 定义五个数组
							int maxCellCount = workbookNew.getSheetAt(i).getRow(0).getPhysicalNumberOfCells();
							// 每一个sheet的行处理
							for (int j = 1; j < workbookNew.getSheetAt(i).getLastRowNum()+1; j++) {// 获取每行，数据从第二行开始
								//汇总表的总计一栏不处理

								Row row = workbookNew.getSheetAt(i).getRow(j);
								if( null == row || "".equals(PolicyCheck.checkRowAllNull(row,maxCellCount))){
									//存在空行数据
									continue;
								}
																
								if(i==0){ ConvertData.convertDataToO80V(row, maxCellCount,policyLst,registerCode,policyNoLst);}
								if(i==1){ ConvertData.convertDataTo05PI(row, maxCellCount,policyLst,registerCode,policyNoLst);}
								if(i==2){ ConvertData.convertDataTo09IE(row, maxCellCount,policyLst,registerCode,policyNoLst);}
								if(i==3){ ConvertData.convertDataTo08CT(row, maxCellCount,policyLst,registerCode,policyNoLst);}
								if(i==4){ ConvertData.convertDataTo05OP(row, maxCellCount,policyLst,registerCode,policyNoLst);}
								if(i==5){ ConvertData.convertDataToSummary(workbookNew.getSheetAt(i),row, maxCellCount,summaryLst,registerCode); }
							}
						}
						
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						reportPolicyService.savePolicyInfoBatch(policyLst,policyNoLst);	
						summaryService.saveSummaryInfo(summaryLst);
					}
				}).start();
				
				}catch(Exception ex){
					logger.info("异常信息：" + ex.getMessage());
					ex.printStackTrace();
				}
				
				policyHistory.setIsSuccess("1");
				policyHistory.setRemark("操作成功");
				policyHistoryService.savePolicyReportHistory(policyHistory);
				this.responseMessage(response, json);
			}
			
		} catch (Exception e) {
			
			logger.info("异常信息：" + e.getMessage());
			policyHistory.setIsSuccess("0");
			policyHistory.setRemark("程序异常");
			policyHistoryService.savePolicyReportHistory(policyHistory);
			
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("exception");
			this.responseMessage(response, json);
			return;
		}
		
	}

	public void setReportPolicyService(IPolicyInformationService reportPolicyService) {
		this.reportPolicyService = reportPolicyService;
	}
 
	@Override
	public void setUserId(Long userId) {
		// TODO Auto-generated method stub
		this.current_user_id = userId; 
	}

	@Override
	public void setUserName(String userName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUserNickName(String userNickName) {
		// TODO Auto-generated method stub
		this.userNickName = userNickName; 
	}

	@Override
	public void setRoleCode(String roleCodes) {
		// TODO Auto-generated method stub
		
	}
}
