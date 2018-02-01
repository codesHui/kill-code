package com.imi.controllers.admin.attachment;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.entity.setting.Attachment;
import com.imi.service.attachment.IAttachmentService;
import com.imi.support.UploadFileUtil;
import com.softvan.model.Json;

@Controller
@RequestMapping(value = "/att")
public class AttachmentController {
	
	@Resource
	private IAttachmentService attachmentServiceImpl;
	
	@Resource
	private UploadFileUtil uploadFileUtil;
	@RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
	@ResponseBody
	public Json delete(HttpServletRequest request)
	{
		Json j = new Json();
		j.setSuccess(true);
		
		String att_id = request.getParameter("id");
		if(StringUtils.isBlank(att_id)){
			j.setSuccess(false);
			j.setMsg("删除附件失败，无法获取ID...");
			return j;
		}
		try {
			attachmentServiceImpl.delete(Long.parseLong(att_id));
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("删除附件出错...");
			return j;
		} 
		return j;
	}
	
	/**
	 * 保存附件信息，与产品附件表信息
	 * 此方法保存时无产品编号信息，暂时用于产品注册
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/insert"}, method = RequestMethod.POST)
	@ResponseBody
	public Json insert(HttpServletRequest request){
		Json j = new Json();
		j.setSuccess(true);
		// 获取附件信息
		Attachment attachment = null;
		try {
			// 产品注册文件类型  1/商品条款   0/其他
			String filetype = request.getParameter("filetype");
			attachment = uploadFileUtil.uploadFile(request);
			attachment.setFiletype(filetype);
			Long id = attachmentServiceImpl.save(attachment);
			j.setId(String.valueOf(id));
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("上传附件出错...");
			return j;
		}
		return j;
	}
	
	
	/**
	 * 保存附件信息，与产品附件表信息
	 * 此方法保存附件时，含有productNO数据，可用于复查、审核等附加上传
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/insertReal"}, method = RequestMethod.POST)
	@ResponseBody
	public Json insertReal(HttpServletRequest request){
		Json j = new Json();
		j.setSuccess(true);
		// 获取附件信息
		Attachment attachment = null;
		try {
			attachment = uploadFileUtil.uploadFile(request);
			String productNo = request.getParameter("productNO");
			// 1 :产品注册 ，2:产品复查，3:产品核查,4:产品投诉
			String type = request.getParameter("type");
			Long id = attachmentServiceImpl.saveReal(attachment,productNo,type);
			j.setId(String.valueOf(id));
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("上传附件出错...");
			return j;
		}
		return j;
	}
	
	

}

