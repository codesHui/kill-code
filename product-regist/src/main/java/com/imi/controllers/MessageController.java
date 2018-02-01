package com.imi.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.entity.security.User;
import com.imi.entity.setting.CommonMessage;
import com.imi.service.security.IUserService;
import com.imi.service.setting.ReceiverService;
import com.imi.support.Constants;
import com.softvan.aware.IUserAware;
import com.softvan.model.Json;


@Controller
@RequestMapping("/message")
public class MessageController  implements IUserAware {
	
	private Long current_user_id;
	private String roleCode;
	private String userNickName;

	@Resource
	private ReceiverService receiverService;
	@Resource
	private IUserService UserService;
	
	
	@RequestMapping(value = {"/list"}, method = RequestMethod.GET)
	public String list(Model model,HttpServletRequest request){
//		String id = request.getParameter("id");
		
		// 根据当前登录用户查询站内信息
		if(StringUtils.isEmpty(current_user_id+"")){
			request.setAttribute("msg", "获取当前登录用户ID失败...");
			return "500";
		}
		int totalNum = receiverService.getMessageTotal(current_user_id);
		
		// 查询已读数据
		Integer num = receiverService.findReadYes(current_user_id);
		
		request.setAttribute("readTotal", totalNum);
		request.setAttribute("readNo", totalNum-num);
		
		return "message";
	}
	
	
	
	
	

	/**
	 * 
	 * 获取站内信已读和总数
	 * @param id
	 */
	@RequestMapping("/getReadInfo")
	@ResponseBody
	public Json readMessage(HttpServletRequest request) {
		Json j = new Json();
		j.setSuccess(true);
		// 站内信总数
		int totalNum = receiverService.getMessageTotal(current_user_id);
		
		// 查询已读数据
		Integer num = receiverService.findReadYes(current_user_id);
		j.setReadNo(totalNum-num);
		j.setReadTotal(totalNum);
		return j;
	}
	
