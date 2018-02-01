package com.imi.support;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.imi.base.model.AttachmentInfo;
import com.imi.entity.setting.Attachment;
import com.softvan.utils.IOUtil;

public class UploadFileUtil {
	
	private static Logger logger = Logger.getLogger(UploadFileUtil.class);
	private String tempStoragePath;
	
	public  Attachment uploadFile(HttpServletRequest request) throws Exception{
		if(request == null){
			return null;
		}
		Attachment attachment = new Attachment();
		attachment.setCreateTime(new Date());
//		attachment.setCreator(creator);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
  
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();    
        // 文件保存路径  
        String ctxPath=tempStoragePath;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");  
        String ymd = sdf.format(new Date());  
        String childDir =  ymd +  "/" ; 
        System.out.println(ctxPath + childDir);
        // 创建文件夹  
        File file = new File(ctxPath + childDir);    
        if (!file.exists()) {    
            file.mkdirs();    
        }    
        String fileName = null;    
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 上传文件   
            MultipartFile mf = entity.getValue();    
            fileName = mf.getOriginalFilename(); 
            // 文件后缀
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();  
            // 重命名文件  
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;  
            File uploadFile = new File(ctxPath + childDir + newFileName);    
            attachment.setName(fileName);
            attachment.setExtension(fileExt);
            attachment.setFilePath(childDir+newFileName);
            attachment.setSize(mf.getSize());
            System.out.println("文件物理路径"+uploadFile.getAbsolutePath());
            try {  
                FileCopyUtils.copy(mf.getBytes(), uploadFile);
                // 上传成功
                attachment.setType("1");
            } catch (IOException e) {  
            	// 上传失败
                attachment.setType("0");
                e.printStackTrace();  
            }
        }
		return attachment;
	}

	/**
	 * 将上传的MultipartFile转成File
	 * @param orgFile
	 * @return
	 */
	public void multipartToFile(MultipartFile orgFile){

		
		 /*File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +   
                 file.getName());  
         multfile.transferTo(tmpFile);  
         return tmpFile; */ 
	}
	
	
	/**
	 * 存储上报信息的文件
	 * @return
	 */
	public File saveReportPolicyFile(MultipartFile orgFile,Long currentId){
		
		String orgFilename = orgFile.getOriginalFilename();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
        String ymd = sdf.format(new Date());  
        String childDir =  ymd +  File.separator ; 
		File tempFileDir = new File(tempStoragePath+File.separator + "reportPolicy" +File.separator + childDir + File.separator + currentId);
		//创建临时路径
		if(!tempFileDir.exists()){
			tempFileDir.mkdirs();
		}
		//2.存储该临时文件		
		String fileExt = orgFilename.substring(orgFilename.lastIndexOf(".") + 1).toLowerCase();  
        String newFileName = "信息登记" +  "." + fileExt;  

		File uploadFile = new File(tempFileDir + File.separator + newFileName);  
		System.out.println("临时路径：" + uploadFile.getAbsolutePath());
		try {
			FileCopyUtils.copy(orgFile.getBytes(), uploadFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uploadFile;
	}
	
	
	public String getTempStoragePath() {
		return tempStoragePath;
	}

	public void setTempStoragePath(String tempStoragePath) {
		this.tempStoragePath = tempStoragePath;
	}

	/**
	 * 上传文件
	 * @param originalFilename
	 * @param contentType
	 * @param bytes
	 * @return
	 * @throws Exception 
	 */
	public Attachment addUpload(String fileName, String contentType, byte[] data)
			throws Exception {
		if (logger.isDebugEnabled())
			logger.debug(String.format("上传附件［%1$s  %2$s］...", fileName,
					contentType));
		String msg = null;
		if (StringUtils.isEmpty(fileName)) {
			logger.error(msg = "附件文件名称为空！");
			throw new Exception(msg);
		}
		if (data == null || data.length == 0) {
			logger.error(msg = "附件文件内容为空！");
			throw new Exception(msg);
		}
		Attachment attachment = new Attachment();
		attachment.setName(fileName);
		attachment.setExtension(IOUtil.getExtension(attachment.getName()));
		attachment.setSize((long) data.length);
		attachment.setCode(DigestUtils.md5DigestAsHex(data));// 文件生成md5
		attachment.setContentType(contentType);
		attachment.setCreateTime(new Date());

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String ymd = sdf.format(new Date());
		String childPath="ueditor"+ File.separator +ymd;
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + attachment.getExtension();
		File file = new File(tempStoragePath + File.separator + childPath
				+ File.separator + newFileName);
	     attachment.setFilePath( childPath+ File.separator+newFileName);
		if (!file.exists()) {
			File dir = new File(tempStoragePath + File.separator + childPath);
			if (!dir.exists())
				dir.mkdirs();

			// 生成临时文件。

			try {
				FileCopyUtils.copy(data, file);
				// 上传成功
				attachment.setType("1");
			} catch (IOException e) {
				// 上传失败
				attachment.setType("0");
				e.printStackTrace();
			}

		}

		return attachment;
}
		
		
		
		


	/**
	 * 下载文件
	 * @param attachementId
	 * @return
	 */
	public AttachmentInfo download(String attachementId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
