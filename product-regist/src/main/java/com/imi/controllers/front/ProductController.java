package com.imi.controllers.front;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.base.model.security.ComplaintsInfo;
import com.imi.controllers.admin.security.ModuleConstant;
import com.imi.entity.products.Complaints;
import com.imi.entity.products.Product;
import com.imi.entity.security.Right;
import com.imi.entity.setting.Attachment;
import com.imi.entity.setting.Dictionary;
import com.imi.model.product.ProductInfo;
import com.imi.service.attachment.IAttachmentService;
import com.imi.service.complaints.IComplaintsService;
import com.imi.service.products.IProductService;
import com.imi.service.setting.DictionaryService;
import com.imi.support.Constants;
import com.softvan.aware.IUserAware;
import com.softvan.model.Json;
/**
 * 主险种管理控制器。
 * @author wanmei.
 * @since 2014-06-02.
 */
@Controller("productController")
@RequestMapping(value = "/front/product")
public class ProductController  implements IUserAware  {
	private static final Logger logger = Logger.getLogger(ProductController.class);
	//注入产品服务接口。
	@Resource
	private IProductService productService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private IComplaintsService complaintsService;
	
	@Resource
	private IAttachmentService attachmentServiceImpl;
	
	
	private Long current_user_id;
	/**
	 * 获取列表页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/list"})
	public String list(@ModelAttribute(value="productInfo")ProductInfo productInfo,HttpServletRequest request,HttpServletResponse response){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
	
		List<Product> products = null;
		if(productInfo.getPage()==null){
			productInfo.setPage(1);
		}
		long total = productService.totalNums(productInfo);

		productInfo.setRows(10);
		productInfo.setCurrentRowNo((productInfo.getPage()-1)*productInfo.getRows()+1);
		
		products = productService.findProducts(productInfo,null,null,null);
		if(products != null && products.size() >0){
			long nowtime =  new Date().getTime();
			for(int i =0;i<products.size();i++){
				Product product = products.get(i);
				if(product.getProtectionType() == 1){
					long registTime = product.getRegistTime().getTime();
					int days = (int)((nowtime - registTime)/(1000*60*60*24));
					if( days >180){
						product.setProtectionType(2);
					}
				}
			}
		}
		
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("productInfo",productInfo);
		request.setAttribute("total",String.valueOf(total));
		request.setAttribute("products",products);
		request.setAttribute("now", new Date());
		
		return "product/product_list";
	}
	
	/**
	 * 获取详情页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/detail"})
	public String detail(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载详情页面...");
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
		String productNO = request.getParameter("productNO");
		/*			ProductInfo productInfo = new ProductInfo();
	List<Product> products = null;
		productInfo.setProductNO(productNO);*/
		
		Product product = productService.findProductByProductNO(productNO);
		if(product != null){
			long nowtime =  new Date().getTime();
			if(product.getProtectionType() == 1){
				long registTime = product.getRegistTime().getTime();
				int days = (int)((nowtime - registTime)/(1000*60*60*24));
				if( days >180){
					product.setProtectionType(2);
				}
			}
			try {
				List<Attachment> atts = attachmentServiceImpl.findAllSIGNFile(productNO);
				if(atts!=null){
				request.setAttribute("attachments",atts);
				}
				Attachment att;
				att = attachmentServiceImpl.findSIGNPDF(productNO);
				if(att!=null){
					 request.setAttribute("filePath", att.getFilePath());
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("product",product);
		
		return "product/product_detail";
	}
	
	
	/**
	 * 获取险种列表。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/riskName"})
	@ResponseBody
	public Json getRiskName(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("获取对应险种数据...");
		String key  = request.getParameter("key");
		Json result = new Json();
		//获取险类
		List<Dictionary> dictionarys = null;
		Dictionary dictionary1 = new Dictionary();
		dictionary1.setDicValue("测试一");
		dictionarys = dictionaryService.findChildDicLst(key);
	/*	dictionarys.add(dictionary1);*/
		
		 List a= new ArrayList();
	        int index = 0;
	        for (Dictionary item : dictionarys) {
	        	
	        	a.add(item.getDicKey()+"|"+item.getDicValue());
	            
	        }
	       
		result.setSuccess(true);
		result.setData(a);
		return result;
	}
	
	
	/**
	 * 保存产品投诉数据
	 * @param productInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/complain", method = RequestMethod.POST)
	@ResponseBody
	public Json complain(@ModelAttribute(value="complaints")Complaints complaints,HttpServletRequest request,HttpServletResponse response){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json j = new Json();
		j.setSuccess(true);
		String token = request.getParameter("token");
		if(token(token, request)){
			j.setSuccess(false);
			j.setMsg("请勿重复提交...");
			return j;
		}
		ComplaintsInfo info = new ComplaintsInfo();
		String productNO = request.getParameter("productNO");
		Product p = productService.findProductByProductNO(productNO);
		if(p == null){
			j.setSuccess(false);
			j.setMsg("保存投诉数据出错...");
			return j;
		}
		complaints.setProduct(p);
		info.setComplaints(complaints);
		try {
			complaintsService.save(info);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("保存投诉数据出错...");
			return j;
		}
		return j;
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
	}

	public Long getCurrent_user_id() {
		return current_user_id;
	}

	public void setCurrent_user_id(Long current_user_id) {
		this.current_user_id = current_user_id;
	}
	


	@Override
	public void setUserId(Long userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRoleCode(String roleCodes) {
		// TODO Auto-generated method stub
		
	}
}