	@RequestMapping(value={"/findMessContent"},method = RequestMethod.POST)
	@ResponseBody
	public void findMessContent(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Json j = new Json();
		j.setSuccess(true);
		response.setCharacterEncoding( "UTF-8" );
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if(StringUtils.isBlank(id+"")){
			j.setSuccess(false);
			j.setMsg("获取站内信息内容失败...");
		}
		CommonMessage mess = receiverService.getMessById(Long.parseLong(id));
		if(mess == null){
			j.setSuccess(false);
			j.setMsg("获取站内信息内容失败...");
		}else{
			// 标记发送记录(send) 还是 收信记录(receive)
			if(!"send".equals(type)){
				// 修改站内信状态为已读
				receiverService.updateReadStateById(mess.getId());
			}
			mess.setId(Long.parseLong(id));
			// 查询与当前发送人聊天记录
			List<CommonMessage> list = receiverService.findReceiverChatRecord(mess);
			
			String json = this.getMessageToJson(list);
			
			try {
				response.getWriter().print(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	/**
	 * 删除站内信
	 * @param ids
	 * @return
	 * 2014年8月22日
	 * liuyijun
	 */
	@RequestMapping("/deleteMessage")
	@ResponseBody
	public Json deleteMessage(HttpServletRequest request){
		Json j=new Json();
		j.setSuccess(true);
		String id = request.getParameter("id");
		// 标记发送记录(send) 还是 收信记录(receive)
		String type = request.getParameter("type");
		if(StringUtils.isBlank(id)){
			j.setSuccess(false);
			j.setMsg("删除失败");
			return j;
		}
		try {
			receiverService.deleteMessage(id,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		j.setSuccess(true);
		return j;
	}
	
	
	
	
	/**
	 * 回复收信箱中信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value={"/sendMessage_shouxin"},method = RequestMethod.POST)
	@ResponseBody
	public void sendMessage_shouxin(HttpServletRequest request,HttpServletResponse response){
		Json j=new Json();
		j.setSuccess(true);
		response.setCharacterEncoding( "UTF-8" );
		String pId = request.getParameter("id");
		if(StringUtils.isBlank(pId)){
			j.setSuccess(false);
			j.setMsg("发送站内信异常，请与管理员联系...");
		}
		String sendContent = request.getParameter("sendContent");
		CommonMessage comm = new CommonMessage();
		comm.setId(Long.parseLong(pId));
		comm.setText(sendContent);
		comm.setCreateTime(new Date());
		try {
			receiverService.insertMessageMutual_shouxin(comm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 查询与当前发送人聊天记录
		List<CommonMessage> list = receiverService.findReceiverChatRecord(comm);
		String json = getMessageToJson(list);
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 回复发信箱中信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value={"/sendMessage_faxin"},method = RequestMethod.POST)
	@ResponseBody
	public void sendMessage_faxin(HttpServletRequest request,HttpServletResponse response){
		Json j=new Json();
		j.setSuccess(true);
		response.setCharacterEncoding( "UTF-8" );
		String pId = request.getParameter("id");
		if(StringUtils.isBlank(pId)){
			j.setSuccess(false);
			j.setMsg("发送站内信异常，请与管理员联系...");
		}
		String sendContent = request.getParameter("sendContent");
		CommonMessage comm = new CommonMessage();
		comm.setId(Long.parseLong(pId));
		comm.setText(sendContent);
		comm.setCreateTime(new Date());
		try {
			receiverService.insertMessageMutual_faxin(comm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 查询与当前发送人聊天记录
		List<CommonMessage> list = receiverService.findReceiverChatRecord(comm);
		String json = getMessageToJson(list);
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 
	 * 写信
	 * @param id
	 */
	@RequestMapping("/writeInfo")
	@ResponseBody
	public Json writeInfo(HttpServletRequest request) {
		Json j = new Json();
		j.setSuccess(true);
		String writeContent = request.getParameter("writeContent");
		String writeMan = request.getParameter("writeMan");
		User user = UserService.findByUserCode(writeMan);
		if(user == null){
			j.setSuccess(false);
			j.setMsg("找遍了整个数据库都没有找到收件人...");
			return j;
		}
		CommonMessage comm = new CommonMessage();
		User fromUser = new User();
		fromUser.setId(current_user_id);
		comm.setFromUser(fromUser);
		
		comm.setToUser(user);
		comm.setReceiveOrSend(Constants.MESSAGE_SEND);
		comm.setTitle(writeContent);
		comm.setText(writeContent);
		comm.setCreateTime(new Date());
		comm.setState(Constants.READ_NO);
		Long id = receiverService.save(comm);
		receiverService.updateMutualFlag(id,id+"");
		return j;
	}
	
	
	/**
	 * 发件箱聊天记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value={"/findSendMessContent"},method = RequestMethod.POST)
	@ResponseBody
	public void findSendMessContent(HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding( "UTF-8" );
		Json j = new Json();
		j.setSuccess(true);
		// 查询当前登录人的发件箱记录
		List<CommonMessage> list = receiverService.findSendInfo(current_user_id);
		//j.setList(list); 出错
		StringBuffer json = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(list != null && list.size()>0){
			json.append("[");
			for (int i = 0; i < list.size(); i++) {
				json.append("{\"title\":\"").append(list.get(i).getTitle()).append("\",");
				json.append("\"id\":\"").append(list.get(i).getId()).append("\",");
				json.append("\"state\":\"").append(list.get(i).getState()).append("\",");
				json.append("\"createTime\":\"").append(format.format(list.get(i).getCreateTime())).append("\"},");
			}
			String js = json.toString().substring(0,json.toString().length()-1);
			json = new StringBuffer();
			json.append(js).append("]");
		}
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 收件箱聊天记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value={"/findReceiveMessContent"},method = RequestMethod.POST)
	@ResponseBody
	public void findReceiveMessContent(HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding( "UTF-8" );
		Json j = new Json();
		j.setSuccess(true);
		// 查询当前登录人的发件箱记录
		List<CommonMessage> list = receiverService.getReceiveList(current_user_id);
		//j.setList(list); 出错
		StringBuffer json = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(list != null && list.size()>0){
			json.append("[");
			for (int i = 0; i < list.size(); i++) {
				json.append("{\"title\":\"").append(list.get(i).getTitle()).append("\",");
				json.append("\"id\":\"").append(list.get(i).getId()).append("\",");
				json.append("\"state\":\"").append(list.get(i).getState()).append("\",");
				json.append("\"createTime\":\"").append(format.format(list.get(i).getCreateTime())).append("\"},");
			}
			String js = json.toString().substring(0,json.toString().length()-1);
			json = new StringBuffer();
			json.append(js).append("]");
		}
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private String getMessageToJson(List<CommonMessage> list){

		StringBuffer json = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(list != null && list.size()>0){
			json.append("{\"param\":[");
			for (int i = 0; i < list.size(); i++) {
				json.append("{\"text\":\"").append(list.get(i).getText()).append("\",");
				json.append("\"date\":\"").append(format.format(list.get(i).getCreateTime())).append("\",");
				if(list.get(i).getFromUser().getId() == current_user_id){
					json.append("\"username\":\"").append(list.get(i).getFromUser().getName()).append("\",");
					json.append("\"from\":\"").append(true).append("\"},");
				}else{
					json.append("\"username\":\"").append(list.get(i).getFromUser().getName()).append("\",");
					json.append("\"from\":\"").append(false).append("\"},");
				}
			}
			String js = json.toString().substring(0,json.toString().length()-1);
			json = new StringBuffer();
			json.append(js).append("]}");
		}
		return json.toString();
	}

	@Override
	public void setUserName(String userName) {
	
	System.out.println(userName);
	}

	@Override
	public void setUserNickName(String userNickName) {
		// TODO Auto-generated method stub
	//	System.out.println(userNickName);
		this.userNickName=userNickName;
	}
	@Override
	public void setUserId(Long userId) {
		current_user_id = userId;
	}
	public void setRoleCode(String roleCodes) {
//		System.out.println(roleCodes);
		roleCode  = roleCodes;
	}
	

}
