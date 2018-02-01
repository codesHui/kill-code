package com.imi.controllers.admin.news;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.imi.base.model.news.NewsInfo;
import com.imi.controllers.admin.security.ModuleConstant;
import com.imi.entity.products.News;
import com.imi.entity.security.Right;
import com.imi.service.news.INewsService;
import com.imi.service.security.IUserService;
import com.softvan.aware.IUserAware;
import com.softvan.model.Json;

@Controller
@RequestMapping(value = "/admin/news")
public class NewsControllers implements IUserAware  {
	private static final Logger logger = Logger.getLogger(NewsControllers.class);
	@Resource
	private INewsService newsService;
	@Resource
	private IUserService userService;
	private Long current_user_id;
	
	    //@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
		@RequestMapping(value={"","/newslist"},method={RequestMethod.GET, RequestMethod.POST})
		public String list(Model model,NewsInfo info,String pagenumber,String status,String checkAll){
			if(logger.isDebugEnabled()) logger.debug("加载消息列表页面...");
			if(checkAll!=null && !"".equals(checkAll)){
				model.addAttribute("checkAll", checkAll);
			}else{
				model.addAttribute("checkAll", 0);
			}
			if(status!=null && !"".equals(status)){
				if(status.length()==1){
					Integer[] state=new Integer[1];
					state[0]=Integer.valueOf(status);
					info.setAllState(state);
					model.addAttribute("states", status);
				}else{
					String [] stateStr=status.split(",");
					Integer[] allState=new Integer[10];
					String stateStr1="";
					for(int i=0;i<stateStr.length;i++){
						int j = Integer.valueOf(stateStr[i]).intValue();
						allState[i]=j;
						stateStr1+=j;
						System.out.println("state-----"+stateStr[i]+"/"+allState[i]);
					}
					info.setAllState(allState);
					model.addAttribute("states", stateStr1);
				}
			}
			if(!StringUtils.isEmpty(pagenumber)){
				info.setPage(Integer.valueOf(pagenumber));
			}else{
				pagenumber=1+"";
			} 
			info.setRows(10);
			info.setWhereFrom("后台");
			System.out.println("页码："+pagenumber);
			model.addAttribute("newslist", this.newsService.findAllNews(info));
			model.addAttribute("total",this.newsService.total(info));
			model.addAttribute("page",Integer.valueOf(pagenumber));
			
			return "news/newslist";
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
			String newsId = request.getParameter("newsId");
			News news = new News();
			
			news = newsService.findById(Long.parseLong(newsId));
			request.setAttribute("current_user_id",current_user_id);
			request.setAttribute("news",news);
			
			return "news/news_detail";
		}
		
		//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
		@RequestMapping(value={"/pushnews"}, method = RequestMethod.POST)
		@ResponseBody
		public Json pushNews(HttpServletRequest request){
			if(logger.isDebugEnabled()) logger.debug("保存发布内容...");
			Json result = new Json();
			try {
				int state=Integer.valueOf(request.getParameter("state"));
				Long createId=current_user_id;
				String subtitle=request.getParameter("subtitle");
				String content=request.getParameter("content");
				this.newsService.saveNews(state,createId,subtitle,content);
				result.setSuccess(true);
			} catch (Exception e) {
				result.setSuccess(false);
				result.setMsg(e.getMessage());
				logger.error("保存news失败", e);
			}
			 System.out.println("当前用户ID"+current_user_id);
			return result;
		}
		
		//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
		@RequestMapping(value={"/deletenews"}, method = RequestMethod.POST)
		@ResponseBody
		public Json deleteNews(HttpServletRequest request){
			if(logger.isDebugEnabled()) logger.debug("删除消息...");
			Json result = new Json();
			try {
				Long newsId=Long.valueOf(request.getParameter("newsId").replace(",", ""));
				News news=this.newsService.findById(newsId);
				this.newsService.deleteNews(news);
				result.setSuccess(true);
			} catch (Exception e) {
				result.setSuccess(false);
				result.setMsg(e.getMessage());
				logger.error("删除news失败", e);
			}
			return result;
		}		
		
		
		@RequestMapping(value = {"/pushnew"}, method = RequestMethod.GET)
		public String pushnew(){
			 
			 return "news/pushnews";
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
			
		}
}
