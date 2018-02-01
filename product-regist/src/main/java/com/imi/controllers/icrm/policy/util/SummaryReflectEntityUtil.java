package com.imi.controllers.icrm.policy.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.imi.entity.reportPolicy.ReportSummary;

public class SummaryReflectEntityUtil {
	
	private ReportSummary summary;  
    
    public SummaryReflectEntityUtil(ReportSummary summary){  
        this.summary = summary;  
    }  
  
    public void doInitEntity(String methodName , Object obj)   
        throws SecurityException, NoSuchMethodException, IllegalArgumentException,   
        IllegalAccessException, InvocationTargetException{  
          
        //根据传入的属性名称构造属性的set方法名  
        methodName = "set"+methodName;  
        Method[] methods = summary.getClass().getMethods();  
        for(Method method:methods){  
            //如果方法同名则执行该方法（不能用于BO中有重载方法的情况）  
            if(methodName.equals(method.getName())){  
                method.invoke(summary, obj);  
            }  
        }  
    }  
}
