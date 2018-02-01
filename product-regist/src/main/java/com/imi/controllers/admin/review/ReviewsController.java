package com.imi.controllers.admin.review;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imi.base.model.reviews.ReviewsExcelInfo;
import com.imi.model.excel.RProductExcel;
import com.imi.support.ImportExport;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.imi.base.model.reviews.ReviewsInfo;
import com.imi.controllers.admin.security.UserController;
import com.imi.entity.products.Product;
import com.imi.entity.products.Reviews;
import com.imi.entity.products.Verifys;
import com.imi.entity.setting.Attachment;
import com.imi.service.attachment.IAttachmentService;
import com.imi.service.news.INewsService;
import com.imi.service.products.IProductService;
import com.imi.service.review.IReviewsService;
import com.imi.service.security.IRoleService;
import com.imi.service.security.IUserService;
import com.imi.service.setting.ReceiverService;
import com.imi.service.verifys.IVerifysService;
import com.imi.support.Constants;
import com.softvan.aware.IUserAware;
import com.softvan.model.Json;
@Controller
@RequestMapping(value = "/admin/review")
public class ReviewsController implements IUserAware {
	private static final Logger logger = Logger.getLogger(UserController.class);
	@Resource
	private IReviewsService reviewsService;
	@Resource
	private IProductService productService;
	@Resource
	private IVerifysService verifysService;
	@Resource
	private IUserService userService;
	@Resource
	private IAttachmentService attachmentService;
	@Resource
	private IRoleService roleService;
	@Resource
	private INewsService newsService;
	@Resource
	private ReceiverService receiverService;
	private String current_role;
	private Long current_user_id;
	//问题方法 肖磊多注意
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.UPDATE})
	@RequestMapping(value="/updateReviews", method = RequestMethod.POST)
	@ResponseBody
	public Json updateReviews(Reviews reviews,HttpServletRequest request){
		reviews=this.reviewsService.findOneReview(Long.valueOf(request.getParameter("reviewId").replace(",", "")));
		System.out.println("reviews数据=="+reviews.getFlowReason1());
		String flowOperate=request.getParameter("flowCheck").split(",")[0];
		int flowCheck=Integer.valueOf(request.getParameter("flowCheck").split(",")[1]);
		String flowReason=request.getParameter("flowReason");
		reviews.setStatus(flowCheck);
		reviews.setModifiedTime(new Date());
		Product product=reviews.getProduct();
		Verifys vf = null;
		if(current_role.equals("admin1")){
			if(flowCheck==1){
				reviews.setFlowCheck1(1);
			}else{
				reviews.setFlowCheck1(2);
				reviews.setRole(this.roleService.findBycode("admin2"));
			}

			if (flowCheck == 8){
				try {
					this.receiverService.insertMess(flowOperate, flowReason, current_user_id, product.getUser().getUser().getId());
				} catch (Exception e) {
					e.printStackTrace();
				}

				vf = new Verifys();
				vf.setProduct(reviews.getProduct());
				vf.setVerifyMan(this.userService.findById(current_user_id));
				vf.setVerifyReason(flowReason);
				vf.setCreateTime(new Date());
				vf.setStatus(1);
				vf.setSource(Constants.VERIFYS_SOURCE_FUCHA);
				vf.setModifiedTime(new Date());
				this.verifysService.save(vf);

			}


			reviews.setFlowOperate1(flowOperate);
			reviews.setFlowReason1(flowReason);
			reviews.setFlowDate1(new Date());
			reviews.setFlowMan1(this.userService.findById(current_user_id));
		}else if(current_role.equals("admin2")){
			if(flowCheck==1){
				reviews.setFlowCheck2(1);
			}else{
				reviews.setFlowCheck2(2);
				reviews.setRole(this.roleService.findBycode("admin3")); 
			}

			if (flowCheck == 8){
				try {
					this.receiverService.insertMess(flowOperate, flowReason, current_user_id, product.getUser().getUser().getId());
				} catch (Exception e) {
					e.printStackTrace();
				}

				vf = new Verifys();
				vf.setProduct(reviews.getProduct());
				vf.setVerifyMan(this.userService.findById(current_user_id));
				vf.setVerifyReason(flowReason);
				vf.setCreateTime(new Date());
				vf.setStatus(1);
				vf.setSource(Constants.VERIFYS_SOURCE_FUCHA);
				vf.setModifiedTime(new Date());
				this.verifysService.save(vf);
			}

			reviews.setFlowOperate2(flowOperate);
			reviews.setFlowReason2(flowReason);
			reviews.setFlowDate2(new Date());
			reviews.setFlowMan2(this.userService.findById(current_user_id));
		}else if(current_role.equals("admin3")){
			switch (flowCheck) {
			case 1:
				reviews.setFlowCheck3(1);
				break;
			case 6:
				//瑕疵产品提示(风险提示注册人)
				reviews.setFlowCheck3(2);
				product.setProductFlag(1);
				this.productService.saveProduct(product);
				//站内信   title:主题、text :内容、from_id：发送人ID、to_id ： 接收人ID
				try {
					this.receiverService.insertMess(flowOperate, flowReason, current_user_id, product.getUser().getUser().getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			/*case 7:
				//通报问题产品 ,生产news数据
				reviews.setFlowCheck3(2);
				int state=Constants.PROBLEM_NEWS;
				Long createId=current_user_id;
				String subtitle=reviews.getProduct().getUser().getCompanyName()+"产品为"+reviews.getProduct().getProductName()+"有问题！";
				String content=flowReason;
				this.newsService.saveNews(state,createId,subtitle,content);
				break;*/
			case 8:
				//站内信
				try {
					this.receiverService.insertMess(flowOperate, flowReason, current_user_id, product.getUser().getUser().getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				//提请核查,生产复查数据
				reviews.setFlowCheck3(2);
				vf = new Verifys();
				vf.setProduct(reviews.getProduct());
				vf.setVerifyMan(this.userService.findById(current_user_id));
				vf.setVerifyReason(flowReason);
				vf.setCreateTime(new Date());
				vf.setStatus(1);
				vf.setSource(Constants.VERIFYS_SOURCE_FUCHA);
				vf.setModifiedTime(new Date());
				this.verifysService.save(vf);
				break;
			default:
				break;
			}
			reviews.setFlowOperate3(flowOperate);
			reviews.setFlowReason3(flowReason);
			reviews.setFlowDate3(new Date());
			reviews.setFlowMan3(this.userService.findById(current_user_id));
		}; 
		if(logger.isDebugEnabled()) logger.debug("更新复查数据...");
		Json result = new Json();
		try {
			 this.reviewsService.updateReviews(reviews);
			 
			 //分配复查数据给admin3
			 if(current_role.equals("admin1")){  
					if(flowCheck==1){
						if(product.getProductType()==1){
							Boolean secondReviewMain=this.reviewsService.SecondReviewMain(reviews);
							System.out.println("SecondReviewMain:"+secondReviewMain);
						}
						if(product.getProductType()==2){
							this.reviewsService.SecondReviewExt(reviews);
							//System.out.println("SecondReviewExt:"+this.reviewsService.SecondReviewExt(reviews));
						}
						if(product.getProductType()==3){
							this.reviewsService.SecondReviewOth(reviews);
						}
					} 
				}else if(current_role.equals("admin2")){
					if(flowCheck==1){
						if(product.getProductType()==1){
							this.reviewsService.SecondReviewMain(reviews);
							//System.out.println("SecondReviewMain:"+this.reviewsService.SecondReviewMain(reviews));
						}
						if(product.getProductType()==2){
							this.reviewsService.SecondReviewExt(reviews);
							//System.out.println("SecondReviewExt:"+this.reviewsService.SecondReviewExt(reviews));
						}
						if(product.getProductType()==3){
							this.reviewsService.SecondReviewOth(reviews);
						}
					} 
				}
			 
			 //记录日志
			 List<Long> attIds = null;
			 this.productService.insertProductLog(
						Constants.PRODUCT_REVIEW, 
						String.valueOf(flowCheck), 
						flowOperate,
						current_role,
						current_user_id, 
						product.getProductNO(), 
						reviews.getId(), 
						flowReason, 
						Constants.ATTACHMENT_YES, 
						attIds);
			 result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新复查数据发生异常", e);
		}
		return result;
	}
	
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"","/reviewslist"},method={RequestMethod.GET, RequestMethod.POST})
	public String list(Model model,Product product,ReviewsInfo info,HttpServletRequest request){
		
		String pagenumber = request.getParameter("pagenumber");
		String status = request.getParameter("reviewStatus");
		Integer num = -1;
		try{
			num = Integer.parseInt(status);
		}catch (Exception e) {
			num = -1;
		}
		if(logger.isDebugEnabled()) logger.debug("加载复查列表页面...");
		info.setProduct(product);
		info.setRole(this.roleService.findBycode(current_role));
		if(!StringUtils.isEmpty(pagenumber)){
			info.setPage(Integer.valueOf(pagenumber));
		}else{
			pagenumber=1+"";
		} 
		info.setRows(10);
		if(!StringUtils.isEmpty(status)){
			switch (num) {
			case 0:
				info.setHasReviews("0");
				break;
			case 1:
				info.setHasReviews("1");
				break;
			case 2:
				info.setHasReviews("2");
				break;
			default:
				break;
			}
		}
		System.out.println("productType=="+product.getProductType()+"/"+current_role);
		model.addAttribute("reviewlist", this.reviewsService.findReviews(info));
		model.addAttribute("productInfo", product);
		model.addAttribute("total",String.valueOf(this.reviewsService.total(info)));
		model.addAttribute("page",Integer.valueOf(pagenumber));
		model.addAttribute("current_role",current_role);
		model.addAttribute("reviewStatus",status);
		
			return "review/reviews_list";
	}
	
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
		@RequestMapping(value={"/reviewshandle"}, method = RequestMethod.GET)
		public String reviewsHandle(Model model,HttpServletRequest request){
			if(logger.isDebugEnabled()) logger.debug("进入复查页面...");
			 //model.addAttribute("product", this.productService.findProductById(Long.valueOf(request.getParameter("productId"))));
			Reviews reviews=this.reviewsService.findOneReview(Long.valueOf(request.getParameter("reviewId").replace(",", "")));
			 model.addAttribute("product",reviews.getProduct());
			 model.addAttribute("review", reviews);
			 	// 查询pdf附件供预览
			//	List<Attachment> att = new ArrayList<Attachment>();
			 String productNO=reviews.getProduct().getProductNO();
				try {
					List<Attachment> atts = attachmentService.findAllSIGNFile(productNO);
					if(atts!=null){
							model.addAttribute("attachments",atts);
					}
					Attachment att = attachmentService.findSIGNPDF(productNO);
					// request.setAttribute("filePath", att.getFilePath());
					if(att != null){
							 model.addAttribute("filePath",att.getFilePath());
						}
					/*att = attachmentServiceImpl.findAllFile(reviews.getProduct().getProductNO(), Constants.PRODUCT_SIGN);
					if(null != att && att.size() >0){
						for (int i = 0; i < att.size(); i++) {
							 String doc=att.get(i).getName().split("\\.")[1];
							 if(doc.equals("pdf")){
								 model.addAttribute("pdfDoc",att.get(i).getFilePath());
							 }
						}
					}*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			 model.addAttribute("current_role",current_role);
			 
			return "review/reviews_handle02";
		}

		//*)Excel表单导出
		@RequestMapping(value = {"/import_product"})
		public  void import_product(Product product,ReviewsInfo info,HttpServletRequest request, HttpServletResponse response){
			info.setProduct(product);
			info.setRole(this.roleService.findBycode(current_role));
			info.setPage(1);//设置分页起始页
			info.setRows(10000);//设置最大行数
			List<Reviews> reviews = reviewsService.findReviews(info);
			//Excel中转集合对象
			List<ReviewsExcelInfo> ReviewsExcelInfo = new ArrayList<>();
			ReviewsExcelInfo ReviewsExcelInfos = null;
			//赋值转换
			int i = 1;
			for (Reviews review:reviews) {
				System.err.print(review);
				ReviewsExcelInfos = new ReviewsExcelInfo(review);//创建输出对象
				ReviewsExcelInfos.setReviews(review);
				ReviewsExcelInfos.setProductIndex(i);//传入序号值
				ReviewsExcelInfos.setFlowOperate(review.getFlowOperate1(),review.getFlowOperate2());//复查人意见
				ReviewsExcelInfos.setFlowReason(review.getFlowReason1(),review.getFlowReason2());//复查人描述
																							//复查人角色名称
				ReviewsExcelInfos.setFlowDate(review.getFlowDate1(),review.getFlowDate2());//复查人复查时间
				ReviewsExcelInfos.setFlowOperate3(review.getFlowOperate3());
				ReviewsExcelInfos.setFlowReason3(review.getFlowReason3());
				/*ReviewsExcelInfos.setFlowMan3(review.getFlowMan3().getNickName());*/ //抽查人角色
				ReviewsExcelInfos.setFlowDate3(review.getFlowDate3());
				ReviewsExcelInfo.add(ReviewsExcelInfos);//添加进导出对象集合
				i++;
			}

			RProductExcel rpe = new RProductExcel();//导出Excel表 表头和数据映射对象
			//出错关键 s
			Workbook workbook = ImportExport.exportExcel(rpe.getClass(),"复查问题整改统计表",ReviewsExcelInfo);
			//出错关键 e
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String fileName = "复查问题整改统计表"+format.format(new Date());
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
			// TODO Auto-generated method stub
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
			current_role=roleCodes;
		}
}
