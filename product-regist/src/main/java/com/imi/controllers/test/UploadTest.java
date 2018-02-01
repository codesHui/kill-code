package com.imi.controllers.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.imi.entity.setting.Attachment;

@Controller
@RequestMapping(value = "/test")
public class UploadTest {
	
	@RequestMapping(value = {"/index"}, method = RequestMethod.GET)
	public String showVerifysData(Model model)
	{
		
		return "test/index";
	}
	
	@RequestMapping(value = {"/upload"},method = RequestMethod.POST)
	@ResponseBody
	public void test(HttpServletRequest request){
		try {
			List<Attachment> list = new ArrayList<Attachment>();
	        String responseStr="";  
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
	  
	        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();    
	        // 文件保存路径  
	        String ctxPath=request.getSession().getServletContext().getRealPath("/")+File.separator+"uploadFiles";   
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");  
	        String ymd = sdf.format(new Date());  
	        ctxPath += File.separator + ymd + File.separator;  
	        // 创建文件夹  
	        File file = new File(ctxPath);    
	        if (!file.exists()) {    
	            file.mkdirs();    
	        }    
	        String fileName = null;    
	        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
	        	Attachment attachment = new Attachment();
	            // 上传文件   
	            MultipartFile mf = entity.getValue();    
	            fileName = mf.getOriginalFilename(); 
	            // 文件后缀
	            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();  
	            // 重命名文件  
	            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
	            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;  
	            File uploadFile = new File(ctxPath + newFileName);    
	            attachment.setName(fileName);
	            attachment.setExtension(fileExt);
	            attachment.setFilePath(newFileName);
	            attachment.setSize(mf.getSize());
	            try {  
	                FileCopyUtils.copy(mf.getBytes(), uploadFile);  
	                attachment.setType("1");
	                responseStr="上传成功";  
	            } catch (IOException e) {  
	                responseStr="上传失败";  
	                attachment.setType("0");
	                e.printStackTrace();  
	            }
	            list.add(attachment);
	        }   
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
