package com.imi.support;

import java.util.ArrayList;
import java.util.List;

/**
 * DFA 节点树
 * @version 1.0
 *
 */
public class DFANode {
	  
    public char c;  
    public int flag; //1：表示终结，0：延续  
    public List<DFANode> nodes = new ArrayList<DFANode>();  
      
    public DFANode(char c) {  
        this.c = c;  
        this.flag = 0;  
    }  
      
    public DFANode(char c, int flag) {  
        this.c = c;  
        this.flag = flag;  
    }
    public String toString()
    {
    	return "--DFANode: c = "+ c +",flag = "+flag + ",nodes = " + nodes;            	
    }



}