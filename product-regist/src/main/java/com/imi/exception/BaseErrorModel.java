package com.imi.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 向前台返回的错误信息
 * @author admin
 *
 */
public class BaseErrorModel extends  RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BaseErrorModel(String errorMessage,String errorView) {
        super(errorMessage);
		this.errorMessage = errorMessage;
		this.errorView = errorView;
	}
	private String errorView;
	private String errorMessage;
	public String getErrorView() {
		return errorView;
	}
	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

    public Map toMap(){
        Map map = new HashMap();
        map.put("success",false);
        map.put("errorMessage",errorMessage);
        map.put("errorView",errorView);
        return map;
    }

}