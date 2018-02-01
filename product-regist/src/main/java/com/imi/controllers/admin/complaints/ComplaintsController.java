package com.imi.controllers.admin.complaints;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imi.base.model.security.ComplaintsInfo;
import com.imi.controllers.admin.security.ModuleConstant;
import com.imi.entity.products.Complaints;
import com.imi.entity.products.Product;
import com.imi.entity.security.Right;
import com.imi.entity.setting.Attachment;
import com.imi.entity.setting.MemberUser;
import com.imi.model.product.ProductInfo;
import com.imi.service.attachment.IAttachmentService;
import com.imi.service.complaints.IComplaintsService;
import com.imi.service.products.IProductService;
import com.imi.service.setting.DictionaryService;
import com.imi.support.Constants;
import com.softvan.aware.IUserAware;

@Controller
@RequestMapping(value = "/admin/complaints")
public class ComplaintsController implements IUserAware{
	private static final Logger logger = Logger.getLogger(ComplaintsController.class);
	@Resource
	private IComplaintsService complaintsServiceImpl;
	@Resource
	private IProductService productService;
	
	@Resource
	private IAttachmentService attachmentService;
	
	@Resource
	private DictionaryService dictionaryService;
	// 当前用户ID
	private Long current_user_id;
	private String roleCode;
	private String userNickName;
	
	
	
	@RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
	public String disposeDetail(Model model,HttpServletRequest request)
	{
		//投诉数据ID
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)){
			request.setAttribute("msg", "获取投诉数据出错...");
			return "500";
		}
		String productNO = request.getParameter("productNO");
		ComplaintsInfo info = new ComplaintsInfo();
		info.setProductNO(productNO);
		Attachment att = null;
		// 获取预览PDF文档
		try {
			att = attachmentService.findSIGNPDF(productNO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(att != null){
			request.setAttribute("filePath", att.getFilePath());
		}
		// 查询该投诉记录对应的产品
		ProductInfo pInfo = null;
		try {
			// 产品信息
			pInfo = complaintsServiceImpl.findProduct(productNO);
			// 投诉信息
			Complaints com = complaintsServiceImpl.findComplaintsById(id);
			Product p = pInfo.getProduct();
			model.addAttribute("p", p);
			model.addAttribute("m", p.getUser());
			model.addAttribute("c", com);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "complaints/complaints_detail";
	}
	
	
	
	
	
	
	/**
	 * 跳转管理员反馈处理页面
	 * 此页面下只查勘已处理的投诉数据
	 * @param productInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/list"})
	public String disposeList(@ModelAttribute(value="productInfo")ProductInfo productInfo,HttpServletRequest request,HttpServletResponse response){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
		
		MemberUser user = new MemberUser();
		user.setId(current_user_id);
		productInfo.setUser(user);
		if(productInfo.getPage()==null){
			productInfo.setPage(1);
		}

		productInfo.setRows(10);
		productInfo.setCurrentRowNo((productInfo.getPage()-1)*productInfo.getRows()+1);
		try {
			complaintsServiceImpl.findDisposeComplaints(productInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("productInfo",productInfo);
		return "complaints/complaints_list";
	}
	
	
	@RequestMapping(value={"/update"},method = RequestMethod.POST)
	public String update(@ModelAttribute(value="Complaints")Complaints complaints,HttpServletRequest request,HttpServletResponse response){
		String token = request.getParameter("token");
		if(token(token, request)){
			return "redirect:/admin/complaints/list ";
		}
		String productNO = request.getParameter("productNO");
		// 无理0   有理1
		String result = request.getParameter("result");
		String complainsResult = request.getParameter("complainsResult");
		if("0".equals(result)){
			complaints.setStatus(Constants.COMPLAINS_STATUS_NO_NEED);
		}
		if(complaints.getId() == null || StringUtils.isBlank(productNO)){
			request.setAttribute("msg", "发起投诉失败...");
			return "500";
		}
		ComplaintsInfo info = new ComplaintsInfo();
		info.setComplaints(complaints);
		try {
			info.setCurrent_user_id(current_user_id);
			info.setProductNO(productNO);
			complaintsServiceImpl.updateComplain(info);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			return "500";
		}
		
		// 添加产品详情
		productService.insertProductLog(
				Constants.PRODUCT_COMPLAINS, 
				null, 
				complainsResult,
				"1", 
				current_user_id, 
				productNO, 
				complaints.getId(), 
				complaints.getResultReason(),
				Constants.ATTACHMENT_YES, 
				null);
		
		
		
		return "redirect:/admin/complaints/list ";
	}
	
	
	public boolean token(String token,HttpServletRequest request){
		Object session_token = request.getSession().getAttribute("token");
		if(session_token == null){
			request.getSession().setAttribute("token", token);
			return false;
		}else{
			if(token.equals(session_token.toString())){
				return true;
			}else{
				request.getSession().setAttribute("token", token);
				return false;
			}
		}
	}

	@Override
	public void setUserName(String userName) {
	
		System.out.println(userName);
	}

	@Override
	public void setUserNickName(String userNickName) {
		// TODO Auto-generated method stub
		System.out.println(userNickName);
		this.userNickName=userNickName;
	}
	@Override
	public void setUserId(Long userId) {
		current_user_id = userId;
	}
	public void setRoleCode(String roleCodes) {
		System.out.println(roleCodes);
		roleCode  = roleCodes;
	}
	
	
	

}
