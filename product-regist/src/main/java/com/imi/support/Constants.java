package com.imi.support;

public class Constants {
	
	  /** 名称描述 */
    public final static String VC_NAMEDESCS = "VC_NAMEDESCS";

    /** 导入导出 */
    public final static String VC_IMPORTEXPORTS = "VC_IMPORTEXPORTS";
	// 审核处理状态  1/未处理  2/已处理
	public static int VERIFYS_STATUS_NO = 1;
	public static int VERIFYS_STATUS_YES = 2;
	
	// 正常
	public static int VERIFYS_STATUS_0 = 0;
	// 待核查
	public static int VERIFYS_STATUS_1 = 1;
	// 注册人上传材料  待举证
	public static int VERIFYS_STATUS_2 = 2;
	// 提交核查结论     待定论
	public static int VERIFYS_STATUS_3 = 3;
	// 注册人申请复议   待复议
	public static int VERIFYS_STATUS_4 = 4;
    // 核查结束
	public static int VERIFYS_STATUS_5 = 5;
	
	// 核查结果 0/正常  2/强制注销   3/通报问题产品    4/风险提示注册人
	public static int VERIFYS_RESULT_0 = 0;
	public static int VERIFYS_RESULT_2 = 2;
	public static int VERIFYS_RESULT_3 = 3;
	public static int VERIFYS_RESULT_4 = 4;
	
	
	
	// 投诉处理状态 1/未处理  2/已处理
	public static int COMPLAINS_STATUS_NO = 1;
	public static int COMPLAINS_STATUS_YES = 2;
	public static int COMPLAINS_STATUS_NO_NEED = 3;  // 无理投诉
	
	// 附件上传 1 :产品注册 ，2:产品复查，3:产品核查,4:产品投诉 
	public static int PRODUCT_SIGN = 1;
	public static int PRODUCT_REVIEW = 2;
	public static int PRODUCT_VERIFYS = 3;
	public static int PRODUCT_COMPLAINS = 4;
	
	//附件上传格式：0：其他文件， 1：PDF文件，2：图片文件
	public static String ATTACHMENT_FILETYPE_PDF="1";//PDF文件
	public static String ATTACHMENT_FILETYPE_IMAGE="2";//图片文件
	public static String ATTACHMENT_FILETYPE_OTHER="0";//其他文件

	// 附件是否生效
	public static int ATTACHMENT_YES = 1;
	public static int ATTACHMENT_NO = 2;
	
	//附件是否上传成功
	public static String ATTACHMENT_UPLOAD_TYPE_FAIL= "0"; //附件是上传成功
	public static String ATTACHMENT_UPLOAD_TYPE_SUCC = "1";//附件上传失败
	
	//消息表单初始状态
	//1：产品注册通知，2：产品注销通知，3：产品问题通知，4：产品警告通知，
	//5：注册平台简报，6：协会处理通报，7：监督行政处理，8：政策法规，9：相关新闻
	public static int REGIST_NEWS = 1;
	public static int CANCEL_NEWS = 2;
	public static int PROBLEM_NEWS = 3;
	public static int WARN_NEWS = 4;
	public static int REPORT_NEWS = 5;
	public static int DEAL_NEWS = 6;
	public static int ADMIN_NEWS = 7;
	public static int POLICY_NEWS = 8;
	public static int ABOUTUS_NEWS = 9;
	
	// 站内信状态
	private Integer status; 
	private Integer state;  
	
	// 已发送 1  未发送0
	public static int SEND_YES = 1;
	public static int SEND_NO = 0;
	// 已读1  未读0
	public static int READ_YES = 1;
	public static int READ_NO = 0;
	
	// 删除 1 未删除 0
	public static int DELETE_YES = 1;
	public static int DELETE_NO = 0;
	
	
	// 标识第一次是收件（1） 还是发件（2）
	public static int MESSAGE_RECEIVE = 1;
	public static int MESSAGE_SEND = 2;
	
	
	// 核查数据来源  1/复查  2/投诉
	public static int VERIFYS_SOURCE_FUCHA = 1;
	public static int VERIFYS_SOURCE_TOUSU = 2;
	
	
	//删除标示  1/发送方删除   2/接收方删除 
	public static int MESSAGE_DELETE_SEND = 1;
	public static int MESSAGE_DELETE_RECEIVE = 2;
	
	
	

}
